package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.equipmentEntity.LandFill;
import com.inTrack.spring.entity.equipmentEntity.PortableFireExtinguisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortableFireExtinguisherRepository extends JpaRepository<PortableFireExtinguisher, Long> {

    PortableFireExtinguisher findByFireExtinguisherId(Long FireExtinguisherId);

    List<PortableFireExtinguisher> findAllByBuilding(Building building);

    List<PortableFireExtinguisher> findAllByBuildingAndFloor(Building building, Long floor);

    PortableFireExtinguisher findByFireExtinguisherIdAndIsActive(Long FireExtinguisherId, boolean activeStatus);

}
