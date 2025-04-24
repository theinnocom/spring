package com.inTrack.spring.repository.equipmentRepository.PetroleumBulkStorageDropDown;

import com.inTrack.spring.entity.equipmentEntity.petroleumDropDown.TankSecondaryContainment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TankSecondaryContainmentRepository extends JpaRepository<TankSecondaryContainment, Long> {

    @Query(value = "select * from inTrack.tank_secondary_containment where (lower(type) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<TankSecondaryContainment> getTankSecondaryContainment(String search);
}
