package com.inTrack.spring.service;

import com.inTrack.spring.dto.request.AddressReqDTO;
import com.inTrack.spring.dto.request.BuildingReqDTO;
import com.inTrack.spring.dto.response.BuildingResDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.Facility;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.FacilityRepository;
import com.inTrack.spring.store.ApplicationMessageStore;
import com.inTrack.spring.store.ConstantStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildingService {

    private final BuildingRepository buildingRepository;
    private final FacilityRepository facilityRepository;

    public BuildingResDTO createBuilding(final BuildingReqDTO buildingReqDTO, final Long buildingId) {
        try {
            final Building building;
            if (buildingId != null) {
                building = this.buildingRepository.findByBuildingIdAndIsActive(buildingId, true);
                if (building == null) {
                    throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
                }
            } else {
                building = new Building();
                building.setBinNo(buildingReqDTO.getBinNo());
            }
            building.setBuildingName(buildingReqDTO.getBuildingName());
            building.setBlockNumber(buildingReqDTO.getBlockNumber());
            building.setLotNumber(buildingReqDTO.getLotNumber());
            building.setIsActive(buildingReqDTO.isActive());
            building.setCertificateAvailability(buildingReqDTO.getCertificateAvailability() == 1 ? ConstantStore.CERTIFICATE_AVAILABILITY_STATUS_YES : buildingReqDTO.getCertificateAvailability() == 2 ? ConstantStore.CERTIFICATE_AVAILABILITY_STATUS_NO : ConstantStore.CERTIFICATE_NOT_REQUIRED);
            building.setCertificateNumber(buildingReqDTO.getCertificateAvailability() == 1 ? buildingReqDTO.getCertificateNumber() : null);
            building.setNoOfStories(buildingReqDTO.getNoOfStories());
            building.setOccupancyClassification(buildingReqDTO.getOccupancyClassification());
            building.setNotes(buildingReqDTO.getNotes());
            building.setCity(buildingReqDTO.getCity());
            building.setState(buildingReqDTO.getState());
            building.setAddressLine(buildingReqDTO.getAddressLine());
            building.setAlterAddressLine(buildingReqDTO.getAlterAddressLine());
            building.setZipcode(buildingReqDTO.getZipcode());
            final Facility facility = this.facilityRepository.findByFacilityIdAndActive(buildingReqDTO.getFacilityId(), true);
            if (facility == null) {
                throw new ValidationError(ApplicationMessageStore.FACILITY_NOT_FOUND);
            }
            building.setFacility(facility);
            return this.setBuilding(this.buildingRepository.save(building));
        } catch (final DataIntegrityViolationException e) {
            throw new ValidationError(ApplicationMessageStore.DATA_ALREADY_EXIST);
        }
    }

    public List<BuildingResDTO> getBuildings(final Long buildingId, final Long facilityId) {
        List<Building> buildings = new LinkedList<>();
        if (buildingId == null) {
            final Facility facility = this.facilityRepository.findByFacilityIdAndActive(facilityId, true);
            buildings = this.buildingRepository.findByFacility(facility);
        } else {
            buildings.add(this.buildingRepository.findByBuildingIdAndIsActive(buildingId, true));
        }
        return buildings.stream().map(this::setBuilding).toList();
    }

    public BuildingResDTO updateBuilding(final BuildingReqDTO buildingReqDTO, final Long buildingId) {
        return this.createBuilding(buildingReqDTO, buildingId);
    }

    private BuildingResDTO setBuilding(final Building building) {
        final BuildingResDTO buildingResDTO = new BuildingResDTO();
        buildingResDTO.setBuildingId(building.getBuildingId());
        buildingResDTO.setBuildingName(building.getBuildingName());
        buildingResDTO.setBlockNumber(building.getBlockNumber());
        buildingResDTO.setLotNumber(building.getLotNumber());
        buildingResDTO.setBinNo(building.getBinNo());
        buildingResDTO.setCertificateAvailability((byte) (building.getCertificateAvailability().equalsIgnoreCase(ConstantStore.CERTIFICATE_AVAILABILITY_STATUS_YES) ? 1 : building.getCertificateAvailability().equalsIgnoreCase(ConstantStore.CERTIFICATE_AVAILABILITY_STATUS_NO) ? 2 : 3));
        buildingResDTO.setCertificateNumber(building.getCertificateNumber());
        buildingResDTO.setNoOfStories(building.getNoOfStories());
        buildingResDTO.setFacilityId(building.getFacility().getFacilityId());
        buildingResDTO.setNotes(building.getNotes());
        buildingResDTO.setNotes(building.getNotes());
        buildingResDTO.setCity(building.getCity());
        buildingResDTO.setState(building.getState());
        buildingResDTO.setAddressLine(building.getAddressLine());
        buildingResDTO.setAlterAddressLine(building.getAlterAddressLine());
        buildingResDTO.setZipcode(building.getZipcode());
        return buildingResDTO;
    }

    public void deleteBuilding(final Long buildingId) {
        final Building building = this.buildingRepository.findByBuildingIdAndIsActive(buildingId, true);
        if (building == null) {
            throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
        }
        building.setIsActive(false);
        this.buildingRepository.save(building);
    }
}
