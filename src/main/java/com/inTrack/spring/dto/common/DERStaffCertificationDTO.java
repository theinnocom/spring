package com.inTrack.spring.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DERStaffCertificationDTO {

    private Long id;
    private String staffName;
    private Long issueDate;
    private String authorizationNumber;
    private Boolean certificationAvailable; // true for Yes, false for No
    private String note;
    private Long certificateType;
    private Long spcc;
}
