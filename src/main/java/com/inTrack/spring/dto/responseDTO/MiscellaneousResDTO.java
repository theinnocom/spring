package com.inTrack.spring.dto.responseDTO;

import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MiscellaneousResDTO {

    private Long id;
    private Long uniqueId;
    private Long floor;
    private String location;
    private String landmark;
    private Long installedOn;
    private Long installedBy;
    private ManagementType management;
    private ElevatorStatus status;
    private String managedBy;
    private String managementNote;
    private Long disconnectedOn;
    private String applicationId;
    private String equipmentType;
    private String make;
    private String model;
    private String serialNumber;
    private String capacity;
    private Integer numberOfIdenticalUnits;
    private Boolean connectedWithOtherEquipment;
    private String note;
    private Building buildingId;
    private JobFilingInformation jobFilingInformation;
}
