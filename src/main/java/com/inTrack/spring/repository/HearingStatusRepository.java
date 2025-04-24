package com.inTrack.spring.repository;

import com.inTrack.spring.entity.HearingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HearingStatusRepository extends JpaRepository<HearingStatus, Long> {
}
