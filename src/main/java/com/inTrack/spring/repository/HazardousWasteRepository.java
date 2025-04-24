package com.inTrack.spring.repository;

import com.inTrack.spring.entity.Facility;
import com.inTrack.spring.entity.HazardousWaste;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HazardousWasteRepository extends JpaRepository<HazardousWaste, Long> {
    List<HazardousWaste> findByFacility(Facility facility);
}
