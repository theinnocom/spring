package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.equipmentEntity.Stack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StackRepository extends JpaRepository<Stack, Long> {
    Stack findByStackId(Long stackId);

    Stack findByStackIdAndIsActive(Long id, boolean activeStatus);

    List<Stack> findAllByBuilding(Building building);
    List<Stack> findAllByBuildingAndFloor(Building building, Long floor);

    Long countByUniqueId(String uniqueId);
}
