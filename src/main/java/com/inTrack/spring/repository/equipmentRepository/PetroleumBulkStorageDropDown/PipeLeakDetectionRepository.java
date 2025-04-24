package com.inTrack.spring.repository.equipmentRepository.PetroleumBulkStorageDropDown;

import com.inTrack.spring.entity.equipmentEntity.petroleumDropDown.PipeLeakDetection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PipeLeakDetectionRepository extends JpaRepository<PipeLeakDetection, Long> {

    @Query(value = "select * from inTrack.pipe_leak_detection where (lower(type) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<PipeLeakDetection> getPipeLeakDetection(String search);
}
