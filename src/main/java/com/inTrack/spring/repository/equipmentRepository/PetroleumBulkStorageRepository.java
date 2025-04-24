package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.equipmentEntity.LandFill;
import com.inTrack.spring.entity.equipmentEntity.PetroleumBulkStorage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetroleumBulkStorageRepository extends JpaRepository<PetroleumBulkStorage, Long> {
    PetroleumBulkStorage findByPetroleumStorageId(Long petroleumStorageId);

    List<PetroleumBulkStorage> findAllByBuilding(Building building);

    List<PetroleumBulkStorage> findAllByBuildingAndFloor(Building building, Long floor);

    PetroleumBulkStorage findByPetroleumStorageIdAndIsActive(Long petroleumStorageId, boolean activeStatus);

    Long countByUniqueId(String uniqueId);
}
