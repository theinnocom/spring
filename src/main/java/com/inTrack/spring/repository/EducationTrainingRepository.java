package com.inTrack.spring.repository;

import com.inTrack.spring.entity.EducationTraining;
import com.inTrack.spring.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationTrainingRepository extends JpaRepository<EducationTraining, Long> {
    List<EducationTraining> findByFacility(Facility facility);
}
