package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.equipmentEntity.HydraulicStorageTank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HydraulicStorageTankRepository extends JpaRepository<HydraulicStorageTank, Long> {
    HydraulicStorageTank findByIdAndIsActive(Long tankId, boolean b);

    Long countByUniqueId(String uniqueId);

    List<HydraulicStorageTank> findAllByBuilding(Building building);

    List<HydraulicStorageTank> findAllByBuildingAndFloor(Building building, Long floor);
}
