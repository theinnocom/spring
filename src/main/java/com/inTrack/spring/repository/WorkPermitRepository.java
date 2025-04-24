package com.inTrack.spring.repository;

import com.inTrack.spring.dto.common.WorkPermitDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.WorkPermit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkPermitRepository extends JpaRepository<WorkPermit, Long> {
    List<WorkPermit> findByBuilding(Building building);
}
