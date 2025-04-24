package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.equipmentEntity.BatteryLocation;
import com.inTrack.spring.entity.equipmentEntity.BatteryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BatteryLocationRepository extends JpaRepository<BatteryLocation, Long> {

    @Query(value = "select * from inTrack.battery_location where (lower(battery_location) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<BatteryLocation> getBatteryLocation(String search);
}
