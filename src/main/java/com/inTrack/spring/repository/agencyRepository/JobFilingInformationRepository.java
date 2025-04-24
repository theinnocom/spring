package com.inTrack.spring.repository.agencyRepository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobFilingInformationRepository extends JpaRepository<JobFilingInformation, Long> {
    Long countByJobNumber(String jobNumber);

    JobFilingInformation findByJobFilingId(Long jobFilingId);

    List<JobFilingInformation> findByBuilding(Building building);
}
