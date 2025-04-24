package com.inTrack.spring.entity.equipmentEntity;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "printing_press")
public class PrintingPress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "printer_id", nullable = false)
    private Long printerId;

    @Column(name = "floor")
    private Long floor;

    @Column(name = "unique_id", unique = true)
    private String uniqueId;

    @Column(name = "location")
    private String location;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "application_id")
    private String applicationId;

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

    @Column(name = "total_voc")
    private double totalVOC;

    @Column(name = "stack_exhaust_search_button")
    private String stackExhaustSearchButton;

    @Column(name = "stack_exhaust_height")
    private double stackExhaustHeight;

    @Column(name = "stack_exhaust_diameter")
    private double stackExhaustDiameter;

    @Column(name = "stack_exhaust_velocity")
    private double stackExhaustVelocity;

    @Column(name = "filter_used_type")
    private String filterUsedType;

    @Column(name = "efficiency_percentage")
    private double efficiencyPercentage;

    @Column(name = "ink_or_solvent_usage_cap")
    private boolean inkOrSolventUsageCap;

    @Column(name = "permitted_max_voc")
    private Double permittedMaxVOC;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private ElevatorStatus status;

    @ManyToOne
    @JoinColumn(name = "management_id")
    private ManagementType management;

    @ManyToOne
    @JoinColumn(name = "stack_id")
    private Stack stack;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @ManyToOne
    @JoinColumn(name = "job_filing_information_id")
    private JobFilingInformation jobFilingInformation;
}
