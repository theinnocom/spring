package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.equipmentEntity.ETO;
import com.inTrack.spring.entity.equipmentEntity.FireAlarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FireAlarmRepository extends JpaRepository<FireAlarm, Long> {
    FireAlarm findByFireAlarmId(Long FireAlarmId);

    List<FireAlarm> findAllByBuilding(Building building);

    List<FireAlarm> findAllByBuildingAndFloor(Building building, Long floor);

    FireAlarm findByFireAlarmIdAndIsActive(Long FireAlarmId, boolean activeStatus);
}
