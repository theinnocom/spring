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
@Table(name = "fire_alarm")
public class FireAlarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fire_alarm_id", nullable = false)
    private Long fireAlarmId;

    @Column(name = "unique_id", unique = true, nullable = false)
    private String uniqueId;

    @Column(name = "floor")
    private Long floor;

    @Column(name = "location")
    private String location;

    @Column(name = "make_by")
    private String makeBy;

    @Column(name = "model")
    private String model;

    @Column(name = "capacity")
    private String capacity;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "device_type")
    private String deviceType;

    @Column(name = "disconnected_on")
    private Long disconnectedOn;

    @Column(name = "management_note")
    private String managementNote;

    @Column(name = "note")
    private String note;

    @Column(name = "fire_alarm_type")
    private String fireAlarmType;

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

    @Column(name = "transient_suppressors")
    private String transientSuppressors;

    @Column(name = "control_unit_trouble_signals")
    private String controlUnitTroubleSignals;

    @Column(name = "remote_annunciations")
    private String remoteAnnunciations;

    @Column(name = "initiating_devices")
    private String initiatingDevices;

    @Column(name = "guards_tour_equipment")
    private String guardsTourEquipment;

    @Column(name = "interface_equipment")
    private String interfaceEquipment;

    @Column(name = "alarm_notification_appliances")
    private String alarmNotificationAppliances;

    @Column(name = "supervising_station_fire_alarm_system")
    private String supervisingStationFireAlarmSystem;

    @Column(name = "emergency_communications_equipment")
    private String emergencyCommunicationsEquipment;

    @Column(name = "special_procedures")
    private String specialProcedures;

    @Column(name = "last_inspection_date")
    private Long lastInspectionDate;

    @Column(name = "next_inspection_date")
    private Long nextInspectionDate;

    @ManyToOne
    @JoinColumn(name = "management_id")
    private ManagementType management;

    @ManyToOne
    @JoinColumn(name = "fiber_optic_connection_id")
    private FiberOpticConnection fiberOpticConnection;

    @ManyToOne
    @JoinColumn(name = "control_equipment_id")
    private ControlEquipment controlEquipment;

    @ManyToOne
    @JoinColumn(name = "battery_type_id")
    private BatteryType batteryType;

    @ManyToOne
    @JoinColumn(name = "battery_location_id")
    private BatteryLocation batteryLocation;

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
