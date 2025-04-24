package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.EquipmentFloorRelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentFloorRelationRepository extends JpaRepository<EquipmentFloorRelation, Long> {
    EquipmentFloorRelation findByFloorAndEquipmentType(Long floor, String equipmentType);
}
