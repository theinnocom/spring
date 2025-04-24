package com.inTrack.spring.entity.equipmentEntity.agencyEntity;

import com.inTrack.spring.entity.equipmentEntity.BulkOxygenStorage;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.BulkOxygenStorageAgency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bulk_oxygen_storage_agency_info")
public class BulkOxygenStorageAgencyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "permit_id", unique = true)
    private String permitId;

    @Column(name = "issue_date")
    private Long issueDate;

    @Column(name = "expiration_date")
    private Long expirationDate;

    @Column(name = "note")
    private String note;

    @Column(name = "fire_department_permit_obtained")
    private String fireDepartmentPermitObtained;

    @Column(name = "test_performed")
    private String testPerformed;

    @Column(name = "last_test_date")
    private Long lastTestDate;

    @Column(name = "next_test_date")
    private Long nextTestDate;

    @ManyToOne
    @JoinColumn(name = "oxygen_storage_agency_id")
    private BulkOxygenStorageAgency bulkOxygenStorageAgency;

    @ManyToOne
    @JoinColumn(name = "oxygen_storage_id")
    private BulkOxygenStorage bulkOxygenStorage;
}
