package com.inTrack.spring.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncineratorCrematoriesAgencyInfoDTO {

    private Long id;
    private String depNumber;
    private Long issueDate;
    private Long expirationDate;
    private String status;
    private String decNumber;
    private Long decIssueDate;
    private Long decExpirationDate;
    private Boolean isSolidWastePermitRequired;
    private Long stackLastTestDate;
    private Long stackNextTestDate;
    private boolean stackTestProtocolSubmitted;
    private Long parameterTypes;
    private boolean stackTestPassed;
    private String testConductedBy;
    private String note;
    private Long incineratorCrematoriesAgency;
}
