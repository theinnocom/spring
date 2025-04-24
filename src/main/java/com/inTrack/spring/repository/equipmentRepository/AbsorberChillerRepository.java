package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.equipmentEntity.AbsorberChiller;
import com.inTrack.spring.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AbsorberChillerRepository extends JpaRepository<AbsorberChiller, Long> {
    AbsorberChiller findByChillerId(Long equipmentId);

    AbsorberChiller findByChillerIdAndIsActive(Long equipmentId, boolean activeStatus);

    List<AbsorberChiller> findAllByBuilding(Building building);
    List<AbsorberChiller> findAllByBuildingAndFloor(Building building, Long floor);
}
