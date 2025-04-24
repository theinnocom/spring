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
@Table(name = "air_conditioning_unit")
public class AirConditioningUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ac_unit_id", nullable = false)
    private Long acUnitId;

    @Column(name = "unique_id", unique = true, nullable = false)
    private String uniqueId;

    @Column(name = "floor")
    private Long floor;

    @Column(name = "location")
    private String location;

    @Column(name = "make_by")
    private String makeBy;

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

    @Column(name = "FDNY_inspection_date")
    private Long FDNYInspectionDate;

    @Column(name = "EUP_card_available")
    private String EUPCardAvailable;

    @Column(name = "inspection_date")
    private Long inspectionDate;

    @Column(name = "inspector")
    private String inspector;

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

    @Column(name = "refrigeration_recovery_involved")
    private Boolean refrigerationRecoveryInvolved = false;

    @Column(name = "EPA_refrigeration_recovery_involved")
    private Boolean EPARefrigerationRecoveryInvolved = false;

    @Column(name = "EPA_record_maintained")
    private Boolean EPARecordMaintained = false;

    @Column(name = "EPA_submitted_date")
    private Long EPASubmittedDate;

    @Column(name = "EPA_approval_date")
    private Long EPAApprovalDate;

    @Column(name = "type_of_chiller_others_list")
    private String typeOfChillerOthersList;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private ElevatorStatus status;

    @ManyToOne
    @JoinColumn(name = "management_id")
    private ManagementType management;

    @ManyToOne
    @JoinColumn(name = "chiller_type_id")
    private TypeOfChiller typeOfChiller;

    @ManyToOne
    @JoinColumn(name = "chiller_group_id")
    private ChillerGroup chillerGroup;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @ManyToOne
    @JoinColumn(name = "job_filing_information_id")
    private JobFilingInformation jobFilingInformation;
}
