package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.equipmentEntity.BatteryLocation;
import com.inTrack.spring.entity.equipmentEntity.ControlEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ControlEquipmentRepository extends JpaRepository<ControlEquipment, Long> {
    @Query(value = "select * from inTrack.control_equipment where (lower(type) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<ControlEquipment> getControlEquipment(String search);
}
