package com.inTrack.spring.repository.AgencyRepo;

import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.GeneratorAgency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneratorAgencyRepository extends JpaRepository<GeneratorAgency, Long> {
}
