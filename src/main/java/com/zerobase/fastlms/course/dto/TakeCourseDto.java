package com.zerobase.fastlms.course.dto;

import com.zerobase.fastlms.course.entity.TakeCourse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TakeCourseDto {

    Long id;
    long courseId;
    String userId;
    long payPrice;
    String status;//상태(수강신청, 결재완료, 수강취소)
    LocalDateTime regDt;

    String userName;
    String phone;
    String subject;

    long totalCount;
    long seq;

    public static TakeCourseDto of(TakeCourse x) {

        return TakeCourseDto.builder()
                .id(x.getId())
                .courseId(x.getCourseId())
                .userId(x.getUserId())
                .payPrice(x.getPayPrice())
                .status(x.getStatus())
                .regDt(x.getRegDt())
                .build();
    }

    public String getRegDtText() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return regDt != null ? regDt.format(dateTimeFormatter) : "";
    }
}

