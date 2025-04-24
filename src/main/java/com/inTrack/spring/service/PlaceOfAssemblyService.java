package com.inTrack.spring.service;

import com.inTrack.spring.dto.common.PlaceOfAssemblyDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.PlaceOfAssembly;
import com.inTrack.spring.entity.PlaceOfAssemblyType;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.PlaceOfAssemblyRepository;
import com.inTrack.spring.repository.PlaceOfAssemblyTypeRepository;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceOfAssemblyService {

    private final PlaceOfAssemblyRepository placeOfAssemblyRepository;
    private final BuildingRepository buildingRepository;
    private final PlaceOfAssemblyTypeRepository placeOfAssemblyTypeRepository;

    public PlaceOfAssemblyDTO createPlaceOfAssembly(final PlaceOfAssemblyDTO placeOfAssemblyDTO) {
        PlaceOfAssembly placeOfAssembly;
        if (!ObjectUtils.isEmpty(placeOfAssemblyDTO.getId())) {
            placeOfAssembly = this.placeOfAssemblyRepository.findById(placeOfAssemblyDTO.getId())
                    .orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
        } else {
            placeOfAssembly = new PlaceOfAssembly();
        }
        placeOfAssembly.setCapacity(placeOfAssemblyDTO.getCapacity());
        placeOfAssembly.setNote(placeOfAssemblyDTO.getNote());
        placeOfAssembly.setPermitNumber(placeOfAssemblyDTO.getPermitNumber());
        placeOfAssembly.setPermitObtainedDate(placeOfAssemblyDTO.getPermitObtainedDate());
        if (!ObjectUtils.isEmpty(placeOfAssemblyDTO.getBuilding())) {
            final Building building = this.buildingRepository.findByBuildingId(placeOfAssemblyDTO.getBuilding());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            placeOfAssembly.setBuilding(building);
        }
        placeOfAssembly.setType(!ObjectUtils.isEmpty(placeOfAssemblyDTO.getType()) ? new PlaceOfAssemblyType().setId(placeOfAssemblyDTO.getType()) : null);
        return this.setPlaceOfAssemblyDTO(this.placeOfAssemblyRepository.save(placeOfAssembly));
    }

    public PlaceOfAssemblyDTO getPlaceOfAssembly(final Long id) {
        final PlaceOfAssembly placeOfAssembly = this.placeOfAssemblyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ApplicationMessageStore.DATA_NOT_FOUND));
        return this.setPlaceOfAssemblyDTO(placeOfAssembly);
    }

    public List<PlaceOfAssemblyDTO> getAllPlaceOfAssembly(final Long buildingId) {
        final Building building = this.buildingRepository.findByBuildingId(buildingId);
        if (building == null) {
            throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
        }
        final List<PlaceOfAssembly> placeOfAssemblies = this.placeOfAssemblyRepository.findByBuilding(building);
        return placeOfAssemblies.stream().map(this::setPlaceOfAssemblyDTO).collect(Collectors.toList());
    }

    public PlaceOfAssemblyDTO updatePlaceOfAssembly(final PlaceOfAssemblyDTO placeOfAssemblyDTO) {
        if (ObjectUtils.isEmpty(placeOfAssemblyDTO.getId())) {
            throw new ValidationError(ApplicationMessageStore.ID_MANDATORY);
        }
        return this.createPlaceOfAssembly(placeOfAssemblyDTO);
    }

    public void deletePlaceOfAssembly(final Long id) {
        this.placeOfAssemblyRepository.deleteById(id);
    }

    public List<PlaceOfAssemblyType> getAllPlaceOfAssemblyTypes() {
        return this.placeOfAssemblyTypeRepository.findAll();
    }

    private PlaceOfAssemblyDTO setPlaceOfAssemblyDTO(final PlaceOfAssembly placeOfAssembly) {
        final PlaceOfAssemblyDTO placeOfAssemblyDTO = new PlaceOfAssemblyDTO();
        placeOfAssemblyDTO.setId(placeOfAssembly.getId());
        placeOfAssemblyDTO.setCapacity(placeOfAssembly.getCapacity());
        placeOfAssemblyDTO.setPermitNumber(placeOfAssembly.getPermitNumber());
        placeOfAssemblyDTO.setNote(placeOfAssembly.getNote());
        placeOfAssemblyDTO.setPermitObtainedDate(placeOfAssembly.getPermitObtainedDate());
        placeOfAssemblyDTO.setType(!ObjectUtils.isEmpty(placeOfAssembly.getType()) ? placeOfAssembly.getType().getId() : null);
        placeOfAssemblyDTO.setBuilding(!ObjectUtils.isEmpty(placeOfAssembly.getBuilding()) ? placeOfAssembly.getBuilding().getBuildingId() : null);
        return placeOfAssemblyDTO;
    }
}
