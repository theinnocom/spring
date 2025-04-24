package com.inTrack.spring.dto;

import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.FirstAidAgencyInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FirstAidKitDTO {
    private Long id;
    private String uniqueId;
    private Long status;
    private Long floor;
    private String location;
    private String landmark;
    private Long installedOn;
    private String installedBy;
    private Long management;  // Foreign key reference to ManagementType
    private String managedBy;
    private String managementNote;
    private Long disconnectedOn;
    private String make;
    private String model;
    private String serialNumber;
    private Long kitUsedOn;
    private String usedFor;
    private Boolean aidKitRestoredAfterUse;
    private Long kitRestoredDate;
    private Long kitExpiryDate;
    private String kitRestoredBy;
    private Boolean employeeTrained;
    private String employeeName;
    private String trainedPersonNames;
    private Long monthlyInspectionDate;
    private String inspectedBy;
    private Long annualInspectionDate;
    private String note;
    private Long buildingId;  // Foreign key reference to Building
    private JobFilingInformation jobFilingInformation;  // Foreign key reference to JobFilingInformation
    private List<FirstAidAgencyInfo> firstAidAgencyInfoList;
}

