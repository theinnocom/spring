package com.inTrack.spring.entity.equipmentEntity;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.FuelType;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.boilerDropDown.BoilerPressureType;
import com.inTrack.spring.entity.equipmentEntity.boilerDropDown.PrimaryUse;
import com.inTrack.spring.entity.equipmentEntity.boilerDropDown.TestOnFuel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "boiler")
@Entity
public class Boiler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boiler_id", nullable = false)
    private Long boilerId;

    @Column(name = "unique_id", unique = true)
    private String uniqueId;

    @Column(name = "floor")
    private Long floor;

    @Column(name = "location")
    private String location;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "make_by")
    private String makeBy;

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

    @Column(name = "management_note")
    private String managementNote;

    @Column(name = "device_type")
    private String deviceType;

    @Column(name = "application_id")
    private String applicationId;

    @Column(name = "application_type")
    private String applicationType;

    @Column(name = "boiler_make")
    private String boilerMake;

    @Column(name = "boiler_model")
    private String boilerModel;

    @Column(name = "burner_make")
    private String burnerMake;

    @Column(name = "burner_model")
    private String burnerModel;

    @Column(name = "burner_serial_number")
    private String burnerSerialNumber;

    @Column(name = "number_of_identical_units")
    private Long numberOfIdenticalUnits;

    @Column(name = "capacity")
    private Double capacity;

    @Column(name = "mea_number")
    private String meaNumber;

    @Column(name = "dep_number")
    private String depNumber;

    @Column(name = "dep_status")
    private String depStatus;

    @Column(name = "dec_source_id")
    private String decSourceId;

    @Column(name = "dob_device_number")
    private String dobDeviceNumber;

    @Column(name = "firing_rate_oil")
    private Double firingRateOil;

    @Column(name = "firing_rate_natural_gas")
    private Double firingRateNaturalGas;

    @Column(name = "primary_fuel_tank")
    private String primaryFuelTank;

    @Column(name = "secondary_fuel_tank")
    private String secondaryFuelTank;

    @Column(name = "stack_exhausting")
    private String stackExhausting;

    @Column(name = "fire_department_approval")
    private Boolean fireDepartmentApproval;

    @Column(name = "schedule_c")
    private Boolean scheduleC;

    @Column(name = "plan_approval")
    private Boolean planApproval;

    @Column(name = "parameter")
    private String parameter;

    @Column(name = "octr_number")
    private String octrNumber;

    @Column(name = "opacity_monitor_installed")
    private Boolean opacityMonitorInstalled;

    @Column(name = "performance_test_protocol_submitted_to_dec")
    private Boolean performanceTestProtocolSubmitted;

    @Column(name = "performance_test_protocol_date")
    private Long performanceTestProtocolDate;

    @Column(name = "performance_test_report_submitted_to_dep")
    private Boolean performanceTestReportSubmitted;

    @Column(name = "performance_test_report_date")
    private Long performanceTestReportDate;

    @Column(name = "sulphur_content_sampled")
    private Boolean sulphurContentSampled;

    @Column(name = "sulphur_content_percentage")
    private Double sulphurContentPercentage;

    @Column(name = "nitrogen_content_required")
    private Boolean nitrogenContentRequired;

    @Column(name = "nitrogen_content_percentage")
    private Double nitrogenContentPercentage;

    @Column(name = "nsps_subject")
    private Boolean nspsSubject;

    @Column(name = "modified_in_past")
    private Boolean modifiedInPast;

    @Column(name = "emission_credit_required")
    private Boolean emissionCreditRequired;

    @Column(name = "federal_psd_subject")
    private Boolean federalPSDSubject;

    @Column(name = "other_boiler_combined")
    private Boolean otherBoilerCombined;

    @Column(name = "fuel_capping")
    private Boolean fuelCapping;

    @Column(name = "gas_fuel_capping")
    private Double gasFuelCapping;

    @Column(name = "gas_nox_emission_factor")
    private Double gasNOxEmissionFactor;

    @Column(name = "oil_fuel_capping")
    private Double oilFuelCapping;

    @Column(name = "oil_nox_emission_factor")
    private Double oilNOxEmissionFactor;

    @Column(name = "so2_emission_factor_oil")
    private Double so2EmissionFactorOil;

    @Column(name = "so2_emission_factor_gas")
    private Double so2EmissionFactorGas;

    @Column(name = "so2_allowable_tons_per_year")
    private Double so2AllowableTonsPerYear;

    @Column(name = "nox_allowable_tons_per_year")
    private Double noxAllowableTonsPerYear;

    @Column(name = "comments")
    private String comments;

    @ManyToOne
    @JoinColumn(name = "management_id")
    private ManagementType management;

    @Column(name = "disconnected_on")
    private Long disconnectedOn;

    @ManyToOne
    @JoinColumn(name = "test_on_fuel_id")
    private TestOnFuel testOnFuel;

    @ManyToOne
    @JoinColumn(name = "boiler_pressure_type_id")
    private BoilerPressureType boilerPressureType;

    @ManyToOne
    @JoinColumn(name = "primary_use_id")
    private PrimaryUse primaryUse;

    @ManyToOne
    @JoinColumn(name = "secondary_fuel_id")
    private FuelType secondaryFuel;

    @ManyToOne
    @JoinColumn(name = "primary_fuel_id")
    private FuelType primaryFuel;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private ElevatorStatus status;

    @ManyToOne
    @JoinColumn(name = "job_filing_information_id")
    private JobFilingInformation jobFilingInformation;
}
