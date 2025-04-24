package com.inTrack.spring.repository.equipmentRepository.PetroleumBulkStorageDropDown;

import com.inTrack.spring.entity.equipmentEntity.petroleumDropDown.TankLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TankLocationRepository extends JpaRepository<TankLocation, Long> {

    @Query(value = "select * from inTrack.tank_location where (lower(type) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<TankLocation> getTankLocation(String search);
}
