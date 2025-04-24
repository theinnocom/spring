package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.equipmentEntity.Generator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeneratorRepository extends JpaRepository<Generator, Long> {
    Generator findByGeneratorId(Long equipmentId);

    Generator findByGeneratorIdAndIsActive(Long generatorId, boolean activeStatus);

    List<Generator> findAllByBuilding(Building building);
    List<Generator> findAllByBuildingAndFloor(Building building, Long floor);

    Long countByUniqueId(String uniqueId);
}
