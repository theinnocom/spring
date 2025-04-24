package com.inTrack.spring.repository;

import com.inTrack.spring.entity.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuelTypeRepository extends JpaRepository<FuelType, Long> {
    FuelType findByFuelTypeId(Long fuelTypeId);
}
