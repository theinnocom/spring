package com.inTrack.spring.dto;

import com.inTrack.spring.entity.FacilityAgency.PostLogAgency;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostLogDTO {

    private Long employeeId;
    private String employeeNo;
    private String employeeName;
    private String jobTitle;
    private Long dateOfInjuryOrIllness;
    private String incidentTime;
    private String incidentLocation;
    private String injuryOrIllnessDescription;
    private String harmfulObjectOrSubstance;
    private String activityBeforeIncident;
    private String previousIllnessOrInjuryDescription;
    private String healthCareProfessional;
    private String injuryOrIllnessCategory;
    private int daysAwayFromWork;
    private String description;
    private String note;
    private Long facilityId;
    private Long buildingId;
    private Boolean annualLogInspected;
    private Long lastInspectionDate;
    private Long nextInspectionDate;
    private String inspectedBy;
    private PostLogAgency postLogAgency;
}

