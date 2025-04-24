package com.inTrack.spring.repository;

import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.TypeOfChiller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagementTypeRepository extends JpaRepository<ManagementType, Long> {
}
