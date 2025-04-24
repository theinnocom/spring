package com.inTrack.spring.repository.equipmentRepository.PetroleumBulkStorageDropDown;

import com.inTrack.spring.entity.equipmentEntity.petroleumDropDown.Subpart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubpartRepository extends JpaRepository<Subpart, Long> {

    @Query(value = "select * from inTrack.subpart where (lower(type) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<Subpart> getSubpart(String search);
}
