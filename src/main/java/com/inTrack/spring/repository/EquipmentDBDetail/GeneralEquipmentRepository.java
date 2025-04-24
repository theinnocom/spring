package com.inTrack.spring.repository.EquipmentDBDetail;

import com.inTrack.spring.entity.EquipmentDBDetailEntity.GeneralEquipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneralEquipmentRepository extends JpaRepository<GeneralEquipment, Long> {
}
