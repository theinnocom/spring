package com.inTrack.spring.dto.requestDTO;

import com.inTrack.spring.dto.common.IncineratorCrematoriesAgencyInfoDTO;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.IncineratorCrematoriesDropDown.UnitType;
import com.inTrack.spring.entity.equipmentEntity.IncineratorCrematoriesDropDown.WasteTypeBurner;
import com.inTrack.spring.entity.equipmentEntity.IncineratorFuelConsumption;
import com.inTrack.spring.entity.equipmentEntity.Stack;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.IncineratorCrematoriesAgencyInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class IncineratorCrematoriesReqDTO {

    private Long floor;
    private String uniqueId;
    private String location;
    private boolean activeStatus;
    private String make;
    private Long status;
    private String serialNo;
    private Long installedOn;
    private String installedBy;
    private String managedBy;
    private String applicationId;
    private Long unitType; // Crematory, Municipal Incinerator, Medical waste Incinerator
    private String burnerModel;
    private String burnerSerialNumber;
    private String burnerCapacity;
    private Long stackId;
    private Long wasteTypeBurner; // Cat, Deer, Dog, Medical Waste, Pets, Road Kills
    private boolean scrubberInstalled;
    private boolean coMonitorInstalled;
    private boolean opacityMonitorInstalled;
    private boolean o2MonitorInstalled;
    private boolean opacityChartRecorderAvailable;
    private boolean quarterlyCgaRequired;
    private boolean temperatureMeasurementRequired;
    private double minimumTemperaturePrimary;
    private double minimumTemperatureSecondary;
    private double averageFacilityTemperaturePrimary;
    private double averageFacilityTemperatureSecondary;
    private Long buildingId;
    private JobFilingInformationReqDTO jobFilingInformationReqDTO;
    private List<IncineratorCrematoriesAgencyInfoDTO> agencyInfoList;
    private List<IncineratorFuelConsumption> incineratorFuelConsumptions;
}
