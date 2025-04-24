package com.inTrack.spring.entity.equipmentEntity.agencyEntity;

import com.inTrack.spring.entity.equipmentEntity.PetroleumBulkStorage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "petroleum_bulk_storage_agency")
public class PetroleumBulkStorageAgency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dec_permit")
    private String decPermit;

    @Column(name = "pbs_expiration_date")
    private Long pbsExpirationDate;

    @Column(name = "tank_tightness_test_required")
    private boolean tankTightnessTestRequired;

    @Column(name = "tank_tightness_test_date")
    private Long tankTightnessTestDate;

    @Column(name = "next_tank_tightness_test_date")
    private Long nextTankTightnessTestDate;

    @Column(name = "tank_tightness_test_notes")
    private String tankTightnessTestNotes;

    @Column(name = "annual_cathodic_protection_test_required")
    private boolean annualCathodicProtectionTestRequired;

    @Column(name = "annual_cathodic_protection_test_date")
    private Long annualCathodicProtectionTestDate;

    @Column(name = "next_annual_cathodic_protection_test_date")
    private Long nextAnnualCathodicProtectionTestDate;

    @Column(name = "annual_cathodic_protection_test_notes")
    private String annualCathodicProtectionTestNotes;

    @Column(name = "overfill_alarm_inspection_required")
    private boolean overfillAlarmInspectionRequired;

    @Column(name = "last_overfill_alarm_inspection_date")
    private Long lastOverfillAlarmInspectionDate;

    @Column(name = "next_overfill_alarm_inspection_date")
    private Long nextOverfillAlarmInspectionDate;

    @Column(name = "overfill_alarm_inspection_notes")
    private String overfillAlarmInspectionNotes;

    @Column(name = "spill_prevention_test_required")
    private boolean spillPreventionTestRequired;

    @Column(name = "last_spill_prevention_test_date")
    private Long lastSpillPreventionTestDate;

    @Column(name = "next_spill_prevention_test_date")
    private Long nextSpillPreventionTestDate;

    @Column(name = "spill_prevention_test_notes")
    private String spillPreventionTestNotes;

    @Column(name = "leak_detection_required")
    private boolean leakDetectionRequired;

    @Column(name = "last_leak_detection_date")
    private Long lastLeakDetectionDate;

    @Column(name = "next_leak_detection_date")
    private Long nextLeakDetectionDate;

    @Column(name = "leak_detection_notes")
    private String leakDetectionNotes;

    @Column(name = "epa_permit")
    private String epaPermit;

    @Column(name = "cfr_40_280_required")
    private boolean cfr40_280Required;

    @Column(name = "hydrostatic_testing_last_date")
    private Long hydrostaticTestingLastDate;

    @Column(name = "hydrostatic_testing_next_date")
    private Long hydrostaticTestingNextDate;

    @Column(name = "overfill_functionality_test_last_date")
    private Long overfillFunctionalityTestLastDate;

    @Column(name = "overfill_functionality_test_next_date")
    private Long overfillFunctionalityTestNextDate;

    @Column(name = "annual_walk_through_completed")
    private boolean annualWalkThroughCompleted;

    @Column(name = "notes")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "petroleum_storage_id")
    private PetroleumBulkStorage petroleumBulkStorage;
}
