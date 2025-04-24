package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.equipmentEntity.Elevator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author vijayan
 */

public interface ElevatorRepository extends JpaRepository<Elevator, Long> {
    Elevator findByElevatorIdAndIsActive(Long elevatorId, Boolean activeStatus);
    List<Elevator> findAllByBuilding(Building building);

    List<Elevator> findAllByBuildingAndFloor(Building building, Long floor);

    Elevator findByElevatorId(Long elevatorId);

    Long countByUniqueId(String uniqueId);
}
