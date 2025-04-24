package com.inTrack.spring.repository;

import com.inTrack.spring.entity.HazardousWaste;
import com.inTrack.spring.entity.Waste;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WasteRepository extends JpaRepository<Waste, Long> {
    Waste findByHazardousWaste(HazardousWaste hazardousWaste);
}
