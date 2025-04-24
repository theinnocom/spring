package com.inTrack.spring.repository.AgencyRepo;

import com.inTrack.spring.entity.equipmentEntity.*;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoilerCogenAgencyInfoRepository extends JpaRepository<BoilerCogenAgencyInfo, Long> {
    List<BoilerCogenAgencyInfo> findByBoiler(Boiler boiler);

    List<BoilerCogenAgencyInfo> findByCogenEngineTurbine(CogenEngineTurbine cogenEngineTurbine);

    List<BoilerCogenAgencyInfo> findByFireAlarm(FireAlarm fireAlarm);

    List<BoilerCogenAgencyInfo> findByKitchenHoodFireSuppression(KitchenHoodFireSuppression kitchenHoodFireSuppression);

    List<BoilerCogenAgencyInfo> findByPaintSprayBooth(PaintSprayBooth paintSprayBooth);

    List<BoilerCogenAgencyInfo> findByRpz(RPZ rpz);

    List<BoilerCogenAgencyInfo> findByElevator(Elevator elevator);

    List<BoilerCogenAgencyInfo> findByEmergencyEgress(EmergencyEgressAndLighting emergencyEgressAndLighting);

    List<BoilerCogenAgencyInfo> findByHydraulicStorageTank(HydraulicStorageTank tank);

    List<BoilerCogenAgencyInfo> findByLandFill(LandFill landFill);

    List<BoilerCogenAgencyInfo> findByPrintingPress(PrintingPress printingPress);

    List<BoilerCogenAgencyInfo> findByPortableFireExtinguisher(PortableFireExtinguisher fireExtinguisher);

    List<BoilerCogenAgencyInfo> findByFumeHood(FumeHood fumeHood);

    @Query(value = "select * from inTrack.boiler_cogen_agency_info where id = ?1", nativeQuery = true)
    BoilerCogenAgencyInfo getByAgencyId(Long id);
}
