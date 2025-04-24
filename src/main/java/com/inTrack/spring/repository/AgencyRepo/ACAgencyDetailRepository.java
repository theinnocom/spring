package com.inTrack.spring.repository.AgencyRepo;

import com.inTrack.spring.dto.requestDTO.ACUnitDTO;
import com.inTrack.spring.entity.equipmentEntity.AirConditioningUnit;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.ACAgencyDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ACAgencyDetailRepository extends JpaRepository<ACAgencyDetail, Long> {
    List<ACAgencyDetail> findByAirConditioningUnit(AirConditioningUnit airConditioningUnit);
}
