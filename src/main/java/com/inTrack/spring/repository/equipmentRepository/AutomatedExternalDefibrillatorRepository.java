package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.equipmentEntity.AutomatedExternalDefibrillator;
import com.inTrack.spring.entity.equipmentEntity.LandFill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutomatedExternalDefibrillatorRepository extends JpaRepository<AutomatedExternalDefibrillator, Long> {
    AutomatedExternalDefibrillator findByExternalDefibrillatorId(Long externalDefibrillatorId);

    List<AutomatedExternalDefibrillator> findAllByBuilding(Building building);

    List<AutomatedExternalDefibrillator> findAllByBuildingAndFloor(Building building, Long floor);

    AutomatedExternalDefibrillator findByExternalDefibrillatorIdAndIsActive(Long externalDefibrillatorId, boolean activeStatus);
}
