package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.Equipment;
import com.inTrack.spring.entity.Facility;
import com.inTrack.spring.query.JPAQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    Equipment findByEquipmentTypeAndBuildingAndFacility(String elevator, Building building, Facility facility);

    @Query(JPAQuery.GET_EQUIPMENT_USING_BUILDING_ID)
    List<Equipment> findByBuilding(Long buildingId);

    Equipment findByEquipmentId(Long equipmentName);
}
