package com.inTrack.spring.dto.responseDTO;

import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LandFillResDTO {

    private Long landFillId;
    private String uniqueId;
    private String managementNote;
    private Long management;
    private Long status;
    private Long disconnectedOn;
    private String landmark;
    private String deviceType;
    private Long floor;
    private String location;
    private boolean activeStatus;
    private String make;
    private String serialNo;
    private Long installedOn;
    private String installedBy;
    private String managedBy;
    private Long buildingId;
    private String applicationId;
    private String operationCommenceDate;
    private String statusOfSource;
    private boolean linkedToCogenOrTurbines;
    private String linkedEquipmentList;
    private boolean capOnLfgMonthlyProductionQuantity;
    private String lfgMonthlyProductionQuantityCap;
    private boolean capOnLfgCombustionByCogenOrTurbineUnit;
    private String lfgCombustionCap;
    private boolean monitoringRequired;
    private String fivePercentMeasuredAllowableValue;
    private String ch4PercentMeasuredAllowableValue;
    private boolean flueGasTemperatureLimit;
    private String flueGasTemperatureLimitTestedAllowableValue;
    private String noxTestedAllowableEmission;
    private String coTestedAllowableEmission;
    private String othersTestedAllowableEmission;
    private boolean limitOnYearlyOperatingHours;
    private String yearlyOperatingHours;
    private String comments;
    private JobFilingInformation jobFilingInformation;
    private List<BoilerCogenAgencyInfoDTO> agencyInfoList;
}
