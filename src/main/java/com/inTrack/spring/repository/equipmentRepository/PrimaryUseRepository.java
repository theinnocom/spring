package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.equipmentEntity.boilerDropDown.PrimaryUse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrimaryUseRepository extends JpaRepository<PrimaryUse, Long> {

    @Query(value = "select * from inTrack.primary_use where (lower(type) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<PrimaryUse> getAllPrimaryUse(String type);
}
