package com.inTrack.spring.repository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.Facade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacadeRepository extends JpaRepository<Facade, Long> {
    Facade findByFacadeId(Long facadeId);

    Facade findByBuilding(Building building);
}
