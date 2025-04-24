package com.inTrack.spring.repository.equipmentRepository.PetroleumBulkStorageDropDown;

import com.inTrack.spring.entity.equipmentEntity.petroleumDropDown.TankLeakDetection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TankLeakDetectionRepository extends JpaRepository<TankLeakDetection, Long> {

    @Query(value = "select * from inTrack.tank_leak_detection where (lower(type) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<TankLeakDetection> getTankLeakDetection(String search);
}
