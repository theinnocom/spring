package com.inTrack.spring.repository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.Facility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Long> {
    Building findByBuildingIdAndIsActive(Long buildingId, Boolean activeStatus);
    Building findByBuildingId(Long buildingId);
    List<Building> findByFacility(Facility facility);

    Page<Building> findByFacility(Pageable pageable, Facility facility);
}
