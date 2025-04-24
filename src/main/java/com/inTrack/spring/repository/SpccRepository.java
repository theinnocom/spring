package com.inTrack.spring.repository;

import com.inTrack.spring.entity.Facility;
import com.inTrack.spring.entity.Spcc;
import com.inTrack.spring.store.NativeQuires;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpccRepository extends JpaRepository<Spcc, Long> {
    List<Spcc> findByFacility(Facility facility);

    @Query(value = NativeQuires.GET_PBS_CAPACITY, nativeQuery = true)
    List<Object[]> getPbsCapacity(Long pbsNo, Long facilityId);
}
