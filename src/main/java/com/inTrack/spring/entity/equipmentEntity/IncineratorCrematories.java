package com.inTrack.spring.entity.equipmentEntity;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.IncineratorCrematoriesDropDown.UnitType;
import com.inTrack.spring.entity.equipmentEntity.IncineratorCrematoriesDropDown.WasteTypeBurner;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "incinerator_crematories")
public class IncineratorCrematories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "floor")
    private Long floor;

    @Column(name = "location")
    private String location;

    @Column(name = "make")
    private String make;

    @Column(name = "unique_id")
    private String uniqueId;

    @Column(name = "device_type")
    private String deviceType;

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

    @ManyToOne
    @JoinColumn(name = "management_id")
    private ManagementType management;

    @Column(name = "management_note")
    private String managementNote;

    @Column(name = "application_id")
    private String applicationId;

    @ManyToOne
    @JoinColumn(name = "unit_type_id")
    private UnitType unitType; // Crematory, Municipal Incinerator, Medical waste Incinerator

    @Column(name = "burner_model")
    private String burnerModel;

    @Column(name = "burner_serial_number")
    private String burnerSerialNumber;

    @Column(name = "burner_capacity")
    private String burnerCapacity;

    @ManyToOne
    @JoinColumn(name = "stack_id")
    private Stack stack;

    @ManyToOne
    @JoinColumn(name = "waste_type_burner_id")
    private WasteTypeBurner wasteTypeBurner; // Cat, Deer, Dog, Medical Waste, Pets, Road Kills

    @Column(name = "scrubber_installed")
    private boolean scrubberInstalled;

    @Column(name = "co_monitor_installed")
    private boolean coMonitorInstalled;

    @Column(name = "opacity_monitor_installed")
    private boolean opacityMonitorInstalled;

    @Column(name = "o2_monitor_installed")
    private boolean o2MonitorInstalled;

    @Column(name = "opacity_chart_recorder_available")
    private boolean opacityChartRecorderAvailable;

    @Column(name = "quarterly_cga_required")
    private boolean quarterlyCgaRequired;

    @Column(name = "temperature_measurement_required")
    private boolean temperatureMeasurementRequired;

    @Column(name = "minimum_temperature_primary")
    private double minimumTemperaturePrimary;

    @Column(name = "minimum_temperature_secondary")
    private double minimumTemperatureSecondary;

    @Column(name = "average_facility_temperature_primary")
    private double averageFacilityTemperaturePrimary;

    @Column(name = "average_facility_temperature_secondary")
    private double averageFacilityTemperatureSecondary;

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
