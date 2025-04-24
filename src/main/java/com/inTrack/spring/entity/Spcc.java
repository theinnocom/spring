package com.inTrack.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "spcc")
public class Spcc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "pbs_number")
    private String pbsNumber;

    @Column(name = "pbs_expiration")
    private Long pbsExpiration;

    @Column(name = "cumulative_ast")
    private Double cumulativeAst;

    @Column(name = "cumulative_ust")
    private Double cumulativeUst;

    @Column(name = "spcc_plan_required")
    private Boolean spccPlanRequired; // true for Yes, false for No

    @Column(name = "last_plan_date")
    private Long lastPlanDate;

    @Column(name = "next_plan_date")
    private Long nextPlanDate;

    @Column(name = "spcc_training_required")
    private Boolean spccTrainingRequired; // true for Yes, false for No

    @Column(name = "last_training_date")
    private Long lastTrainingDate;

    @Column(name = "next_training_date")
    private Long nextTrainingDate;

    @Column(name = "spcc_audit_required")
    private Boolean spccAuditRequired; // true for Yes, false for No

    @Column(name = "der40_certified_staff_count")
    private Integer der40CertifiedStaffCount;

    @Column(name = "job_number")
    private String jobNumber;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "audit_cycle_id")
    private AuditCycle auditCycle; // Possible values: Quarterly, Semi-Annually, Annually

    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;
}
