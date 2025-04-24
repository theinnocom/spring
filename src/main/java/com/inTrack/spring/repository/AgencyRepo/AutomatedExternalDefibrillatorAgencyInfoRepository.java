package com.inTrack.spring.repository.AgencyRepo;

import com.inTrack.spring.entity.equipmentEntity.AutomatedExternalDefibrillator;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.AutomatedExternalDefibrillatorAgencyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutomatedExternalDefibrillatorAgencyInfoRepository extends JpaRepository<AutomatedExternalDefibrillatorAgencyInfo, Long> {
    List<AutomatedExternalDefibrillatorAgencyInfo> findByAutomatedExternalDefibrillator(AutomatedExternalDefibrillator automatedExternalDefibrillator);
}
