package com.inTrack.spring.entity.equipmentEntity;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.HydraulicStorageTankDropDown.HydraulicStorageTankUsage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "hydraulic_storage_tank")
public class HydraulicStorageTank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unique_id", unique = true, nullable = false)
    private String uniqueId;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private ElevatorStatus status;

    @Column(name = "floor")
    private Long floor;

    @Column(name = "location")
    private String location;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "installed_on")
    private Long installedOn;

    @Column(name = "installed_by")
    private String installedBy;

    @ManyToOne
    @JoinColumn(name = "management_id")
    private ManagementType management; // Internal/External

    @Column(name = "managed_by")
    private String managedBy;

    @Column(name = "management_note")
    private String managementNote;

    @Column(name = "disconnected_on")
    private Long disconnectedOn;

    @ManyToOne
    @JoinColumn(name = "usage_id")
    private HydraulicStorageTankUsage usage; // Dropdown: Hydraulic oil, Lube oil, Transformer oil, Waste Water, Used oil, other oil

    @Column(name = "secondary_containment")
    private boolean secondaryContainment; // yes, no

    @Column(name = "agency_approval_required")
    private boolean agencyApprovalRequired; // yes, no

    @Column(name = "tank_number")
    private String tankNumber; // Search button (Storage tank)

    @Column(name = "spill_kit_available")
    private boolean spillKitAvailable; // yes, no

    @Column(name = "comments")
    private String comments;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @ManyToOne
    @JoinColumn(name = "building_id", referencedColumnName = "building_id")
    private Building building;

    @ManyToOne
    @JoinColumn(name = "job_filing_information_id")
    private JobFilingInformation jobFilingInformation;
}

