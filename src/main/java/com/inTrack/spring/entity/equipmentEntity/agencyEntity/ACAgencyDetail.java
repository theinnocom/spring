package com.inTrack.spring.entity.equipmentEntity.agencyEntity;

import com.inTrack.spring.entity.equipmentEntity.AirConditioningUnit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "ac_agency_detail")
public class ACAgencyDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "agency_id")
    private String agencyId;

    @Column(name = "fdny_inspection_date")
    private Long fdnyInspectionDate;

    @Column(name = "fdny_inspected_by")
    private String fdnyInspectedBy;

    @Column(name = "fdny_note")
    private String fdnyNote;

    @Column(name = "eup_card_available")
    private Boolean eupCardAvailable;

    @Column(name = "eup_inspection_date")
    private Long eupInspectionDate;

    @Column(name = "eup_next_inspection_date")
    private Long eupNextInspectionDate;

    @Column(name = "eup_note")
    private String eupNote;

    @Column(name = "epa_submitted_date")
    private Long epaSubmittedDate;

    @Column(name = "is_epa_refrigeration_recovery_registered")
    private Boolean isEPARefrigerationRecoveryRegistered;

    @Column(name = "epa_approval_date")
    private Long epaApprovalDate;

    @Column(name = "epa_note")
    private String epaNote;

    @ManyToOne
    @JoinColumn(name = "ac_unit_id")
    private AirConditioningUnit airConditioningUnit;
}

