package com.inTrack.spring.dto.responseDTO;

import com.inTrack.spring.dto.common.CoolingTowerAgencyInfoDTO;
import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.TypeOfCollingTower;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.CoolingTowerAgencyInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CoolingTowerResDTO {

    private Long coolingTowerId;
    private String uniqueId;
    private Long floor;
    private Long status;
    private String location;
    private String landmark;
    private String make;
    private String serialNo;
    private Long installedOn;
    private Boolean isActive;
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
    private JobFilingInformation jobFilingInformation;
    private List<CoolingTowerAgencyInfoDTO> agencyInfoList;
}
