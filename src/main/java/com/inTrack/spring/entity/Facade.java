package com.inTrack.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "facade")
public class Facade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facade_id")
    private Long facadeId;

    @Column(name = "is_facade_inspection_available")
    private Boolean isFacadeInspectionAvailable = false;

    @Column(name = "filing_number")
    private String filingNumber;

    @Column(name = "facade_period")
    private Long facadePeriod;

    @Column(name = "cycle")
    private Long cycle;

    @Column(name = "number_of_stories")
    private Long numberOfStories;

    @Column(name = "exterior_wall_type")
    private String exteriorWallType;

    @Column(name = "initial_filing_date")
    private Long initialFilingDate;

    @Column(name = "initial_filing_status")
    private String initialFilingStatus;

    @Column(name = "last_cycle_filing_date")
    private Long lastCycleFilingDate;

    @Column(name = "prior_status")
    private String priorStatus;

    @Column(name = "field_inspection_completed_date")
    private Long fieldInspectionCompletedDate;

    @Column(name = "signed_date")
    private Long signedDate;

    @Column(name = "next_cycle_filing_date")
    private Long nextCycleFilingDate;

    @Column(name = "current_status")
    private String currentStatus;

    @Column(name = "applicant_name")
    private String applicantName;

    @Column(name = "license")
    private String license;

    @Column(name = "note")
    private String note;

    @Column(name = "created_at")
    private Long createAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;
}
