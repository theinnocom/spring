package com.inTrack.spring.repository.AgencyRepo;

import com.inTrack.spring.entity.equipmentEntity.Stack;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.StackAgencyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StackAgencyInfoRepository extends JpaRepository<StackAgencyInfo, Long> {
    List<StackAgencyInfo> findByStack(Stack stack);
}
