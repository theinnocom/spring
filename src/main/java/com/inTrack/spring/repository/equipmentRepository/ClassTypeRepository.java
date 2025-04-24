package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.equipmentEntity.ClassType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassTypeRepository extends JpaRepository<ClassType, Long> {

    @Query(value = "select * from inTrack.class_type where (lower(type) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<ClassType> getClassType(String search);
}
