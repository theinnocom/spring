package com.inTrack.spring.repository.AgencyRepo;

import com.inTrack.spring.entity.equipmentEntity.Generator;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.GeneratorAgencyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeneratorAgencyInfoRepository extends JpaRepository<GeneratorAgencyInfo, Long> {
    List<GeneratorAgencyInfo> findByGenerator(Generator generator);
}
