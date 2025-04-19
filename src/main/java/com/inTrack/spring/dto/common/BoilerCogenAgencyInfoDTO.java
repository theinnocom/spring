package com.inTrack.spring.dto.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BoilerCogenAgencyInfoDTO {

    private Long id;
    private String agencyNumber;
    private Long issueDate;
    private Long expirationDate;
    private String status;
    private Long submittedDate;
    private String note;
    private Long lastInspectionDate;
    private Long nextInspectionDate;
    private String inspectedBy;
    private Long fdDate;
    private Long datePerformed;
    private String performedBy;
    private Boolean isRecordKept;
    private Long issuedDate;  //state permit
    private Boolean isPaintSprayBooth;
    private Boolean isInspectionTag;
    private Long dobIssueDate;
    private Long lastTestDate;
    private Long nextTestDate;
    private String testDoneBy;
    private String fdnyCertificateNo;
    private String firmInspected;
    private Long inspectionDurationType;
    private Long boilerCogenAgency;
    private Long inspectionType;
}
