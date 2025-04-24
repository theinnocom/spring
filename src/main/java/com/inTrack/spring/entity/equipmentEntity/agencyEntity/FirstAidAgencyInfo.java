package com.inTrack.spring.entity.equipmentEntity.agencyEntity;

import com.inTrack.spring.entity.equipmentEntity.FirstAidKit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "first_aid_agency_info")
public class FirstAidAgencyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "monthly_inspection_date")
    private Long monthlyInspectionDate;

    @Column(name = "monthly_inspected_by")
    private String monthlyInspectedBy;

    @Column(name = "annual_inspection_date")
    private Long annualInspectionDate;

    @Column(name = "annual_inspected_by")
    private String annualInspectedBy;

    @Column(name = "monthly_note")
    private String monthly_note;


    @Column(name = "annual_note")
    private String annual_note;

    @ManyToOne
    @JoinColumn(name = "first_aid_kit_id")
    private FirstAidKit firstAidKit;
}
