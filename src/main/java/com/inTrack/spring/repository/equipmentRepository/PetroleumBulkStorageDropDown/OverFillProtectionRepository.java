package com.inTrack.spring.repository.equipmentRepository.PetroleumBulkStorageDropDown;

import com.inTrack.spring.entity.equipmentEntity.petroleumDropDown.OverFillProtection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OverFillProtectionRepository extends JpaRepository<OverFillProtection, Long> {

    @Query(value = "select * from inTrack.overfill_protection where (lower(type) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<OverFillProtection> getOverFillProtection(String search);
}
