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
@Table(name = "cooling_tower")
public class CoolingTower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cooling_tower_id", nullable = false)
    private Long coolingTowerId;

    @Column(name = "unique_id", unique = true)
    private String uniqueId;

    @Column(name = "floor")
    private Long floor;

    @Column(name = "location")
    private String location;

    @Column(name = "landmark")
    private String landmark;

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

    @Column(name = "management_note")
    private String managementNote;

    @Column(name = "device_type")
    private String deviceType;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "cooling_capacity")
    private Double coolingCapacity;

    @Column(name = "cooling_capacity_unit")
    private String coolingCapacityUnit;

    @Column(name = "basin_capacity")
    private Double basinCapacity;

    @Column(name = "basin_capacity_unit")
    private String basinCapacityUnit;

    @Column(name = "intended_use")
    private String intendedUse;

    @Column(name = "equipment_name")
    private String equipmentName;

    @Column(name = "number_of_cells")
    private Long numberOfCells;

    @Column(name = "state_registration_completed")
    private Boolean stateRegistrationCompleted;

    @Column(name = "state_equipment_id")
    private Long stateEquipmentId;

    @Column(name = "nyc_dob_registration_no")
    private Long nycDobRegistrationNo;

    @Column(name = "commissioning_date")
    private Long commissioningDate;

    @Column(name = "securely_affix_nycdob_registration")
    private Boolean securelyAffixNycdobRegistration;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private ElevatorStatus status;

    @ManyToOne
    @JoinColumn(name = "management_id")
    private ManagementType management;

    @ManyToOne
    @JoinColumn(name = "type_of_colling_tower_id")
    private TypeOfCollingTower typeOfCollingTower;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @ManyToOne
    @JoinColumn(name = "job_filing_information_id")
    private JobFilingInformation jobFilingInformation;
}
