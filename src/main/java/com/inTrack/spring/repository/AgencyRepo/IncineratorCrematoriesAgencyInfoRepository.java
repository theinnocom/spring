package com.inTrack.spring.repository.AgencyRepo;

import com.inTrack.spring.entity.equipmentEntity.IncineratorCrematories;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.IncineratorCrematoriesAgencyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncineratorCrematoriesAgencyInfoRepository extends JpaRepository<IncineratorCrematoriesAgencyInfo, Long> {
    List<IncineratorCrematoriesAgencyInfo> findByIncineratorCrematories(IncineratorCrematories incineratorCrematories);
}
