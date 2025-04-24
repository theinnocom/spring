package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.equipmentEntity.CogenEngineTurbine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CogenEngineTurbineRepository extends JpaRepository<CogenEngineTurbine, Long> {
    CogenEngineTurbine findByEngineId(Long equipmentId);

    CogenEngineTurbine findByEngineIdAndIsActive(Long engineId, boolean activeStatus);

    List<CogenEngineTurbine> findAllByBuilding(Building building);
    List<CogenEngineTurbine> findAllByBuildingAndFloor(Building building, Long floor);
}
