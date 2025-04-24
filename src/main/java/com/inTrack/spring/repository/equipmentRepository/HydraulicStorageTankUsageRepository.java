package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.equipmentEntity.HydraulicStorageTankDropDown.HydraulicStorageTankUsage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HydraulicStorageTankUsageRepository extends JpaRepository<HydraulicStorageTankUsage, Long> {
}
