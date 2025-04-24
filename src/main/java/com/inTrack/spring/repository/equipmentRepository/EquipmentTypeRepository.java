package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.EquipmentPerformance.EquipmentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentTypeRepository extends JpaRepository<EquipmentType, Long> {
}
