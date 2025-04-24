package com.inTrack.spring.repository.equipmentRepository.PetroleumBulkStorageDropDown;

import com.inTrack.spring.entity.equipmentEntity.BatteryType;
import com.inTrack.spring.entity.equipmentEntity.TierLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TierLevelRepository extends JpaRepository<TierLevel, Long> {

    @Query(value = "select * from inTrack.tier_level where (lower(type) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<TierLevel> getTierLevel(String search);
}
