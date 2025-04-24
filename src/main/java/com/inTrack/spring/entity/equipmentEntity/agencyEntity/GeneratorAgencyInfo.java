package com.inTrack.spring.entity.equipmentEntity.agencyEntity;

import com.inTrack.spring.entity.equipmentEntity.Generator;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.GeneratorAgency;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "generator_agency_info")
public class GeneratorAgencyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "agency_number")
    private String agencyNumber;

    @Column(name = "issue_date")
    private Long issueDate;

    @Column(name = "expiration_date")
    private Long expirationDate;

    @Column(name = "status")
    private String status;

    @Column(name = "submitted_date")
    private Long submittedDate;

    @Column(name = "note")
    private String note;

    @Column(name = "qualifies_for_dr_program")
    private Boolean qualifiesForDRProgram;

    @Column(name = "stack_last_test_date")
    private Long stackLastTestDate;

    @Column(name = "stack_next_test_date")
    private Long stackNextTestDate;

    @Column(name = "nox_level")
    private String noxLevel;

    @Column(name = "unit_type")
    private String unitType;

    @Column(name = "test_conducted_by")
    private String testConductedBy;

    @Column(name = "other_generator_combined")
    private Boolean otherGeneratorCombined;

    @Column(name = "combined_generator")
    private String combinedGenerator;

    @Column(name = "protocol_submitted_to_dec")
    private Boolean protocolSubmittedToDEC;

    @Column(name = "qualifies_for_doe")
    private Boolean qualifiesForDOE;

    @Column(name = "registered_with_doe")
    private Boolean registeredWithDOE;

    @Column(name = "renewed_with_doe")
    private Boolean renewedWithDOE;

    @ManyToOne
    @JoinColumn(name = "generator_id")
    private Generator generator;

    @ManyToOne
    @JoinColumn(name = "generator_agency_id")
    private GeneratorAgency generatorAgency;
}
