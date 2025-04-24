package com.inTrack.spring.entity.equipmentEntity;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "land_fill")
public class LandFill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "land_fill_id", nullable = false)
    private Long landFillId;

    @Column(name = "floor")
    private Long floor;

    @Column(name = "status")
    private String status;

    @Column(name = "location")
    private String location;

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

    @Column(name = "unique_id", unique = true, nullable = false)
    private String uniqueId;

    @Column(name = "management_note")
    private String managementNote;

    @ManyToOne
    @JoinColumn(name = "management_id")
    private ManagementType management;

    @Column(name = "disconnected_on")
    private Long disconnectedOn;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "application_id")
    private String applicationId;

    @Column(name = "operation_commence_date")
    private String operationCommenceDate;

    @Column(name = "status_of_source")
    private String statusOfSource;

    @Column(name = "linked_to_cogen_or_turbines")
    private boolean linkedToCogenOrTurbines;

    @Column(name = "linked_equipment_list")
    private String linkedEquipmentList;

    @Column(name = "cap_on_lfg_monthly_production_quantity")
    private boolean capOnLfgMonthlyProductionQuantity;

    @Column(name = "lfg_monthly_production_quantity_cap")
    private String lfgMonthlyProductionQuantityCap;

    @Column(name = "cap_on_lfg_combustion_by_cogen_or_turbine_unit")
    private boolean capOnLfgCombustionByCogenOrTurbineUnit;

    @Column(name = "lfg_combustion_cap")
    private String lfgCombustionCap;

    @Column(name = "monitoring_required")
    private boolean monitoringRequired;

    @Column(name = "five_percent_measured_allowable_value")
    private String fivePercentMeasuredAllowableValue;

    @Column(name = "ch4_percent_measured_allowable_value")
    private String ch4PercentMeasuredAllowableValue;

    @Column(name = "flue_gas_temperature_limit")
    private boolean flueGasTemperatureLimit;

    @Column(name = "flue_gas_temperature_limit_tested_allowable_value")
    private String flueGasTemperatureLimitTestedAllowableValue;

    @Column(name = "nox_tested_allowable_emission")
    private String noxTestedAllowableEmission;

    @Column(name = "co_tested_allowable_emission")
    private String coTestedAllowableEmission;

    @Column(name = "others_tested_allowable_emission")
    private String othersTestedAllowableEmission;

    @Column(name = "limit_on_yearly_operating_hours")
    private boolean limitOnYearlyOperatingHours;

    @Column(name = "yearly_operating_hours")
    private String yearlyOperatingHours;

    @Column(name = "comments")
    private String comments;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private ElevatorStatus equipmentStatus;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @ManyToOne
    @JoinColumn(name = "job_filing_information_id")
    private JobFilingInformation jobFilingInformation;
}
