package com.inTrack.spring.repository.EquipmentDBDetail;

import com.inTrack.spring.entity.EquipmentDBDetailEntity.EquipmentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentDetailRepository extends JpaRepository<EquipmentDetail, Long> {
    List<EquipmentDetail> findByEquipmentName(String upperCase);
}
