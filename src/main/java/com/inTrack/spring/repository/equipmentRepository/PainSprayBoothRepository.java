package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.equipmentEntity.PaintSprayBooth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PainSprayBoothRepository extends JpaRepository<PaintSprayBooth, Long> {
    PaintSprayBooth findByPainSprayId(Long PainSprayId);

    List<PaintSprayBooth> findAllByBuilding(Building building);

    List<PaintSprayBooth> findAllByBuildingAndFloor(Building building, Long floor);

    PaintSprayBooth findByPainSprayIdAndIsActive(Long PainSprayId, boolean activeStatus);

    Long countByUniqueId(String uniqueId);
}
