package com.inTrack.spring.repository.AgencyRepo;

import com.inTrack.spring.entity.equipmentEntity.PetroleumBulkStorage;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.PetroleumBulkStorageAgency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetroleumBulkOxygenAgencyRepository extends JpaRepository<PetroleumBulkStorageAgency, Long> {
    List<PetroleumBulkStorageAgency> findByPetroleumBulkStorage(PetroleumBulkStorage petroleumBulkStorage);
}
