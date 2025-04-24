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
@Table(name = "rpz")
public class RPZ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RPZ_id", nullable = false)
    private Long RPZId;

    @Column(name = "unique_id", unique = true, nullable = false)
    private String uniqueId;

    @Column(name = "floor")
    private Long floor;

    @Column(name = "location")
    private String location;

    @Column(name = "make")
    private String make;

    @Column(name = "status")
    private String status;

    @Column(name = "comments")
    private String comments;

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

    @Column(name = "size")
    private String size;

    @Column(name = "meter_reading")
    private String meterReading;

    @Column(name = "water_reading_number")
    private String waterReadingNumber;

    @Column(name = "line_pressure")
    private Double linePressure;

    @Column(name = "type_of_service")
    private String typeOfService;

    @ManyToOne
    @JoinColumn(name = "type_of_rpz_id")
    private TypeOfRpz typeOfRpz;

    @Column(name = "installed_on")
    private Long installedOn;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "installed_by")
    private String installedBy;

    @Column(name = "managed_by")
    private String managedBy;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private ElevatorStatus equipmentStatus;

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
