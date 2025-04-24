package com.inTrack.spring.dto.requestDTO;

import com.inTrack.spring.dto.responseDTO.BoilerCogenAgencyInfoDTO;
import com.inTrack.spring.entity.FuelType;
import com.inTrack.spring.entity.equipmentEntity.CogenTurbineDropDown.CogenPrimaryUse;
import com.inTrack.spring.entity.equipmentEntity.CogenTurbineDropDown.TypeOfControlEfficiency;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CogenTurbineReqDTO {

    private Long floor;
    private String uniqueId;
    private String location;
    private boolean activeStatus;
    private String makeBy;
    private String serialNo;
    private Long installedOn;
    private String installedBy;
    private String managedBy;
    private Long buildingId;
    private String applicationId;
    private String applicationType;
    private int numberOfIdenticalUnits;
    private Long primaryUse;
    private boolean engineHasControlSystem;
    private Long typeOfControlEfficiency;
    private String model;
    private String serialNumber;
    private String burnerMake;
    private String burnerModel;
    private String burnerSerialNumber;
    private Double capacity;
    private String burnerType;
    private Long primaryFuel;
    private Long secondaryFuel;
    private Double firingRateOil;
    private Double firingRateNaturalGas;
    private String primaryFuelFromTank;
    private String secondaryFuelFromTank;
    private String stackExhausting;
    private boolean isTurbineWithUnit;
    private boolean isWasteHrbWithUnit;
    private boolean fuelCapping;
    private Double gasFuelCapping;
    private Double gasNoxEmissionFactor;
    private Double oilFuelCapping;
    private Double oilNoxEmissionFactor;
    private Double so2EmissionFactorOil;
    private Double so2EmissionFactorGas;
    private Double so2Allowable;
    private Double noxAllowable;
    private String comments;
    private Long status;
    private JobFilingInformationReqDTO jobFilingInformationReqDTO;
    private List<BoilerCogenAgencyInfoDTO> agencyInfoList;
}
