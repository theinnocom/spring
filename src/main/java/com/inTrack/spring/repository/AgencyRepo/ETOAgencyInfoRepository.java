package com.inTrack.spring.repository.AgencyRepo;

import com.inTrack.spring.entity.equipmentEntity.ETO;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.ETOAgencyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ETOAgencyInfoRepository extends JpaRepository<ETOAgencyInfo, Long> {
    ETOAgencyInfo findByEto(ETO eto);
}
