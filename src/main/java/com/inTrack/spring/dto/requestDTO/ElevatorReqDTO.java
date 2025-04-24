package com.inTrack.spring.dto.requestDTO;


import com.inTrack.spring.dto.responseDTO.BoilerCogenAgencyInfoDTO;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.DeviceType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ElevatorReqDTO {

    private String uniqueId;
    private Long installedOn;
    private Long installedBy;
    private Long elevatorStatus;
    private String comments;
    private String elevatorJobNo;
    private Long disconnectedOn;
    private boolean activeStatus = true;
    private String typeOfUse;
    private Long floorFrom;
    private Long floorTo;
    private Long floor;
    private String location;
    private String landmark;
    private String safetyType;
    private Long managementType;
    private String managedBy;
    private String make;
    private String model;
    private String capacity;
    private Long serial;
    private String governorType;
    private String machineType;
    private String modeOfOperation;
    private String phoneInspection;
    private JobFilingInformationReqDTO jobFilingInformationReqDTO;
    private Long buildingId;
    private List<BoilerCogenAgencyInfoDTO> agencyInfoList;
    private Long deviceType;
}
