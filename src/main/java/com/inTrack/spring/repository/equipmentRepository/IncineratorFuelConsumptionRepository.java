package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.equipmentEntity.IncineratorCrematories;
import com.inTrack.spring.entity.equipmentEntity.IncineratorFuelConsumption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncineratorFuelConsumptionRepository extends JpaRepository<IncineratorFuelConsumption, Long> {
    List<IncineratorFuelConsumption> findByIncineratorCrematories(IncineratorCrematories incineratorCrematories);

    List<IncineratorFuelConsumption> findByYearIn(List<Long> years);
}
