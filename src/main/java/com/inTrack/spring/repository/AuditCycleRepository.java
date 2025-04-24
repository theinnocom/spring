package com.inTrack.spring.repository;

import com.inTrack.spring.entity.AuditCycle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditCycleRepository extends JpaRepository<AuditCycle, Long> {
}
