package com.inTrack.spring.entity.equipmentEntity;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.FuelType;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "generator")
public class Generator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "generator_id", nullable = false)
    private Long generatorId;

    @Column(name = "unique_id", unique = true, nullable = false)
    private String uniqueId;

    @Column(name = "floor")
    private Long floor;

    @Column(name = "location")
    private String location;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "device_type")
    private String deviceType;

    @Column(name = "disconnected_on")
    private Long disconnectedOn;

    @Column(name = "management_note")
    private String managementNote;

    @Column(name = "capacity")
    private String capacity;

    @Column(name = "no_of_identical_units")
    private Long noOfIdenticalUnits;

    @ManyToOne
    @JoinColumn(name = "tier_level_id")
    private TierLevel tierLevel;

    @Column(name = "serial_no")
    private String serialNo;

    @Column(name = "installed_on")
    private Long installedOn;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "installed_by")
    private String installedBy;

    @Column(name = "managed_by")
    private String managedBy;

    @Column(name = "stack_exhausting")
    private String stackExhausting;

    @Column(name = "mea")
    private String mea;

    @Column(name = "dep")
    private String dep;

    @Column(name = "is_day_tank")
    private Boolean isDayTank = false;

    @Column(name = "has_day_tank")
    private String hasDayTank;

    @Column(name = "firing_rate")
    private String FiringRate;

    @Column(name = "is_DEP_permit_expire")
    private String isDEPPermitExpire;

    @Column(name = "DEP_issue_date")
    private Long DEPIssueDate;

    @Column(name = "DEP_expire_date")
    private Long DEPExpireDate;

    @Column(name = "DEP_status")
    private String DEPStatus;

    @Column(name = "is_DOB_permit_expire")
    private String isDOBPermitExpire;

    @Column(name = "DOB_issue_date")
    private Long DOBIssueDate;

    @Column(name = "DOB_expire_date")
    private Long DOBExpireDate;

    @Column(name = "DOB_status")
    private String DOBStatus;

    @Column(name = "is_schedule")
    private Boolean isSchedule = false;

    @Column(name = "is_plan_approval")
    private Boolean isPlanApproval = false;

    @Column(name = "is_records_in_permanent_bound_book")
    private Boolean isRecordsInPermanentBoundBook = false;

    @Column(name = "is_fuel_capping")
    private Boolean fuelCapping = false;

    @Column(name = "gas_fuel_capping")
    private String gasFuelCapping;

    @Column(name = "gas_nox_emission_factor")
    private Double gasNoxEmissionFactor = 0D;

    @Column(name = "oil_fuel_capping")
    private String oilFuelCapping;

    @Column(name = "oil_nox_emission_factor")
    private Double oilNoxEmissionFactor = 0D;

    @Column(name = "so2_emission_factor_oil")
    private Double so2EmissionFactorOil = 0D;

    @Column(name = "gas_emission_factor")
    private Double gasEmissionFactor = 0D;

    @Column(name = "so2_allowable")
    private Double so2Allowable = 0D;

    @Column(name = "nox_allowable")
    private Double noxAllowable = 0D;

    // Hours of Operation Limit under DEC
    @Column(name = "hours_of_operation_limit")
    private String hoursOfOperationLimit;

    // DOE Qualification
    @Column(name = "qualifies_for_doe")
    private String qualifiesForDOE;

    @Column(name = "registered_with_doe")
    private String registeredWithDOE;

    @Column(name = "renewed_with_doe")
    private String renewedWithDOE;

    // DR Program Qualification
    @Column(name = "qualifies_for_dr_program")
    private String qualifiesForDRProgram;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private ElevatorStatus status;

    @ManyToOne
    @JoinColumn(name = "management_id")
    private ManagementType management;

    @ManyToOne
    @JoinColumn(name = "primary_fuel_type_id")
    private FuelType primaryFuelType;

    @ManyToOne
    @JoinColumn(name = "secondary_fuel_type_id")
    private FuelType secondaryFuelType;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @ManyToOne
    @JoinColumn(name = "job_filing_information_id")
    private JobFilingInformation jobFilingInformation;
}
