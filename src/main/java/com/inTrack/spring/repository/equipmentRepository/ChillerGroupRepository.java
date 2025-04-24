package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.equipmentEntity.ChillerGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChillerGroupRepository extends JpaRepository<ChillerGroup, Long> {
    @Query(value = "select * from inTrack.chiller_group where (lower(group_name) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<ChillerGroup> getChillerGroup(String search);
}
