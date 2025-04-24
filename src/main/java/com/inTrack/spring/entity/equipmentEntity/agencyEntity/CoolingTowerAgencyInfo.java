package com.inTrack.spring.entity.equipmentEntity.agencyEntity;

import com.inTrack.spring.entity.equipmentEntity.CoolingTower;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.CoolingTowerAgency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cooling_tower_agency_info")
public class CoolingTowerAgencyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_certification_date")
    private Long lastCertificationDate;

    @Column(name = "next_certification_date")
    private Long nextCertificationDate;

    @Column(name = "certified_by")
    private String certifiedBy;

    @Column(name = "last_inspection_date")
    private Long lastInspectionDate;

    @Column(name = "next_inspection_date")
    private Long nextInspectionDate;

    @Column(name = "inspected_by")
    private String inspectedBy;

    @Column(name = "last_test_date")
    private Long lastTestDate;

    @Column(name = "next_test_date")
    private Long nextTestDate;

    @Column(name = "tested_by")
    private String testedBy;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "cooling_tower_id")
    private CoolingTower coolingTower;

    @ManyToOne
    @JoinColumn(name = "cooling_tower_agency_id")
    private CoolingTowerAgency coolingTowerAgency;
}
