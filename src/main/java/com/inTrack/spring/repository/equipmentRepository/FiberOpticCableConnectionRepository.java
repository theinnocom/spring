package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.equipmentEntity.BatteryLocation;
import com.inTrack.spring.entity.equipmentEntity.FiberOpticConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FiberOpticCableConnectionRepository extends JpaRepository<FiberOpticConnection, Long> {
    @Query(value = "select * from inTrack.fiber_optic_connection where (lower(connection_status) like lower(concat('%', ?1 '%')))", nativeQuery = true)
    List<FiberOpticConnection> getFiberOpticCableConnection(String search);
}
