package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.equipmentEntity.BatteryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BatteryTypeRepository extends JpaRepository<BatteryType, Long> {

    @Query(value = "select * from inTrack.battery_type where (lower(type) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<BatteryType> getBatteryType(String search);
}
