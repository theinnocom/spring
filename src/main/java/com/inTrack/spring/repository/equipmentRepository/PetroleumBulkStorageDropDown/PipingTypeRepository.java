package com.inTrack.spring.repository.equipmentRepository.PetroleumBulkStorageDropDown;

import com.inTrack.spring.entity.equipmentEntity.petroleumDropDown.PipingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PipingTypeRepository extends JpaRepository<PipingType, Long> {

    @Query(value = "select * from inTrack.piping_type where (lower(type) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<PipingType> getPipingType(String search);
}
