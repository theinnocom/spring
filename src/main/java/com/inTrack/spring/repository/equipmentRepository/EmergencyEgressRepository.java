package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.equipmentEntity.EmergencyEgressAndLighting;
import com.inTrack.spring.entity.equipmentEntity.LandFill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmergencyEgressRepository extends JpaRepository<EmergencyEgressAndLighting, Long> {
    EmergencyEgressAndLighting findByEmergencyEgressId(Long emergencyEgressId);

    List<EmergencyEgressAndLighting> findAllByBuilding(Building building);

    List<EmergencyEgressAndLighting> findAllByBuildingAndFloor(Building building, Long floor);

    EmergencyEgressAndLighting findByEmergencyEgressIdAndIsActive(Long emergencyEgressId, boolean activeStatus);

    Long countByUniqueId(String uniqueId);
}
