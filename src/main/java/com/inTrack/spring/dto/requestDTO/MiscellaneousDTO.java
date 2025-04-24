package com.inTrack.spring.dto.requestDTO;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.AgencyType;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MiscellaneousDTO {

    private Long id;
    private String uniqueId;
    private Long floor;
    private String location;
    private Boolean isActive;
    private String landmark;
    private Long installedOn;
    private Long installedBy;
    private Long management;
    private Long status;
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
    private Long buildingId;
    private String agencyId;
    private Long issueDate;
    private String agencyStatus;
    private Long expirationDate;
    private Long submittedDate;
    private Long agencyName;
    private String agencyNote;
    private JobFilingInformationReqDTO jobFilingInformationReqDTO;
}
