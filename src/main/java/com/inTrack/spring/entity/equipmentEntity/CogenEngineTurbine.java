package com.inTrack.spring.entity.equipmentEntity;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.FuelType;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.equipmentEntity.CogenTurbineDropDown.CogenPrimaryUse;
import com.inTrack.spring.entity.equipmentEntity.CogenTurbineDropDown.TypeOfControlEfficiency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cogen_engine_turbine")
public class CogenEngineTurbine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "engine_id", nullable = false)
    private Long engineId;

    @Column(name = "floor")
    private Long floor;

    @Column(name = "location")
    private String location;

    @Column(name = "unique_id")
    private String uniqueId;

    @Column(name = "make")
    private String make;

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

    @Column(name = "application_id")
    private String applicationId;

    @Column(name = "application_type")
    private String applicationType;

    @Column(name = "number_of_identical_units")
    private int numberOfIdenticalUnits;

    @Column(name = "engine_has_control_system")
    private boolean engineHasControlSystem;

    @Column(name = "model")
    private String model;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "burner_make")
    private String burnerMake;

    @Column(name = "burner_model")
    private String burnerModel;

    @Column(name = "burner_serial_number")
    private String burnerSerialNumber;

    @Column(name = "capacity")
    private Double capacity;

    @Column(name = "burner_type")
    private String burnerType;

    @Column(name = "firing_rate_oil")
    private Double firingRateOil;

    @Column(name = "firing_rate_natural_gas")
    private Double firingRateNaturalGas;

    @Column(name = "primary_fuel_from_tank")
    private String primaryFuelFromTank;

    @Column(name = "secondary_fuel_from_tank")
    private String secondaryFuelFromTank;

    @Column(name = "stack_exhausting")
    private String stackExhausting;

    @Column(name = "is_turbine_with_unit")
    private boolean isTurbineWithUnit;

    @Column(name = "is_waste_hrb_with_unit")
    private boolean isWasteHrbWithUnit;

    @Column(name = "fuel_capping")
    private boolean fuelCapping;

    @Column(name = "gas_fuel_capping")
    private Double gasFuelCapping;

    @Column(name = "gas_nox_emission_factor")
    private Double gasNoxEmissionFactor;

    @Column(name = "oil_fuel_capping")
    private Double oilFuelCapping;

    @Column(name = "oil_nox_emission_factor")
    private Double oilNoxEmissionFactor;

    @Column(name = "so2_emission_factor_oil")
    private Double so2EmissionFactorOil;

    @Column(name = "so2_emission_factor_gas")
    private Double so2EmissionFactorGas;

    @Column(name = "so2_allowable")
    private Double so2Allowable;

    @Column(name = "nox_allowable")
    private Double noxAllowable;

    @Column(name = "comments")
    private String comments;

    @ManyToOne
    @JoinColumn(name = "primary_use_id")
    private CogenPrimaryUse primaryUse;

    @ManyToOne
    @JoinColumn(name = "type_of_control_efficiency_id")
    private TypeOfControlEfficiency typeOfControlEfficiency;

    @ManyToOne
    @JoinColumn(name = "primary_fuel_id")
    private FuelType primaryFuel;

    @ManyToOne
    @JoinColumn(name = "secondary_fuel_id")
    private FuelType secondaryFuel;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private ElevatorStatus status;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @ManyToOne
    @JoinColumn(name = "job_filing_information_id")
    private JobFilingInformation jobFilingInformation;
}
