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
@Table(name = "automated_external_defibrillator")
public class AutomatedExternalDefibrillator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "external_defibrillator_id", nullable = false)
    private Long externalDefibrillatorId;

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

    @Column(name = "model")
    private String model;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "device_type")
    private String deviceType;

    @Column(name = "disconnected_on")
    private Long disconnectedOn;

    @Column(name = "management_note")
    private String managementNote;

    @Column(name = "capacity")
    private String capacity;

    @Column(name = "ed_battery_date")
    private Long edBatteryDate; // Expires 5 years from the date

    @Column(name = "defib_pad_expiration_date")
    private Long defibPadExpirationDate;

    @Column(name = "inspection")
    private String inspection; // Monthly, Quarterly

    @Column(name = "next_inspection_date")
    private Long nextInspectionDate;

    @Column(name = "battery_and_cabinet_alarm_change_date")
    private Long batteryAndCabinetAlarmChangeDate;

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
