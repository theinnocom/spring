package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.equipmentEntity.CoolingTower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoolingTowerRepository extends JpaRepository<CoolingTower, Long> {
    CoolingTower findByCoolingTowerId(Long coolingTowerId);

    List<CoolingTower> findAllByBuilding(Building building);

    List<CoolingTower> findAllByBuildingAndFloor(Building building, Long floor);

    CoolingTower findByCoolingTowerIdAndIsActive(Long collingTowerId, boolean activeStatus);

    Long countByUniqueId(String uniqueId);
}
