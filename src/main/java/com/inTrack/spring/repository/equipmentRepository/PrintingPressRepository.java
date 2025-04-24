package com.inTrack.spring.repository.equipmentRepository;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.equipmentEntity.PrintingPress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrintingPressRepository extends JpaRepository<PrintingPress, Long> {
    PrintingPress findByPrinterId(Long printerId);

    PrintingPress findByPrinterIdAndIsActive(Long id, boolean activeStatus);

    List<PrintingPress> findAllByBuilding(Building building);
    List<PrintingPress> findAllByBuildingAndFloor(Building building, Long floor);
}
