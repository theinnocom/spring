package com.inTrack.spring.repository;

import com.inTrack.spring.entity.IssuingAgency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssuingAgencyRepository extends JpaRepository<IssuingAgency, Long> {
}
