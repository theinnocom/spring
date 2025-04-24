package com.inTrack.spring.dto.requestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacadeReqDTO {

    private Boolean isFacadeInspectionAvailable = false;
    private String filingNumber;
    private Long facadePeriod;
    private Long cycle;
    private Long numberOfStories;
    private String exteriorWallType;
    private Long initialFilingDate;
    private String initialFilingStatus;
    private Long lastCycleFilingDate;
    private String priorStatus;
    private Long fieldInspectionCompletedDate;
    private Long signedDate;
    private Long nextCycleFilingDate;
    private String currentStatus;
    private String applicantName;
    private String license;
    private String note;
    private Long buildingId;
}
