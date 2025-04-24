package com.inTrack.spring.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkPermitDTO {

    private Long id;
    private String permitNumber;
    private String workType;
    private String jobType;
    private String description;
    private Long filingDate;
    private Long jobStartDate;
    private Long expiryDate;
    private String status;
    private String issuedTo;
    private String registrationNumber;
    private String businessName;
    private String phoneNumber;
    private String note;
    private Long jobFilingInformation;
    private Long building;
}
