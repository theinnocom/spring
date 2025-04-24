package com.inTrack.spring.dto.requestDTO;

import com.inTrack.spring.entity.equipmentEntity.agencyEntity.PetroleumBulkStorageAgency;
import com.inTrack.spring.entity.equipmentEntity.petroleumDropDown.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PetroleumBulkStorageReqDTO {

    private String uniqueId;
    private Long floor;
    private String location;
    private String make;
    private Long management;
    private String landmark;
    private String status;
    private String deviceType;
    private Long disconnectedOn;
    private String managementNote;
    private String model;
    private String capacity;
    private String serialNo;
    private Long installedOn;
    private Boolean isActive;
    private String installedBy;
    private String managedBy;
    private Long tankClosedDate;
    private String pbsNumber;
    private String tankNumber;
    private Long tankLocation;
    private String category;
    private Double tankCapacity;
    private Long productStored;
    private Long tankType;
    private Long tankInternalProtection;
    private Long tankExternalProtection;
    private Long tankSecondaryContainment;
    private Long tankLeakDetection;
    private Long overfill;
    private String secondaryOverfill;
    private Long spillPrevention;
    private Long dispenser;
    private Long pipeLocation;
    private Long pipeType;
    private Long pipeExternalProtection;
    private Long pipingSecondaryContainment;
    private Long pipingLeakDetection;
    private String udc;
    private Long tankStatus;
    private Long subpart;
    private Long buildingId;
    private JobFilingInformationReqDTO jobFilingInformationReqDTO;
    private String tankInternalProductionOtherList;
    private String tankExternalProductionOtherList;
    private String tankSecondaryContainmentOtherList;
    private String tankLeakDetectionOtherList;
    private String overFillProtectionOtherList;
    private String spillPreventionOtherList;
    private String pipeTypeOtherList;
    private String pipingSecondaryContainmentOtherList;
    private String pipingLeakDetectionOtherList;
    private String pipingInternalProductionOtherList;
    private String tankTypeOtherList;
    private String note;
    private List<PetroleumBulkStorageAgency> petroleumBulkStorageAgency;
}
