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
@Table(name = "kitchen_hood_fire_suppression")
public class KitchenHoodFireSuppression {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kitchen_hood_id", nullable = false)
    private Long kitchenHoodId;

    @Column(name = "unique_id", unique = true, nullable = false)
    private String uniqueId;

    @Column(name = "floor")
    private Long floor;

    @Column(name = "location")
    private String location;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "device_type")
    private String deviceType;

    @Column(name = "disconnected_on")
    private Long disconnectedOn;

    @Column(name = "management_note")
    private String managementNote;

    @Column(name = "make")
    private String make;

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

    @Column(name = "is_portable_fire_extinguisher")
    private Boolean isPortableFireExtinguisher = false;

    @Column(name = "internal_management")
    private String internalManagement;

    @Column(name = "regulatory_authority")
    private String regulatoryAuthority;

    @Column(name = "permit_number")
    private String permitNumber;

    @Column(name = "permit_date")
    private Long permitDate;

    @Column(name = "is_inspection_tag_attached")
    private String isInspectionTagAttached;

    @Column(name = "inspection_test_frequency")
    private String inspectionTestFrequency;

    @ManyToOne
    @JoinColumn(name = "management_id")
    private ManagementType management;

    @ManyToOne
    @JoinColumn(name = "type_of_suppression_id")
    private TypeOfSuppression typeOfSuppression;

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
