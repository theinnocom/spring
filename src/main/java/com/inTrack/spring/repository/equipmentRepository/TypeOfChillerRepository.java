package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.equipmentEntity.TypeOfChiller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeOfChillerRepository extends JpaRepository<TypeOfChiller, Long> {
    @Query(value = "select * from inTrack.type_of_chiller where (lower(type) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<TypeOfChiller> getChillerTypes(String search);
}
