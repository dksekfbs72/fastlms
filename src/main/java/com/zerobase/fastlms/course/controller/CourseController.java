package com.zerobase.fastlms.course.controller;

import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class CourseController extends BaseController{

    private final CourseService courseService;
    private final CategoryService categoryService;

    @GetMapping("/admin/course/list.do")
    public String list(Model model, CourseParam parameter) {

        parameter.init();

        List<CourseDto> courseDtoList = courseService.list(parameter);

        long totalCount = 0;

        if (!CollectionUtils.isEmpty(courseDtoList)) {
            totalCount = courseDtoList.get(0).getTotalCount();
        }

        String queryString = parameter.getQueryString();
        String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

        model.addAttribute("list",courseDtoList);
        model.addAttribute("totalCount",totalCount);
        model.addAttribute("pager",pagerHtml);


        return "/admin/course/list";
    }

    @GetMapping(value = {"/admin/course/add.do","/admin/course/edit.do"})
    public String add(Model model, HttpServletRequest request,
                      CourseInput parameter) {

        model.addAttribute("category", categoryService.list());

        boolean editMode = request.getRequestURI().contains("/edit.do");
        CourseDto detail = new CourseDto();

        if (editMode) {
            long id = parameter.getId();
            CourseDto existCourse = courseService.getById(id);

            if (existCourse == null) {
                //에러
                model.addAttribute("message", "강좌정보가 존재하지 않습니다.");
                return "common/error";
            }
            detail = existCourse;
        }

        model.addAttribute("detail",detail);
        model.addAttribute("editMode", editMode);
        return "/admin/course/add";
    }

    @PostMapping(value = {"/admin/course/add.do","/admin/course/edit.do"})
    public String addSubmit(Model model, CourseInput parameter,
                            HttpServletRequest request) {



        boolean editMode = request.getRequestURI().contains("/edit.do");

        if (editMode) {
            long id = parameter.getId();
            CourseDto existCourse = courseService.getById(id);

            if (existCourse == null) {
                //에러
                model.addAttribute("message", "강좌정보가 존재하지 않습니다.");
                return "common/error";
            }
            boolean result = courseService.set(parameter);

        } else {
            boolean result = courseService.add(parameter);
        }


        return "redirect:/admin/course/list.do";
    }

    @PostMapping("/admin/course/delete.do")
    public String del(Model model, CourseInput parameter,
                            HttpServletRequest request) {

        boolean result = courseService.del(parameter.getIdList());

        return "redirect:/admin/course/list.do";
    }
}
