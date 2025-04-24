package com.inTrack.spring.dto.requestDTO;

import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.ChillerGroup;
import com.inTrack.spring.entity.equipmentEntity.TypeOfChiller;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChillerReqDTO {

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
    private String model;
    private String capacity;
    private Long fdnyInspectionDate;
    private String eupcardAvailable;
    private Long inspectionDate;
    private String inspector;
    private ChillerGroup chillerGroup;
    private ManagementType management;
    private String landmark;
    private String deviceType;
    private Long disconnectedOn;
    private String managementNote;
    private String typeOfChillerOthersList;
    private TypeOfChiller typeOfChiller;
    private JobFilingInformationReqDTO jobFilingInformationReqDTO;
}
