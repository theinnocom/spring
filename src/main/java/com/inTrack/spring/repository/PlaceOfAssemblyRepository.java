package com.inTrack.spring.repository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.PlaceOfAssembly;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceOfAssemblyRepository extends JpaRepository<PlaceOfAssembly, Long> {
    List<PlaceOfAssembly> findByBuilding(Building building);
}
