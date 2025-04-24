package com.inTrack.spring.entity.equipmentEntity.agencyEntity;

import com.inTrack.spring.entity.equipmentEntity.AutomatedExternalDefibrillator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "automated_external_defibrillator_agency_info")
public class AutomatedExternalDefibrillatorAgencyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ed_battery_date")
    private Long edBatteryDate;

    @Column(name = "defib_pads_expiration_date")
    private Long defibPadsExpirationDate;

    @Column(name = "battery_cabinet_tamper_alarm_change_date")
    private Long batteryCabinetTamperAlarmChangeDate;

    @Column(name = "inspection_date")
    private Long inspectionDate;

    @Column(name = "last_inspection_date")
    private Long lastInspectionDate;

    @Column(name = "next_inspection_date")
    private Long nextInspectionDate;

    @Column(name = "inspection_frequency")
    private String inspectionFrequency;

    @Column(name = "notes")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "automated_id")
    private AutomatedExternalDefibrillator automatedExternalDefibrillator;
}
