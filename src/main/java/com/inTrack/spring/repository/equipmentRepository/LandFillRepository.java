package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.equipmentEntity.FumeHood;
import com.inTrack.spring.entity.equipmentEntity.LandFill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LandFillRepository extends JpaRepository<LandFill, Long> {
    LandFill findByLandFillId(Long LandFillId);

    List<LandFill> findAllByBuilding(Building building);

    List<LandFill> findAllByBuildingAndFloor(Building building, Long floor);

    LandFill findByLandFillIdAndIsActive(Long LandFillId, boolean activeStatus);
}
