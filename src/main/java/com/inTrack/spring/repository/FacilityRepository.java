package com.inTrack.spring.repository;

import com.inTrack.spring.entity.Facility;
import com.inTrack.spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author vijayan
 */

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    Facility findByFacilityIdAndActive(Long id, Boolean activeStatus);
    List<Facility> findByFacilityIdIn(List<Long> id);
    Facility findByFacilityId(Long id);

    List<Facility> findByCreatedByAndActive(User user, Boolean activeStatus);
}
