package com.inTrack.spring.repository.equipmentRepository.PetroleumBulkStorageDropDown;

import com.inTrack.spring.entity.equipmentEntity.petroleumDropDown.SpillPrevention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpillPreventionRepository extends JpaRepository<SpillPrevention, Long> {

    @Query(value = "select * from inTrack.spill_prevention where (lower(type) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<SpillPrevention> getSpillPrevention(String search);
}
