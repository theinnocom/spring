package com.inTrack.spring.repository.equipmentRepository.PetroleumBulkStorageDropDown;

import com.inTrack.spring.entity.equipmentEntity.petroleumDropDown.ExternalProtection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExternalProtectionRepository extends JpaRepository<ExternalProtection, Long> {

    @Query(value = "select * from inTrack.external_protection where (lower(type) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<ExternalProtection> getExternalProtection(String search);
}
