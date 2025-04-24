package com.inTrack.spring.dto.requestDTO;

import com.inTrack.spring.dto.common.StackAgencyInfoDTO;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.StackAgencyInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StackReqDTO {

    private Long floor;
    private String uniqueId;
    private String location;
    private boolean activeStatus;
    private String make;
    private String serialNo;
    private Long installedOn;
    private String installedBy;
    private String managedBy;
    private String deviceType;
    private Long status;
    private String managementNote;
    private String landmark;
    private Long disconnectedOn;
    private Long management;
    private String applicationId;
    private double heightFeet;
    private double diameterInches;
    private Long numberOfEquipmentsConnected;
    private double totalCapacityOfEquipments;
    private boolean isOilFuel;
    private boolean isGasFuel;
    private double flowRate;
    private double exhaustTemperature;
    private double velocity;
    private String note;
    private Long buildingId;
    private JobFilingInformationReqDTO jobFilingInformationReqDTO;
    private List<StackAgencyInfoDTO> agencyInfoList;
}
