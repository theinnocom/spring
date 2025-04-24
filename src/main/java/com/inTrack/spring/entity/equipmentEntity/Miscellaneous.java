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
@Table(name = "miscellaneous")
public class Miscellaneous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "unique_id", nullable = false, unique = true)
    private String uniqueId;

    @Column(name = "floor")
    private Long floor;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "location")
    private String location;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "installed_on")
    private Long installedOn;

    @Column(name = "installed_by")
    private Long installedBy;

    @ManyToOne
    @JoinColumn(name = "management_id")
    private ManagementType management;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private ElevatorStatus status;

    @Column(name = "managed_by")
    private String managedBy;

    @Column(name = "management_note")
    private String managementNote;

    @Column(name = "disconnected_on")
    private Long disconnectedOn;

    @Column(name = "application_id")
    private String applicationId;

    @Column(name = "equipment_type")
    private String equipmentType;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "capacity")
    private String capacity;

    @Column(name = "number_of_identical_units")
    private Integer numberOfIdenticalUnits;

    @Column(name = "connected_with_other_equipment")
    private Boolean connectedWithOtherEquipment;

    @Column(name = "note")
    private String note;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @Column(name = "agency_id")
    private String agencyId;

    @Column(name = "issue_date")
    private Long issueDate;

    @Column(name = "agency_status")
    private String agencyStatus;

    @Column(name = "expiration_date")
    private Long expirationDate;

    @Column(name = "submitted_date")
    private Long submittedDate;

    @ManyToOne
    @JoinColumn(name = "agency_name_id")
    private AgencyType agencyName;

    @Column(name = "agency_note")
    private String agencyNote;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @ManyToOne
    @JoinColumn(name = "job_filing_information_id")
    private JobFilingInformation jobFilingInformation;

}
