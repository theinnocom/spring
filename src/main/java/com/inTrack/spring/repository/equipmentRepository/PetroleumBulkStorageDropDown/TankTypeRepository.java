package com.inTrack.spring.repository.equipmentRepository.PetroleumBulkStorageDropDown;

import com.inTrack.spring.entity.equipmentEntity.petroleumDropDown.TankType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TankTypeRepository extends JpaRepository<TankType, Long> {

    @Query(value = "select * from inTrack.tank_type where (lower(type) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<TankType> getTankType(String search);
}
