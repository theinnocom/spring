package com.inTrack.spring.repository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.OccupancyCertificateInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OccupancyCertificateInformationRepository extends JpaRepository<OccupancyCertificateInformation, Long> {
    List<OccupancyCertificateInformation> findByBuilding(Building building);
}
