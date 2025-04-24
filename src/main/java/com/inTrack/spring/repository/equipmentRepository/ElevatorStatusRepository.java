package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author vijayan
 */

public interface ElevatorStatusRepository extends JpaRepository<ElevatorStatus, Long> {
}
