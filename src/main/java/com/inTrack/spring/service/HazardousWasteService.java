package com.inTrack.spring.service;

import com.inTrack.spring.dto.HazardousWasteDTO;
import com.inTrack.spring.dto.WasteDTO;
import com.inTrack.spring.entity.Facility;
import com.inTrack.spring.entity.HazardousWaste;
import com.inTrack.spring.entity.Waste;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.FacilityRepository;
import com.inTrack.spring.repository.HazardousWasteRepository;
import com.inTrack.spring.repository.WasteRepository;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HazardousWasteService {

    private final HazardousWasteRepository hazardousWasteRepository;
    private final WasteRepository wasteRepository;
    private final FacilityRepository facilityRepository;

    public HazardousWasteDTO saveHazardousWaste(final HazardousWasteDTO hazardousWasteDTO) {
        final HazardousWaste hazardousWaste;
        if (hazardousWasteDTO.getId() != null) {
            hazardousWaste = this.hazardousWasteRepository.findById(hazardousWasteDTO.getId()).orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
        } else {
            hazardousWaste = new HazardousWaste();
        }

        hazardousWaste.setManifestNumber(hazardousWasteDTO.getManifestNumber());
        hazardousWaste.setGeneratorId(hazardousWasteDTO.getGeneratorId());
        hazardousWaste.setDateOfManifest(hazardousWasteDTO.getDateOfManifest());
        hazardousWaste.setTotalWasteInLbs(hazardousWasteDTO.getTotalWasteInLbs());
        hazardousWaste.setContactPerson(hazardousWasteDTO.getContactPerson());
        hazardousWaste.setPhoneNumber(hazardousWasteDTO.getPhoneNumber());
        hazardousWaste.setCompanyNameCollectingWaste(hazardousWasteDTO.getCompanyNameCollectingWaste());
        hazardousWaste.setWasteTransportingCompanyId(hazardousWasteDTO.getWasteTransportingCompanyId());
        hazardousWaste.setFinalDestinationFacilityId(hazardousWasteDTO.getFinalDestinationFacilityId());
        hazardousWaste.setNote(hazardousWasteDTO.getNote());
        hazardousWaste.setWasteGenerationCategory(hazardousWasteDTO.getWasteGenerationCategory());
        final Facility facility = this.facilityRepository.findByFacilityId(hazardousWasteDTO.getFacilityId());
        if (facility == null) {
            throw new ValidationError(ApplicationMessageStore.FACILITY_NOT_FOUND);
        }
        hazardousWaste.setFacility(facility);
        final HazardousWaste saveHazardousWaste = this.hazardousWasteRepository.save(hazardousWaste);
        Waste saveWaste = null;
        if (hazardousWasteDTO.getWasteDTO() != null) {
            final WasteDTO wasteDTO = hazardousWasteDTO.getWasteDTO();
            final Waste waste = new Waste();
            waste.setWaste(wasteDTO.getWaste());
            waste.setWasteVolumeInGallons(wasteDTO.getWasteVolumeInGallons());
            waste.setWasteDensity(wasteDTO.getWasteDensity());
            waste.setWasteQuantityInLbs(wasteDTO.getWasteQuantityInLbs());
            waste.setNameOfWaste(wasteDTO.getNameOfWaste());
            waste.setTypeOfWaste(wasteDTO.getTypeOfWaste());
            waste.setWasteUnit(wasteDTO.getWasteUnit());
            waste.setHazardousWaste(saveHazardousWaste);
            saveWaste = this.wasteRepository.save(waste);
        }
        return this.setHazardousWaste(saveHazardousWaste, saveWaste);
    }

    public List<HazardousWasteDTO> getHazardousWaste(final Long facilityId) {
        final Facility facility = this.facilityRepository.findByFacilityId(facilityId);
        if (facility == null) {
            throw new ValidationError(ApplicationMessageStore.FACILITY_NOT_FOUND);
        }
        final List<HazardousWaste> hazardousWastes = this.hazardousWasteRepository.findByFacility(facility);
        return hazardousWastes.stream().map(hazardousWaste -> this.setHazardousWaste(hazardousWaste, null)).collect(Collectors.toList());
    }

    public HazardousWasteDTO getHazardousWasteUsingId(final Long id) {
        final HazardousWaste hazardousWaste = this.hazardousWasteRepository.findById(id).orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
        final Waste waste = this.wasteRepository.findByHazardousWaste(hazardousWaste);
        return this.setHazardousWaste(hazardousWaste, waste);
    }

    public HazardousWasteDTO updateHazardousWaste(final HazardousWasteDTO hazardousWasteDTO) {
        return this.saveHazardousWaste(hazardousWasteDTO);
    }

    public void deleteHazardousWaste(final Long id) {
        final HazardousWaste hazardousWaste = this.hazardousWasteRepository.findById(id).orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
        final Waste waste = this.wasteRepository.findByHazardousWaste(hazardousWaste);
        if (waste != null) {
            this.wasteRepository.delete(waste);
        }
        this.hazardousWasteRepository.delete(hazardousWaste);
    }


    private HazardousWasteDTO setHazardousWaste(final HazardousWaste hazardousWaste, final Waste waste) {
        final HazardousWasteDTO hazardousWasteDTO = new HazardousWasteDTO();
        hazardousWasteDTO.setManifestNumber(hazardousWaste.getManifestNumber());
        hazardousWasteDTO.setGeneratorId(hazardousWaste.getGeneratorId());
        hazardousWasteDTO.setDateOfManifest(hazardousWaste.getDateOfManifest());
        hazardousWasteDTO.setTotalWasteInLbs(hazardousWaste.getTotalWasteInLbs());
        hazardousWasteDTO.setContactPerson(hazardousWaste.getContactPerson());
        hazardousWasteDTO.setPhoneNumber(hazardousWaste.getPhoneNumber());
        hazardousWasteDTO.setCompanyNameCollectingWaste(hazardousWaste.getCompanyNameCollectingWaste());
        hazardousWasteDTO.setWasteTransportingCompanyId(hazardousWaste.getWasteTransportingCompanyId());
        hazardousWasteDTO.setFinalDestinationFacilityId(hazardousWaste.getFinalDestinationFacilityId());
        hazardousWasteDTO.setNote(hazardousWaste.getNote());
        hazardousWasteDTO.setWasteGenerationCategory(hazardousWaste.getWasteGenerationCategory());
        if (waste != null) {
            final WasteDTO wasteDTO = new WasteDTO();
            wasteDTO.setWaste(waste.getWaste());
            wasteDTO.setWasteVolumeInGallons(waste.getWasteVolumeInGallons());
            wasteDTO.setWasteDensity(waste.getWasteDensity());
            wasteDTO.setWasteQuantityInLbs(waste.getWasteQuantityInLbs());
            wasteDTO.setNameOfWaste(waste.getNameOfWaste());
            wasteDTO.setTypeOfWaste(waste.getTypeOfWaste());
            wasteDTO.setWasteUnit(waste.getWasteUnit());
            hazardousWasteDTO.setWasteDTO(wasteDTO);
        }
        return hazardousWasteDTO;
    }
}
