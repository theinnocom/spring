package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.equipmentEntity.boilerDropDown.BoilerPressureType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoilerPressureTypeRepository extends JpaRepository<BoilerPressureType, Long> {
    @Query(value = "select * from inTrack.boiler_pressure_type where (lower(type) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<BoilerPressureType> getAllBoilerPressureType(String type);
}
