package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.equipmentEntity.AbsorberChiller;
import com.inTrack.spring.entity.equipmentEntity.BridgeTunnel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BridgeTunnelRepository extends JpaRepository<BridgeTunnel, Long> {
    BridgeTunnel findByBridgeTunnelId(Long bridgeTunnelId);

    List<BridgeTunnel> findAllByBuilding(Building building);

    List<BridgeTunnel> findAllByBuildingAndFloor(Building building, Long floor);

    BridgeTunnel findByBridgeTunnelIdAndIsActive(Long bridgeTunnelId, boolean activeStatus);
}
