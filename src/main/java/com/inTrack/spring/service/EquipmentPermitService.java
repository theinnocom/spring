package com.inTrack.spring.service;

import com.inTrack.spring.entity.equipmentEntity.equipmentPermits.EquipmentPermit;
import com.inTrack.spring.repository.EquipmentPermitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipmentPermitService {

    @Autowired
    private EquipmentPermitRepository equipmentPermitRepository;

    public EquipmentPermit getEquipmentPermit(final String equipmentName) {
        return this.equipmentPermitRepository.findByEquipmentNameLike(equipmentName);
    }
}
