package com.inTrack.spring.entity.equipmentEntity;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.User;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.DeviceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author vijayan
 */

@Entity
@Getter
@Setter
@Table(name = "elevator")
public class Elevator {

    @Id
    @Column(name = "elevator_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long elevatorId;

    @Column(name = "unique_id", unique = true, nullable = false)
    private String uniqueId;

    @Column(name = "type_of_use")
    private String typeOfUse;

    @Column(name = "floor_from")
    private Long floorFrom;

    @Column(name = "floor_to")
    private Long floorTo;

    @Column(name = "floor")
    private Long floor;

    @Column(name = "location")
    private String location;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "safety_type")
    private String safetyType;

    @Column(name = "installed_on")
    private Long installedOn;

    @OneToOne
    @JoinColumn(name = "installed_by")
    private User installedBy;

    @Column(name = "managed_by")
    private String managedBy;

    @Column(name = "management_note")
    private String managementNote;

    @ManyToOne
    @JoinColumn(name = "management_id")
    private ManagementType managementType;

    @OneToOne
    @JoinColumn(name = "elevator_status_id", nullable = false)
    private ElevatorStatus elevatorStatus;

    @Column(name = "comments")
    private String comments;

    @Column(name = "elevator_job_no", unique = true, nullable = false)
    private String elevatorJobNo;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "capacity")
    private String capacity;

    @Column(name = "serial")
    private Long serial;

    @Column(name = "governor_type")
    private String governorType;

    @Column(name = "machine_type")
    private String machineType;

    @Column(name = "mode_of_operation")
    private String modeOfOperation;

    @Column(name = "phone_inspection")
    private String phoneInspection;

    @Column(name = "disconnected_on")
    private Long disconnectedOn;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @OneToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @OneToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @ManyToOne
    @JoinColumn(name = "device_type_id")
    private DeviceType deviceType;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @ManyToOne
    @JoinColumn(name = "job_filing_information_id")
    private JobFilingInformation jobFilingInformation;
}
