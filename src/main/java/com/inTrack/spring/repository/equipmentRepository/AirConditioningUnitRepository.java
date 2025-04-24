package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.equipmentEntity.AirConditioningUnit;
import com.inTrack.spring.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AirConditioningUnitRepository extends JpaRepository<AirConditioningUnit, Long> {
    AirConditioningUnit findByAcUnitId(Long equipmentId);

    AirConditioningUnit findByAcUnitIdAndIsActive(Long id, boolean activeStatus);

    List<AirConditioningUnit> findAllByBuilding(Building building);
    List<AirConditioningUnit> findAllByBuildingAndFloor(Building building, Long floor);
}
