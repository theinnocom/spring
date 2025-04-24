package com.inTrack.spring.repository;

import com.inTrack.spring.entity.Facility;
import com.inTrack.spring.entity.PostLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostLogRepository extends JpaRepository<PostLog, Long> {
    PostLog findByEmployeeId(Long employeeId);

    List<PostLog> findByFacility(Facility facility);

    @Query(value = "SELECT * FROM inTrack.post_log WHERE facility_id = ?1", countQuery = "SELECT count(*) FROM inTrack.post_log WHERE facility_id = ?1", nativeQuery = true)
    Page<PostLog> findByFacility(Long facilityId, Pageable pageable);
}
