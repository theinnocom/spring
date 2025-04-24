package com.inTrack.spring.entity.equipmentEntity.agencyEntity;

import com.inTrack.spring.entity.equipmentEntity.IncineratorCrematories;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.IncineratorCrematoriesAgency;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.ParameterType;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "incinerator_crematories_agency_info")
public class IncineratorCrematoriesAgencyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "dep_number")
    private String depNumber;

    @Column(name = "issue_date")
    private Long  issueDate;

    @Column(name = "expiration_date")
    private Long expirationDate;

    @Column(name = "status")
    private String status;

    @Column(name = "dec_number")
    private String decNumber;

    @Column(name = "dec_issue_date")
    private Long decIssueDate;

    @Column(name = "dec_expiration_date")
    private Long decExpirationDate;

    @Column(name = "is_solid_waste_permit_required")
    private Boolean isSolidWastePermitRequired;

    @Column(name = "stack_last_test_date")
    private Long stackLastTestDate;

    @Column(name = "stack_next_test_date")
    private Long stackNextTestDate;

    @Column(name = "stack_test_protocol_submitted")
    private boolean stackTestProtocolSubmitted;

    @ManyToOne
    @JoinColumn(name = "parameter_type_id")
    private ParameterType parameterTypes;

    @Column(name = "stack_test_passed")
    private boolean stackTestPassed;

    @Column(name = "test_conducted_by")
    private String testConductedBy;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "incinerator_crematories_agency_id")
    private IncineratorCrematoriesAgency incineratorCrematoriesAgency;

    @ManyToOne
    @JoinColumn(name = "incinerator_id")
    private IncineratorCrematories incineratorCrematories;
}
