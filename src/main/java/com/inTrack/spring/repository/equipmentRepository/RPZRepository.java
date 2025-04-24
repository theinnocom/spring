package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.equipmentEntity.ETO;
import com.inTrack.spring.entity.equipmentEntity.RPZ;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RPZRepository extends JpaRepository<RPZ, Long> {
    RPZ findByRPZId(Long RPZId);

    List<RPZ> findAllByBuilding(Building building);

    List<RPZ> findAllByBuildingAndFloor(Building building, Long floor);

    RPZ findByRPZIdAndIsActive(Long RPZId, boolean activeStatus);

    Long countByUniqueId(String uniqueId);
}
