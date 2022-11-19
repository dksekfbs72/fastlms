package com.zerobase.fastlms.member.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;


public interface MemberCode {

    String MEMBER_STATUS_REQ = "REQ";
    String MEMBER_STATUS_ING = "ING";

    String MEMBER_STATUS_STOP = "STOP";
}
