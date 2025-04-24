package com.inTrack.spring.repository.AgencyRepo;

import com.inTrack.spring.entity.equipmentEntity.FirstAidKit;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.FirstAidAgencyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FirstAidAgencyInfoRepository extends JpaRepository<FirstAidAgencyInfo, Long> {
    List<FirstAidAgencyInfo> findByFirstAidKit(FirstAidKit firstAidKit);
}
