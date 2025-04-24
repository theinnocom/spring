package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.equipmentEntity.Boiler;
import com.inTrack.spring.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoilerRepository extends JpaRepository<Boiler, Long> {
    Boiler findByBoilerIdAndIsActive(Long boilerId, boolean isActive);
    Boiler findByBoilerId(Long equipmentId);

    List<Boiler> findAllByBuilding(Building building);
    List<Boiler> findAllByBuildingAndFloor(Building building, Long floor);

    Long countByUniqueId(String uniqueId);
}
