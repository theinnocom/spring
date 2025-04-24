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
@Table(name = "eto")
public class ETO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ETO_id", nullable = false)
    private Long ETOId;

    @Column(name = "unique_id", unique = true, nullable = false)
    private String uniqueId;

    @Column(name = "floor")
    private Long floor;

    @Column(name = "location")
    private String location;

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

    @Column(name = "application_id")
    private String applicationId;

    @Column(name = "stack_exhausting")
    private String stackExhausting;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "volume_cubic_ft")
    private double volumeCubicFt;

    @ManyToOne
    @JoinColumn(name = "gas_mixture_type_id")
    private GasMixtureType gasMixtureType;

    @Column(name = "weight_of_container")
    private double weightOfContainer;

    @Column(name = "installation_date")
    private Long installationDate;

    @Column(name = "average_use_hours_per_day")
    private double averageUseHoursPerDay;

    @Column(name = "average_use_days_per_week")
    private double averageUseDaysPerWeek;

    @Column(name = "is_abator")
    private Boolean isAbator;

    @Column(name = "abator_make")
    private String abatorMake;

    @Column(name = "abator_model")
    private String abatorModel;

    @Column(name = "daily_records_available")
    private boolean dailyRecordsAvailable;

    @Column(name = "comments")
    private String comments;

    @ManyToOne
    @JoinColumn(name = "management_id")
    private ManagementType management;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private ElevatorStatus status;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @ManyToOne
    @JoinColumn(name = "job_filing_information_id")
    private JobFilingInformation jobFilingInformation;
}
