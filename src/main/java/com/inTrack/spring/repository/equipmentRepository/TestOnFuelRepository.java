package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.equipmentEntity.boilerDropDown.TestOnFuel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TestOnFuelRepository extends JpaRepository<TestOnFuel, Long> {

    @Query(value = "select * from inTrack.test_on_fuel where (lower(type) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<TestOnFuel> getAllTestOnFuel(String type);
}
