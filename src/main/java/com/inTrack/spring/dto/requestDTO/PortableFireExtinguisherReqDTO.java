package com.inTrack.spring.dto.requestDTO;

import com.inTrack.spring.dto.responseDTO.BoilerCogenAgencyInfoDTO;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.ClassType;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PortableFireExtinguisherReqDTO {

    private String uniqueId;
    private Long floor;
    private String location;
    private boolean activeStatus;
    private String makeBy;
    private String serialNo;
    private Long installedOn;
    private String installedBy;
    private String managedBy;
    private Long buildingId;
    private String landmark;
    private String managementNote;
    private Long disconnectedOn;
    private Long management;
    private String model;
    private String capacity;
    private Long classType; // A, B, C, D, K
    private String hydrostaticPressureTest; // Yes, No, Not Applicable
    private String inspectionTagAttached; // Yes, No, Not Applicable
    private String inspectionHydrostaticPressureTest;
    private Long inspectionLastTestDate;
    private Long inspectionNextTestDate;
    private JobFilingInformationReqDTO jobFilingInformationReqDTO;
    private Long status;
    private List<BoilerCogenAgencyInfoDTO> agencyInfoList;
}
