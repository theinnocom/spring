package com.inTrack.spring.repository.equipmentRepository.PetroleumBulkStorageDropDown;

import com.inTrack.spring.entity.equipmentEntity.petroleumDropDown.PipingLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PipingLocationRepository extends JpaRepository<PipingLocation, Long> {

    @Query(value = "select * from inTrack.piping_location where (lower(type) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<PipingLocation> getPipingLocation(String search);
}
