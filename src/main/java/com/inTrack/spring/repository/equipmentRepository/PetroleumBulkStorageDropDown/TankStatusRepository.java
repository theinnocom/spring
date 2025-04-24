package com.inTrack.spring.repository.equipmentRepository.PetroleumBulkStorageDropDown;

import com.inTrack.spring.entity.equipmentEntity.petroleumDropDown.TankStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TankStatusRepository extends JpaRepository<TankStatus, Long> {

    @Query(value = "select * from inTrack.tank_status where (lower(type) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<TankStatus> getTankStatus(String search);
}
