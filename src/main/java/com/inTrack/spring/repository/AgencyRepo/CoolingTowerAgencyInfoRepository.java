package com.inTrack.spring.repository.AgencyRepo;

import com.inTrack.spring.entity.equipmentEntity.CoolingTower;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.CoolingTowerAgencyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoolingTowerAgencyInfoRepository extends JpaRepository<CoolingTowerAgencyInfo, Long> {
    List<CoolingTowerAgencyInfo> findByCoolingTower(CoolingTower coolingTower);
}
