package com.inTrack.spring.dto.requestDTO;

import com.inTrack.spring.dto.common.CoolingTowerAgencyInfoDTO;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.TypeOfCollingTower;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.CoolingTowerAgencyInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CoolingTowerReqDTO {

    private String uniqueId;
    private Long floor;
    private String location;
    private Long status;
    private String landmark;
    private String make;
    private String serialNo;
    private Long installedOn;
    private Boolean isActive = true;
    private String installedBy;
    private String managedBy;
    private String managementNote;
    private Long management;
    private String deviceType;
    private String model;
    private String serialNumber;
    private Double coolingCapacity;
    private String coolingCapacityUnit;
    private Double basinCapacity;
    private String basinCapacityUnit;
    private String intendedUse;
    private String equipmentName;
    private Long numberOfCells;
    private Boolean stateRegistrationCompleted;
    private Long stateEquipmentId;
    private Long nycDobRegistrationNo;
    private Long commissioningDate;
    private Boolean securelyAffixNycdobRegistration;
    private Long typeOfCollingTower;
    private Long buildingId;
    private JobFilingInformationReqDTO jobFilingInformationReqDTO;
    private List<CoolingTowerAgencyInfoDTO> coolingTowerAgencyInfoList;
}
