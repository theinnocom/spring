package com.inTrack.spring.service;

import com.inTrack.spring.dto.common.OccupancyCertificateInformationDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.OccupancyCertificateInformation;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.OccupancyCertificateInformationRepository;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OccupancyCertificateInformationService {

    private final OccupancyCertificateInformationRepository occupancyCertificateInformationRepository;
    private final BuildingRepository buildingRepository;

    public OccupancyCertificateInformationDTO createOccupancyCertificateInformation(final OccupancyCertificateInformationDTO certificateInformationDTO) {
        OccupancyCertificateInformation occupancyCertificateInformation;

        if (!ObjectUtils.isEmpty(certificateInformationDTO.getId())) {
            occupancyCertificateInformation = this.occupancyCertificateInformationRepository.findById(certificateInformationDTO.getId())
                    .orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
        } else {
            occupancyCertificateInformation = new OccupancyCertificateInformation();
        }
        occupancyCertificateInformation.setOccupancyNumber(certificateInformationDTO.getOccupancyNumber());
        occupancyCertificateInformation.setNumberOfStories(certificateInformationDTO.getNumberOfStories());
        occupancyCertificateInformation.setHeightInFeet(certificateInformationDTO.getHeightInFeet());
        occupancyCertificateInformation.setConstructionClassification(certificateInformationDTO.getConstructionClassification());
        occupancyCertificateInformation.setGroupClassification(certificateInformationDTO.getGroupClassification());
        occupancyCertificateInformation.setProtectionEquipment(certificateInformationDTO.getProtectionEquipment());
        occupancyCertificateInformation.setBuildingType(certificateInformationDTO.getBuildingType());
        occupancyCertificateInformation.setNote(certificateInformationDTO.getNote());
        if (!ObjectUtils.isEmpty(certificateInformationDTO.getBuilding())) {
            final Building building = this.buildingRepository.findByBuildingId(certificateInformationDTO.getBuilding());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            occupancyCertificateInformation.setBuilding(building);
        }
        return this.setOccupancyCertificateInformationDTO(this.occupancyCertificateInformationRepository.save(occupancyCertificateInformation));
    }

    public OccupancyCertificateInformationDTO getOccupancyCertificateInformation(final Long id) {
        final OccupancyCertificateInformation occupancyCertificateInformation = this.occupancyCertificateInformationRepository.findById(id)
                .orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
        return this.setOccupancyCertificateInformationDTO(occupancyCertificateInformation);
    }

    public List<OccupancyCertificateInformationDTO> getAllOccupancyCertificateInformation(final Long buildingId) {
        final Building building = this.buildingRepository.findByBuildingId(buildingId);
        if (building == null) {
            throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
        }
        final List<OccupancyCertificateInformation> occupancyCertificateInformations = this.occupancyCertificateInformationRepository.findByBuilding(building);
        return occupancyCertificateInformations.stream().map(this::setOccupancyCertificateInformationDTO).collect(Collectors.toList());
    }

    public OccupancyCertificateInformationDTO updateOccupancyCertificateInformation(final OccupancyCertificateInformationDTO occupancyCertificateInformationDTO) {
        if (ObjectUtils.isEmpty(occupancyCertificateInformationDTO.getId())) {
            throw new ValidationError(ApplicationMessageStore.ID_MANDATORY);
        }
        return this.createOccupancyCertificateInformation(occupancyCertificateInformationDTO);
    }

    public void deleteOccupancyCertificateInformation(final Long id) {
        this.occupancyCertificateInformationRepository.deleteById(id);
    }
    private OccupancyCertificateInformationDTO setOccupancyCertificateInformationDTO(final OccupancyCertificateInformation occupancyCertificateInformation) {
        final OccupancyCertificateInformationDTO occupancyCertificateInformationDTO = new OccupancyCertificateInformationDTO();
        occupancyCertificateInformationDTO.setId(occupancyCertificateInformation.getId());
        occupancyCertificateInformationDTO.setOccupancyNumber(occupancyCertificateInformation.getOccupancyNumber());
        occupancyCertificateInformationDTO.setNumberOfStories(occupancyCertificateInformation.getNumberOfStories());
        occupancyCertificateInformationDTO.setHeightInFeet(occupancyCertificateInformation.getHeightInFeet());
        occupancyCertificateInformationDTO.setConstructionClassification(occupancyCertificateInformation.getConstructionClassification());
        occupancyCertificateInformationDTO.setGroupClassification(occupancyCertificateInformation.getGroupClassification());
        occupancyCertificateInformationDTO.setProtectionEquipment(occupancyCertificateInformation.getProtectionEquipment());
        occupancyCertificateInformationDTO.setBuildingType(occupancyCertificateInformation.getBuildingType());
        occupancyCertificateInformationDTO.setNote(occupancyCertificateInformation.getNote());
        occupancyCertificateInformationDTO.setBuilding(!ObjectUtils.isEmpty(occupancyCertificateInformation.getBuilding()) ? occupancyCertificateInformation.getBuilding().getBuildingId() : null);
        return occupancyCertificateInformationDTO;
    }
}
