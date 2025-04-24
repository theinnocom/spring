package com.inTrack.spring.repository;

import com.inTrack.spring.entity.Facility;
import com.inTrack.spring.entity.FuelConsumption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuelConsumptionRepository extends JpaRepository<FuelConsumption, Long> {
    List<FuelConsumption> findAllByFacility(Facility facility);

    List<FuelConsumption> findByYear(Long year);

    FuelConsumption findByYearAndFuelTypeFuelName(Long year, String fuelType);
}
