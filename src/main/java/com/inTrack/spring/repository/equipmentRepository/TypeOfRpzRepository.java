package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.equipmentEntity.TypeOfRpz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeOfRpzRepository extends JpaRepository<TypeOfRpz, Long> {

    @Query(value = "select * from inTrack.type_of_rpz where (lower(type) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<TypeOfRpz> getTypeOfRpz(String search);
}
