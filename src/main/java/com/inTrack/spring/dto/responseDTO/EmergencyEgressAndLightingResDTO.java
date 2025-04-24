package com.inTrack.spring.dto.responseDTO;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class EmergencyEgressAndLightingResDTO {

    private Long emergencyEgressId;
    private String uniqueId;
    private Long floor;
    private String location;
    private String landmark;
    private String deviceType;
    private Long installedOn;
    private String installedBy;
    private Long management;
    private String managedBy;
    private Boolean isActive;
    private String managementNote;
    private Long disconnectedOn;
    private String make;
    private String model;
    private String serialNo;
    private Long numberOfSignBoards;
    private String permitNumber;
    private Long permitDate;
    private Long buildingId;
    private JobFilingInformation jobFilingInformation;
    private Long status;
    private List<BoilerCogenAgencyInfoDTO> agencyInfoList;
}
