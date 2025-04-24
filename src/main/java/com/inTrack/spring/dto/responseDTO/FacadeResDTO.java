package com.inTrack.spring.dto.responseDTO;

import com.inTrack.spring.entity.Building;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacadeResDTO {

    private Long facadeId;
    private Boolean isFacadeInspectionAvailable;
    private String filingNumber;
    private Long facadePeriod;
    private Long cycle;
    private Long numberOfStories;
    private String exteriorWallType;
    private Long initialFilingDate;
    private String initialFilingStatus;
    private Long lastCycleFilingDate;
    private String priorStatus;
    private Long nextCycleFilingDate;
    private Long fieldInspectionCompletedDate;
    private Long signedDate;
    private String currentStatus;
    private String applicantName;
    private String license;
    private String note;
    private Building building;
    private Long createAt;
    private Long updatedAt;
}
