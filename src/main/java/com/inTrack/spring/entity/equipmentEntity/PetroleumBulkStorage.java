package com.inTrack.spring.entity.equipmentEntity;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.petroleumDropDown.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "petroleum_bulk_storage")
public class PetroleumBulkStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "petroleum_storage_id", nullable = false)
    private Long petroleumStorageId;

    @Column(name = "unique_id", unique = true, nullable = false)
    private String uniqueId;

    @Column(name = "floor")
    private Long floor;

    @Column(name = "location")
    private String location;

    @Column(name = "make")
    private String make;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "device_type")
    private String deviceType;

    @Column(name = "disconnected_on")
    private Long disconnectedOn;

    @Column(name = "management_note")
    private String managementNote;

    @Column(name = "model")
    private String model;

    @Column(name = "capacity")
    private String capacity;

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

    @Column(name = "tank_closed_date")
    private Long tankClosedDate;

    @Column(name = "pbs_number")
    private String pbsNumber;

    @Column(name = "tank_number")
    private String tankNumber;

    @ManyToOne
    @JoinColumn(name = "tank_location_id")
    private TankLocation tankLocation;

    @Column(name = "category")
    private String category;

    @Column(name = "tank_internal_production_other_list")
    private String tankInternalProductionOtherList;

    @Column(name = "tank_external_production_other_list")
    private String tankExternalProductionOtherList;

    @Column(name = "tank_secondary_containment_other_list")
    private String tankSecondaryContainmentOtherList;

    @Column(name = "tank_leak_detection_other_list")
    private String tankLeakDetectionOtherList;

    @Column(name = "over_fill_protection_other_list")
    private String overFillProtectionOtherList;

    @Column(name = "spill_prevention_other_list")
    private String spillPreventionOtherList;

    @Column(name = "pipe_type_other_list")
    private String pipeTypeOtherList;

    @Column(name = "piping_secondary_containment_other_list")
    private String pipingSecondaryContainmentOtherList;

    @Column(name = "piping_leak_detection_other_list")
    private String pipingLeakDetectionOtherList;

    @Column(name = "tank_capacity")
    private Double tankCapacity;

    @Column(name = "piping_internal_production_other_list")
    private String pipingInternalProductionOtherList;

    @Column(name = "tank_type_others_list")
    private String tankTypeOtherList;

    @ManyToOne
    @JoinColumn(name = "product_stored_id")
    private ProductStored productStored;

    @ManyToOne
    @JoinColumn(name = "tank_type_id")
    private TankType tankType;

    @ManyToOne
    @JoinColumn(name = "tank_internal_protection_id")
    private InternalProtection tankInternalProtection;

    @ManyToOne
    @JoinColumn(name = "tank_external_protection_id")
    private ExternalProtection tankExternalProtection;

    @ManyToOne
    @JoinColumn(name = "tank_secondary_containment_id")
    private TankSecondaryContainment tankSecondaryContainment;

    @ManyToOne
    @JoinColumn(name = "tank_leak_detection_id")
    private TankLeakDetection tankLeakDetection;

    @ManyToOne
    @JoinColumn(name = "over_fill_protection_id")
    private OverFillProtection overFillProtection;

    @Column(name = "secondary_overfill")
    private String secondaryOverfill;

    @ManyToOne
    @JoinColumn(name = "spill_prevention_id")
    private SpillPrevention spillPrevention;

    @ManyToOne
    @JoinColumn(name = "dispenser_id")
    private Dispenser dispenser;

    @ManyToOne
    @JoinColumn(name = "pipe_location_id")
    private PipingLocation pipeLocation;

    @ManyToOne
    @JoinColumn(name = "pipe_type_id")
    private PipingType pipeType;

    @ManyToOne
    @JoinColumn(name = "pipe_external_protection_id")
    private ExternalProtection pipeExternalProtection;

    @ManyToOne
    @JoinColumn(name = "piping_secondary_containment_id")
    private PipingSecondaryContainment pipingSecondaryContainment;

    @ManyToOne
    @JoinColumn(name = "piping_leak_detection_id")
    private PipeLeakDetection pipingLeakDetection;

    @Column(name = "udc")
    private String udc;

    @Column(name = "status")
    private String status;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "tank_status_id")
    private TankStatus tankStatus;

    @ManyToOne
    @JoinColumn(name = "subpart_id")
    private Subpart subpart;

    @ManyToOne
    @JoinColumn(name = "management_id")
    private ManagementType management;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @ManyToOne
    @JoinColumn(name = "job_filing_information_id")
    private JobFilingInformation jobFilingInformation;
}
