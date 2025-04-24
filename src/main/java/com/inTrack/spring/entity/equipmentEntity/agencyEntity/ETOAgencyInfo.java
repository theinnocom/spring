package com.inTrack.spring.entity.equipmentEntity.agencyEntity;

import com.inTrack.spring.entity.equipmentEntity.ETO;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "eto_agency_info")
public class ETOAgencyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dep_number", nullable = false, unique = true)
    private String depNumber;

    @Column(name = "issue_date", nullable = false)
    private Long issueDate;

    @Column(name = "expiration_date", nullable = false)
    private Long expirationDate;

    @Column(name = "status")
    private String status;

    @Column(name = "note")
    private String note;

    @Column(name = "dec_permit_obtained", nullable = false)
    private String decPermitObtained;

    @Column(name = "stack_test_required", nullable = false)
    private String stackTestRequired;

    @Column(name = "stack_test_date")
    private Long stackTestDate;

    @Column(name = "next_stack_test_date")
    private Long nextStackTestDate;

    @Column(name = "test_conducted_by")
    private String testConductedBy;

    @Column(name = "stack_test_protocol_submitted")
    private String stackTestProtocolSubmitted;

    @ManyToOne
    @JoinColumn(name = "eto_id")
    private ETO eto;
}
