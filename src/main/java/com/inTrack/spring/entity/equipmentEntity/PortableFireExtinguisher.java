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
@Table(name = "portable_fire_extinguisher")
public class PortableFireExtinguisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fire_extinguisher_id", nullable = false)
    private Long fireExtinguisherId;

    @Column(name = "unique_id", unique = true, nullable = false)
    private String uniqueId;

    @Column(name = "floor")
    private Long floor;

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

    @Column(name = "management_note")
    private String managementNote;

    @Column(name = "disconnected_on")
    private Long disconnectedOn;

    @Column(name = "device_type")
    private String deviceType;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "model")
    private String model;

    @Column(name = "capacity")
    private String capacity;

    @ManyToOne
    @JoinColumn(name = "class_type_id")
    private ClassType classType; // A, B, C, D, K

    @Column(name = "hydrostatic_pressure_test")
    private String hydrostaticPressureTest; // Yes, No, Not Applicable

    @Column(name = "inspection_tag_attached")
    private String inspectionTagAttached; // Yes, No, Not Applicable

    @Column(name = "inspection_hydrostatic_pressure_test")
    private String inspectionHydrostaticPressureTest;

    @Column(name = "inspection_last_test_date")
    private Long inspectionLastTestDate;

    @Column(name = "inspection_next_test_date")
    private Long inspectionNextTestDate;

    @ManyToOne
    @JoinColumn(name = "management_id")
    private ManagementType management;

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
