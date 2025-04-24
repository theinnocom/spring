package com.inTrack.spring.repository.AgencyRepo;

import com.inTrack.spring.entity.equipmentEntity.BulkOxygenStorage;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BulkOxygenStorageAgencyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BulkOxygenStorageAgencyInfoRepository extends JpaRepository<BulkOxygenStorageAgencyInfo, Long> {
    List<BulkOxygenStorageAgencyInfo> findByBulkOxygenStorage(BulkOxygenStorage bulkOxygenStorage);
}
