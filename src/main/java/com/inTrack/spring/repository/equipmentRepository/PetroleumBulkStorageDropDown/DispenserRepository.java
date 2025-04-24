package com.inTrack.spring.repository.equipmentRepository.PetroleumBulkStorageDropDown;

import com.inTrack.spring.entity.equipmentEntity.petroleumDropDown.Dispenser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DispenserRepository extends JpaRepository<Dispenser, Long> {

    @Query(value = "select * from inTrack.dispenser where (lower(type) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<Dispenser> getDispenser(String search);
}
