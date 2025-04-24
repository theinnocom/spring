package com.inTrack.spring.repository.equipmentRepository;


import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.equipmentEntity.FirstAidKit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FirstAidKitRepository extends JpaRepository<FirstAidKit, Long> {
    List<FirstAidKit> findByBuilding(Building building);
}
