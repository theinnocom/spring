package com.inTrack.spring.service;

import com.inTrack.spring.dto.WasteDTO;
import com.inTrack.spring.entity.HazardousWaste;
import com.inTrack.spring.entity.Waste;
import com.inTrack.spring.entity.WasteUnit;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.HazardousWasteRepository;
import com.inTrack.spring.repository.WasteRepository;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WasteService {

    private final WasteRepository wasteRepository;
    private final HazardousWasteRepository hazardousWasteRepository;

    public WasteDTO saveWaste(final WasteDTO wasteDTO) {
        final Waste waste;
        if (wasteDTO.getId() != null) {
            waste = this.wasteRepository.findById(wasteDTO.getId()).orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
        } else {
            waste = new Waste();
            final HazardousWaste hazardousWaste = this.hazardousWasteRepository.findById(wasteDTO.getHazardousWasteId()).orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
            waste.setHazardousWaste(hazardousWaste);
        }
        waste.setWaste(wasteDTO.getWaste());
        waste.setWasteVolumeInGallons(wasteDTO.getWasteVolumeInGallons());
        waste.setWasteDensity(wasteDTO.getWasteDensity());
        waste.setWasteQuantityInLbs(wasteDTO.getWasteQuantityInLbs());
        waste.setNameOfWaste(wasteDTO.getNameOfWaste());
        waste.setTypeOfWaste(wasteDTO.getTypeOfWaste());
        waste.setWasteUnit(wasteDTO.getWasteUnit());
        return this.setWaste(this.wasteRepository.save(waste));
    }

    public WasteDTO updateWaste(final WasteDTO wasteDTO) {
        return this.saveWaste(wasteDTO);
    }

    private WasteDTO setWaste(final Waste waste) {
        final WasteDTO wasteDTO = new WasteDTO();
        wasteDTO.setWaste(waste.getWaste());
        wasteDTO.setWasteVolumeInGallons(waste.getWasteVolumeInGallons());
        wasteDTO.setWasteDensity(waste.getWasteDensity());
        wasteDTO.setWasteQuantityInLbs(waste.getWasteQuantityInLbs());
        wasteDTO.setNameOfWaste(waste.getNameOfWaste());
        wasteDTO.setTypeOfWaste(waste.getTypeOfWaste());
        wasteDTO.setWasteUnit(waste.getWasteUnit());
        return wasteDTO;
    }
}
