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
@Table(name = "fume_hood")
public class FumeHood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fume_hood_id", nullable = false)
    private Long fumeHoodId;

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

    @Column(name = "type")
    private String type;

    @Column(name = "hours_of_operation")
    private Double HoursOfOperation;

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
    @JoinColumn(name = "management_id")
    private ManagementType management;

    @ManyToOne
    @JoinColumn(name = "job_filing_information_id")
    private JobFilingInformation jobFilingInformation;
}
