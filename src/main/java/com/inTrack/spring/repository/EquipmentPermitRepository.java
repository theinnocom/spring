package com.inTrack.spring.repository;

import com.inTrack.spring.entity.equipmentEntity.equipmentPermits.EquipmentPermit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EquipmentPermitRepository extends JpaRepository<EquipmentPermit, Long> {

    @Query(value = "select * from inTrack.equipment_permit where (lower(equipment_name) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    EquipmentPermit findByEquipmentNameLike(String equipmentName);
}
