package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.equipmentEntity.ETO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ETORepository extends JpaRepository<ETO, Long> {
    ETO findByETOId(Long ETOId);

    List<ETO> findAllByBuilding(Building building);

    List<ETO> findAllByBuildingAndFloor(Building building, Long floor);

    ETO findByETOIdAndIsActive(Long ETOId, boolean activeStatus);

    Long countByUniqueId(String uniqueId);
}
