package com.inTrack.spring.repository;

import com.inTrack.spring.entity.Borough;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoroughRepository extends JpaRepository<Borough, Long> {
    Borough findByBoroughId(Long boroughId);
}
