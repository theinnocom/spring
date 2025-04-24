package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.equipmentEntity.KitchenHoodFireSuppression;
import com.inTrack.spring.entity.equipmentEntity.LandFill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KitchenHoodFireSuppressionRepository extends JpaRepository<KitchenHoodFireSuppression, Long> {
    KitchenHoodFireSuppression findByKitchenHoodId(Long kitchenHoodId);

    List<KitchenHoodFireSuppression> findAllByBuilding(Building building);

    List<KitchenHoodFireSuppression> findAllByBuildingAndFloor(Building building, Long floor);

    KitchenHoodFireSuppression findByKitchenHoodIdAndIsActive(Long kitchenHoodId, boolean activeStatus);

    Long countByUniqueId(String uniqueId);
}
