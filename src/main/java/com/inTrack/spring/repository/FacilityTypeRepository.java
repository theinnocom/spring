package com.inTrack.spring.repository;

import com.inTrack.spring.entity.PermitType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityTypeRepository extends JpaRepository<PermitType, Long> {
}
