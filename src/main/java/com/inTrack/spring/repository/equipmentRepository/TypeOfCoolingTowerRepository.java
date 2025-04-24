package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.equipmentEntity.TypeOfCollingTower;
import com.inTrack.spring.entity.equipmentEntity.TypeOfRpz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeOfCoolingTowerRepository extends JpaRepository<TypeOfCollingTower, Long> {

    @Query(value = "select * from inTrack.type_of_colling_tower where (lower(type) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<TypeOfCollingTower> getTypeOfCoolingTower(String search);
}
