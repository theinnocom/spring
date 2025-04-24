package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.equipmentEntity.Elevator;
import com.inTrack.spring.entity.equipmentEntity.Miscellaneous;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MiscellaneousRepository extends JpaRepository<Miscellaneous, Long> {
    Miscellaneous findByIdAndIsActive(Long id, boolean b);

    Long countByUniqueId(String uniqueId);

    List<Miscellaneous> findAllByBuilding(Building building);

    List<Miscellaneous> findAllByBuildingAndFloor(Building building, Long floor);
}
