package com.inTrack.spring.dto.responseDTO;

import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.entity.FuelType;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import com.inTrack.spring.entity.equipmentEntity.boilerDropDown.BoilerPressureType;
import com.inTrack.spring.entity.equipmentEntity.boilerDropDown.PrimaryUse;
import com.inTrack.spring.entity.equipmentEntity.boilerDropDown.TestOnFuel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BoilerResDTO {

    private Long boilerId;
    private String uniqueId;
    private Long floor;
    private String location;
    private String landMark;
    private String makeBy;
    private String serialNo;
    private Long installedOn;
    private Long management;
    private Long disconnectedOn;
    private String managementNote;
    private String deviceType;
    private Boolean isActive;
    private String installedBy;
    private String managedBy;
    private String boilerMake;
    private String boilerModel;
    private String burnerMake;
    private String burnerModel;
    private String burnerSerialNumber;
    private Long numberOfIdenticalUnits;
    private Double capacity;
    private String meaNumber;
    private String depNumber;
    private String depStatus;
    private String decSourceId;
    private String dobDeviceNumber;
    private Double firingRateOil;
    private Double firingRateNaturalGas;
    private String primaryFuelTank;
    private String secondaryFuelTank;
    private String stackExhausting;
    private Boolean fireDepartmentApproval;
    private Boolean scheduleC;
    private Boolean planApproval;
    private String parameter;
    private String octrNumber;
    private Boolean opacityMonitorInstalled;
    private Boolean performanceTestProtocolSubmitted;
    private Long performanceTestProtocolDate;
    private Boolean performanceTestReportSubmitted;
    private Long performanceTestReportDate;
    private Boolean sulphurContentSampled;
    private Double sulphurContentPercentage;
    private Boolean nitrogenContentRequired;
    private Double nitrogenContentPercentage;
    private Boolean nspsSubject;
    private Boolean modifiedInPast;
    private Boolean emissionCreditRequired;
    private Boolean federalPSDSubject;
    private Boolean otherBoilerCombined;
    private Boolean fuelCapping;
    private Double gasFuelCapping;
    private Double gasNOxEmissionFactor;
    private Double oilFuelCapping;
    private Double oilNOxEmissionFactor;
    private Double so2EmissionFactorOil;
    private Double so2EmissionFactorGas;
    private Double so2AllowableTonsPerYear;
    private Double noxAllowableTonsPerYear;
    private String comments;
    private Long testOnFuel;
    private Long boilerPressureType;
    private Long primaryUse;
    private Long secondaryFuel;
    private Long primaryFuel;
    private Long buildingId;
    private JobFilingInformation jobFilingInformation;
    private String applicationId;
    private String applicationType;
    private Long status;
    private List<BoilerCogenAgencyInfoDTO> agencyInfoList;
}
