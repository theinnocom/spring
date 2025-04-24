package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.equipmentEntity.FireAlarm;
import com.inTrack.spring.entity.equipmentEntity.FumeHood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FumeHoodRepository extends JpaRepository<FumeHood, Long> {
    FumeHood findByFumeHoodId(Long FumeHoodId);

    List<FumeHood> findAllByBuilding(Building building);

    List<FumeHood> findAllByBuildingAndFloor(Building building, Long floor);

    FumeHood findByFumeHoodIdAndIsActive(Long FumeHoodId, boolean activeStatus);
}
