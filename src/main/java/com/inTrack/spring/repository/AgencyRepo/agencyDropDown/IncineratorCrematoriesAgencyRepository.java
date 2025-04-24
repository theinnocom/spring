package com.inTrack.spring.repository.AgencyRepo.agencyDropDown;

import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.IncineratorCrematoriesAgency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Vijayan M
 */
@Repository
public interface IncineratorCrematoriesAgencyRepository extends JpaRepository<IncineratorCrematoriesAgency, Long> {
}
