package com.inTrack.spring.dto.requestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobFilingInformationReqDTO {

    private Long jobFilingId;
    private String jobNumber;
    private String type;
    private String status;
    private String description;
    private Long approvalDate;
    private Long signOffDate;
    private String commands;
    private String equipmentName;
    private String notes;
    private Long buildingId;
}
