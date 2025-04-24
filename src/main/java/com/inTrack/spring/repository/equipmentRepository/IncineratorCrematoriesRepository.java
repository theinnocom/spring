package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.equipmentEntity.IncineratorCrematories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IncineratorCrematoriesRepository extends JpaRepository<IncineratorCrematories, Long> {

    IncineratorCrematories findByIdAndIsActive(Long id, boolean activeStatus);

    @Query("select ic from IncineratorCrematories ic where ic.id = ?1")
    IncineratorCrematories getById(Long id);

    List<IncineratorCrematories> findAllByBuilding(Building building);
    List<IncineratorCrematories> findAllByBuildingAndFloor(Building building, Long floor);

    Long countByUniqueId(String uniqueId);
}
