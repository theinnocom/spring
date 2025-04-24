package com.inTrack.spring.repository.equipmentRepository.PetroleumBulkStorageDropDown;

import com.inTrack.spring.entity.equipmentEntity.petroleumDropDown.PipingSecondaryContainment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PipingSecondaryContainmentRepository extends JpaRepository<PipingSecondaryContainment, Long> {

    @Query(value = "select * from inTrack.piping_secondary_containment where (lower(type) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<PipingSecondaryContainment> getPipingSecondaryContainment(String search);
}
