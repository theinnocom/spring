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
@Table(name = "stack")
public class Stack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stack_id", nullable = false)
    private Long stackId;

    @Column(name = "floor")
    private Long floor;

    @Column(name = "location")
    private String location;

    @Column(name = "unique_id")
    private String uniqueId;

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

    @Column(name = "device_type")
    private String deviceType;

    @ManyToOne
    @JoinColumn(name = "status")
    private ElevatorStatus status;

    @Column(name = "management_note")
    private String managementNote;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "disconnected_on")
    private Long disconnectedOn;

    @ManyToOne
    @JoinColumn(name = "management_id")
    private ManagementType management;

    @Column(name = "application_id")
    private String applicationId;

    @Column(name = "height_feet")
    private double heightFeet;

    @Column(name = "diameter_inches")
    private double diameterInches;

    @Column(name = "number_of_equipments_connected")
    private Long numberOfEquipmentsConnected;

    @Column(name = "total_capacity_of_equipments")
    private double totalCapacityOfEquipments;

    @Column(name = "is_oil_fuel")
    private boolean isOilFuel;

    @Column(name = "is_gas_fuel")
    private boolean isGasFuel;

    @Column(name = "flow_rate")
    private double flowRate;

    @Column(name = "exhaust_temperature")
    private double exhaustTemperature;

    @Column(name = "velocity")
    private double velocity;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @ManyToOne
    @JoinColumn(name = "job_filing_information_id")
    private JobFilingInformation jobFilingInformation;
}
