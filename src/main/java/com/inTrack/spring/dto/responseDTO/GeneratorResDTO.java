package com.inTrack.spring.dto.responseDTO;

import com.inTrack.spring.dto.common.GeneratorAgencyInfoDTO;
import com.inTrack.spring.entity.FuelType;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.TierLevel;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.GeneratorAgencyInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Builder
public class GeneratorResDTO {

    private Long generatorId;
    private String uniqueId;
    private Long floor;
    private String location;
    private String make;
    private String model;
    private String capacity;
    private Long management;
    private String landmark;
    private String deviceType;
    private Long disconnectedOn;
    private String managementNote;
    private Long noOfIdenticalUnits;
    private Long tierLevel;
    private String serialNo;
    private Long installedOn;
    private Boolean isActive;
    private String installedBy;
    private String managedBy;
    private String stackExhausting;
    private String mea;
    private String dep;
    private Boolean isDayTank;
    private String hasDayTank;
    private String firingRate;
    private String isDepPermitExpire;
    private Long depIssueDate;
    private Long depExpireDate;
    private String depStatus;
    private String isDOBPermitExpire;
    private Long dobIssueDate;
    private Long dobExpireDate;
    private String dobStatus;
    private Boolean isSchedule;
    private Boolean isPlanApproval;
    private Boolean isRecordsInPermanentBoundBook;
    private Boolean fuelCapping;
    private String gasFuelCapping;
    private Double gasNoxEmissionFactor;
    private String oilFuelCapping;
    private Double oilNoxEmissionFactor;
    private Double so2EmissionFactorOil;
    private Double gasEmissionFactor;
    private Double so2Allowable;
    private Double noxAllowable;
    // Hours of Operation Limit under DEC
    private String hoursOfOperationLimit;
    // DOE Qualification
    private String qualifiesForDOE;
    private String registeredWithDOE;
    private String renewedWithDOE;
    // DR Program Qualification
    private String qualifiesForDRProgram;
    private Long primaryFuelType;
    private Long secondaryFuelType;
    private Long buildingId;
    private Long status;
    private JobFilingInformation jobFilingInformation;
    private List<GeneratorAgencyInfoDTO> generatorAgencyInfoList;
}
