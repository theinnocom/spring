package com.inTrack.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "violation")
public class Violation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "violation_id")
    private Long violationId;

    @Column(name = "violation_no")
    private String violationNo;

    @Column(name = "issued_date")
    private Long issuedDate;

    @Column(name = "hearing_date")
    private Long hearingDate;

    @Column(name = "respondent_name")
    private String respondentName;

    @Column(name = "code")
    private String code;

    @Column(name = "code_section")
    private String codeSection;

    @Column(name = "description")
    private String description;

    @Column(name = "penalty_imposed")
    private String penaltyImposed;

    @Column(name = "paid_amount")
    private Double paidAmount;

    @Column(name = "balance_due")
    private Double balanceDue;

    @Column(name = "hearing_result")
    private String hearingResult;

    @Column(name = "compliance_date")
    private Long complianceDate;

    @Column(name = "note")
    private String note;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @Column(name = "is_delete")
    private Boolean isDelete = false;

    @Column(name = "deletion_expiration_date")
    private Long deletionExpirationDate;

    @Column(name = "hearing_attended")
    private Boolean hearingAttended = false;

    @Column(name = "fine_paid")
    private Boolean finePaid = false;

    @Column(name = "waived")
    private Boolean waived = false;

    @Column(name = "dismissed")
    private Boolean dismissed = false;

    @Column(name = "status_comments")
    private String statusComments;

    @ManyToOne
    @JoinColumn(name = "hearing_status_id")
    private HearingStatus hearingStatus;

    @ManyToOne
    @JoinColumn(name = "issuing_agency_id")
    private IssuingAgency issuingAgency;

    @ManyToOne
    @JoinColumn(name = "certificate_of_correction_id", referencedColumnName = "COC_id")
    private CertificateOfCorrection certificateOfCorrection;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;
}
