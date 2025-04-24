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
@Table(name = "first_aid_kit")
public class FirstAidKit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unique_id", unique = true, nullable = false)
    private String uniqueId;

    @Column(name = "active_status")
    private Boolean activeStatus;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "floor")
    private Long floor;

    @Column(name = "location")
    private String location;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "installed_on")
    private Long installedOn;

    @Column(name = "installed_by")
    private String installedBy;

    @ManyToOne
    @JoinColumn(name = "management_id")
    private ManagementType management;

    @Column(name = "managed_by")
    private String managedBy;

    @Column(name = "management_note")
    private String managementNote;

    @Column(name = "disconnected_on")
    private Long disconnectedOn;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "kit_used_on")
    private Long kitUsedOn;  // Kit Used on (Long)

    @Column(name = "used_for_incident")
    private String usedFor;  // Used for (Incident)

    @Column(name = "aid_kit_restored_after_use")
    private Boolean aidKitRestoredAfterUse;  // * AID Kit Restored after use (yes, no)

    @Column(name = "kit_restored_date")
    private Long kitRestoredDate;  // Kit Restored date (Long)

    @Column(name = "kit_expiry_date")
    private Long kitExpiryDate;    // Kit Expiry Date (Long)

    @Column(name = "kit_restored_by")
    private String kitRestoredBy;

    @Column(name = "employee_trained")
    private Boolean employeeTrained;  // * Employee Trained? (yes, no)

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "trained_person_names")
    private String trainedPersonNames;

    //Agency information

    @Column(name = "monthly_inspection_date")
    private Long monthlyInspectionDate;

    @Column(name = "inspected_by")
    private String inspectedBy;

    @Column(name = "annual_inspection_date")
    private Long annualInspectionDate;

    @Column(name = "note")
    private String note;

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
