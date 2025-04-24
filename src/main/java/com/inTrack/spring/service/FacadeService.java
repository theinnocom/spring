package com.inTrack.spring.service;

import com.inTrack.spring.dto.requestDTO.FacadeReqDTO;
import com.inTrack.spring.dto.responseDTO.FacadeResDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.Facade;
import com.inTrack.spring.entity.User;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.FacadeRepository;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Vijayan
 */

@Service
@RequiredArgsConstructor
public class FacadeService {

    private final FacadeRepository facadeRepository;
    private final BuildingRepository buildingRepository;
    private final ValidatorService validatorService;

    public FacadeResDTO createFacade(final FacadeReqDTO facadeReqDTO, final Long facadeId) {
        final Facade facade;
        if (facadeId == null) {
            facade = new Facade();
            facade.setCreateAt(System.currentTimeMillis());
        } else {
            facade = this.facadeRepository.findByFacadeId(facadeId);
            facade.setCreateAt(System.currentTimeMillis());
        }
        facade.setIsFacadeInspectionAvailable(facadeReqDTO.getIsFacadeInspectionAvailable());
        facade.setFacadePeriod(facadeReqDTO.getFacadePeriod());
        facade.setApplicantName(facadeReqDTO.getApplicantName());
        facade.setCycle(facadeReqDTO.getCycle());
        facade.setCurrentStatus(facadeReqDTO.getCurrentStatus());
        facade.setNote(facadeReqDTO.getNote());
        facade.setLicense(facadeReqDTO.getLicense());
        facade.setFilingNumber(facadeReqDTO.getFilingNumber());
        facade.setExteriorWallType(facadeReqDTO.getExteriorWallType());
        facade.setPriorStatus(facadeReqDTO.getPriorStatus());
        facade.setInitialFilingDate(facadeReqDTO.getInitialFilingDate());
        facade.setInitialFilingStatus(facadeReqDTO.getInitialFilingStatus());
        facade.setLastCycleFilingDate(facadeReqDTO.getLastCycleFilingDate());
        facade.setNumberOfStories(facadeReqDTO.getNumberOfStories());
        facade.setSignedDate(facadeReqDTO.getSignedDate());
        facade.setNextCycleFilingDate(facadeReqDTO.getNextCycleFilingDate());
        final Building building = this.buildingRepository.findByBuildingIdAndIsActive(facadeReqDTO.getBuildingId(), true);
        if (building == null) {
            throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
        }
        facade.setBuilding(building);
        facade.setFieldInspectionCompletedDate(facadeReqDTO.getFieldInspectionCompletedDate());
        return this.setFacade(this.facadeRepository.save(facade));
    }

    public FacadeResDTO getFacade(final Long facadeId, final Long buildingId) {
        final Facade facade;
        if (facadeId == null && buildingId == null) {
            throw new ValidationError(ApplicationMessageStore.BAD_REQUEST);
        }
        if (facadeId == null) {
            final Building building = this.buildingRepository.findByBuildingIdAndIsActive(buildingId, true);
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            facade = this.facadeRepository.findByBuilding(building);
        } else {
            facade = this.facadeRepository.findByFacadeId(facadeId);
        }
        return this.setFacade(facade);
    }

    public FacadeResDTO updateFacade(final Long facadeId, final FacadeReqDTO facadeReqDTO) {
        if (facadeId == null) {
            throw new ValidationError(ApplicationMessageStore.FACADE_NOT_FOUND);
        }
        return this.createFacade(facadeReqDTO, facadeId);
    }

    public void deleteFacade(final Long facadeId) {
        final User user = this.validatorService.validateUser();
        if (!user.getRole().getRole().equalsIgnoreCase("ADMIN_SUPER_USER") && !user.getRole().getRole().equalsIgnoreCase("ADMIN_USER")) {
            throw new ValidationError(ApplicationMessageStore.ACCESS_DENIED);
        }
        this.facadeRepository.deleteById(facadeId);
    }

    private FacadeResDTO setFacade(final Facade facade) {
        final FacadeResDTO facadeResDTO = new FacadeResDTO();
        facadeResDTO.setIsFacadeInspectionAvailable(facade.getIsFacadeInspectionAvailable());
        facadeResDTO.setFacadePeriod(facade.getFacadePeriod());
        facadeResDTO.setApplicantName(facade.getApplicantName());
        facadeResDTO.setCycle(facade.getCycle());
        facadeResDTO.setCurrentStatus(facade.getCurrentStatus());
        facadeResDTO.setNote(facade.getNote());
        facadeResDTO.setLicense(facade.getLicense());
        facadeResDTO.setFilingNumber(facade.getFilingNumber());
        facadeResDTO.setExteriorWallType(facade.getExteriorWallType());
        facadeResDTO.setPriorStatus(facade.getPriorStatus());
        facadeResDTO.setInitialFilingDate(facade.getInitialFilingDate());
        facadeResDTO.setInitialFilingStatus(facade.getInitialFilingStatus());
        facadeResDTO.setLastCycleFilingDate(facade.getLastCycleFilingDate());
        facadeResDTO.setNumberOfStories(facade.getNumberOfStories());
        facadeResDTO.setSignedDate(facade.getSignedDate());
        facadeResDTO.setNextCycleFilingDate(facade.getNextCycleFilingDate());
        facadeResDTO.setBuilding(facade.getBuilding());
        facadeResDTO.setCreateAt(facade.getCreateAt());
        facadeResDTO.setUpdatedAt(facade.getUpdatedAt());
        return facadeResDTO;
    }
}
