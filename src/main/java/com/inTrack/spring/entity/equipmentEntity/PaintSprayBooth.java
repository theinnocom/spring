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
@Table(name = "paint_spray_booth")
public class PaintSprayBooth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pain_spray_id", nullable = false)
    private Long painSprayId;

    @Column(name = "unique_id")
    private String uniqueId;

    @Column(name = "floor")
    private Long floor;

    @Column(name = "location")
    private String location;

    @Column(name = "make_by")
    private String makeBy;

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

    @Column(name = "make", nullable = false)
    private String make;

    @ManyToOne
    @JoinColumn(name = "management_id")
    private ManagementType management;

    @Column(name = "management_note", nullable = false)
    private String managementNote;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @Column(name = "hours_of_operation_per_day", nullable = false)
    private int hoursOfOperationPerDay;

    @Column(name = "days_of_operation_per_week", nullable = false)
    private int daysOfOperationPerWeek;

    @Column(name = "total_voc_content_per_month", nullable = false)
    private double totalVocContentPerMonth;

    @Column(name = "total_annual_voc_content", nullable = false)
    private double totalAnnualVocContent;

    @Column(name = "cap_on_voc_content", nullable = false)
    private boolean capOnVocContent;

    @Column(name = "paint_lb_per_gal", nullable = true)
    private Double paintLbPerGal;

    @Column(name = "solvent_lb_per_gal", nullable = true)
    private Double solventLbPerGal;

    @Column(name = "application_id")
    private String applicationId;

    @Column(name = "ink_lb_per_gal", nullable = true)
    private Double inkLbPerGal;

    @Column(name = "monthly_limit_gal_per_month", nullable = true)
    private Double monthlyLimitGalPerMonth;

    @Column(name = "comments", nullable = true)
    private String comments;

    @ManyToOne
    @JoinColumn(name = "stack_id")
    private Stack stack;

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
