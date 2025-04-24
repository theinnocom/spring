package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.equipmentEntity.BulkOxygenStorage;
import com.inTrack.spring.entity.equipmentEntity.LandFill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BulkOxygenStorageRepository extends JpaRepository<BulkOxygenStorage, Long> {
    BulkOxygenStorage findByOxygenStorageId(Long oxygenStorageId);

    List<BulkOxygenStorage> findAllByBuilding(Building building);

    List<BulkOxygenStorage> findAllByBuildingAndFloor(Building building, Long floor);

    BulkOxygenStorage findByOxygenStorageIdAndIsActive(Long oxygenStorageId, boolean activeStatus);

    Long countByUniqueId(String uniqueId);
}
