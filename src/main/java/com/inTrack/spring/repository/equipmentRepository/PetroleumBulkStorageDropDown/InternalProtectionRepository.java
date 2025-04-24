package com.inTrack.spring.repository.equipmentRepository.PetroleumBulkStorageDropDown;

import com.inTrack.spring.entity.equipmentEntity.petroleumDropDown.InternalProtection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InternalProtectionRepository extends JpaRepository<InternalProtection, Long> {

    @Query(value = "select * from inTrack.internal_protection where (lower(type) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<InternalProtection> getInternalProtection(String search);
}
