package com.inTrack.spring.repository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.Violation;
import com.inTrack.spring.store.NativeQuires;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ViolationRepository extends JpaRepository<Violation, Long> {
    Violation findByViolationIdAndIsDelete(Long violationId, Boolean isDelete);

    List<Violation> findAllByBuildingAndIsDelete(Building building, Boolean isDelete);

    @Query(value = "select COUNT(*), ia.type from inTrack.violation v join inTrack.issuing_agency ia on ia.id = v.issuing_agency_id where v.building_id = ?1 group by ia.type WITH ROLLUP", nativeQuery = true)
    List<Object[]> getViolationCount(Long buildingId);

    @Query(value = NativeQuires.GET_VIOLATION, nativeQuery = true)
    Page<Object[]> getViolation(Long from, Long to, boolean isHearingStatus, boolean isIssuingAgency, Long hearingStatus, Long issuingAgency, Pageable pageable);
}
