package com.inTrack.spring.service;

import com.inTrack.spring.dto.request.FacilityReqDTO;
import com.inTrack.spring.dto.response.FacilityResDTO;
import com.inTrack.spring.entity.Facility;
import com.inTrack.spring.entity.FacilityOwnerDetail;
import com.inTrack.spring.entity.User;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.FacilityRepository;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author vijayan
 */

@Service
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;
    private final ValidatorService validatorService;
    private final FacilityOwnerDetailService facilityOwnerDetailService;

    public FacilityResDTO createFacility(final FacilityReqDTO facilityReqDTO) {
        final User user = this.validatorService.validateUser();
        final Facility facility;
        if (facilityReqDTO.getId() != null) {
            facility = this.facilityRepository.findByFacilityIdAndActive(facilityReqDTO.getId(), true);
            if (facility == null) {
                throw new ValidationError(ApplicationMessageStore.FACILITY_NOT_FOUND);
            }
            facility.setUpdatedAt(System.currentTimeMillis());
        } else {
            facility = new Facility();
        }
        facility.setActive(true);
        facility.setFacilityNo(facilityReqDTO.getFacilityNo());
        facility.setCity(facilityReqDTO.getCity());
        facility.setAddressLine(facilityReqDTO.getAddressLine());
        facility.setAlterAddressLine(facilityReqDTO.getAlterAddressLine());
        facility.setPrimaryPhone(facilityReqDTO.getPrimaryPhone());
        facility.setAlterPhone(facilityReqDTO.getAlterPhone());
        facility.setFacilityEmail(facilityReqDTO.getFacilityEmail());
        facility.setFacilityName(facilityReqDTO.getFacilityName());
        facility.setContactPerson(facilityReqDTO.getContactPerson());
        facility.setContactTitle(facilityReqDTO.getContactTitle());
        facility.setState(facilityReqDTO.getState());
        facility.setZipcode(facilityReqDTO.getZipcode());
        facility.setFaxNo(facilityReqDTO.getFaxNo());
        facility.setWebsite(facilityReqDTO.getWebsite());
        facility.setNumberOfBuildings(facilityReqDTO.getNumberOfBuildings());
        facility.setCreatedBy(user);
        facility.setCreatedAt(System.currentTimeMillis());
        final FacilityOwnerDetail facilityOwnerDetail = this.facilityOwnerDetailService.createFacilityOwnerDetail(facilityReqDTO.getFacilityOwnerDetailDTO());
        if (facilityOwnerDetail == null) {
            throw new ValidationError(ApplicationMessageStore.FACILITY_OWNER_NOT_FOUND);
        }
        facility.setFacilityOwnerDetail(facilityOwnerDetail);
        return this.setFacility(this.facilityRepository.save(facility));
    }

    public List<FacilityResDTO> getFacilities(final Long id) {
        final User user = this.validatorService.validateUser();
        final List<FacilityResDTO> facilityResDTOS = new LinkedList<>();
        if (id != null && id != 0L) {
            facilityResDTOS.add(this.setFacility(this.facilityRepository.findByFacilityIdAndActive(id, true)));
        } else {
            final List<Facility> facilities = this.facilityRepository.findByCreatedByAndActive(user, true);
            facilities.forEach(facility -> facilityResDTOS.add(this.setFacility(facility)));
        }
        return facilityResDTOS;
    }

    public FacilityResDTO updateFacility(final FacilityReqDTO facilityReqDTO) {
        if (ObjectUtils.isEmpty(facilityReqDTO.getId())) {
            throw new ValidationError(ApplicationMessageStore.ID_MANDATORY);
        }
        return this.createFacility(facilityReqDTO);
    }

    public void deleteFacility(final Long id) {
        final Facility facility = this.facilityRepository.findByFacilityIdAndActive(id, true);
        if (facility == null) {
            throw new ValidationError(ApplicationMessageStore.FACILITY_NOT_FOUND);
        }
        facility.setActive(false);
        this.facilityRepository.save(facility);
    }

    private FacilityResDTO setFacility(final Facility facility) {
        final FacilityResDTO facilityResDTO = new FacilityResDTO();
        facilityResDTO.setFacilityId(facility.getFacilityId());
        facilityResDTO.setNumberOfBuildings(facility.getNumberOfBuildings());
        facilityResDTO.setCreatedAt(facility.getCreatedAt());
        facilityResDTO.setFacilityNo(facility.getFacilityNo());
        facilityResDTO.setCity(facility.getCity());
        facilityResDTO.setAddressLine(facility.getAddressLine());
        facilityResDTO.setAlterAddressLine(facility.getAlterAddressLine());
        facilityResDTO.setPrimaryPhone(facility.getPrimaryPhone());
        facilityResDTO.setAlterPhone(facility.getAlterPhone());
        facilityResDTO.setFacilityEmail(facility.getFacilityEmail());
        facilityResDTO.setFacilityName(facility.getFacilityName());
        facilityResDTO.setContactPerson(facility.getContactPerson());
        facilityResDTO.setContactTitle(facility.getContactTitle());
        facilityResDTO.setState(facility.getState());
        facilityResDTO.setZipcode(facility.getZipcode());
        facilityResDTO.setFaxNo(facility.getFaxNo());
        facilityResDTO.setWebsite(facility.getWebsite());
        facilityResDTO.setCreatedBy(facility.getCreatedBy().getCreatedAt());
        facilityResDTO.setCreatedAt(facility.getCreatedAt());
        facilityResDTO.setFacilityOwnerDetail(facility.getFacilityOwnerDetail());
        return facilityResDTO;
    }
}
