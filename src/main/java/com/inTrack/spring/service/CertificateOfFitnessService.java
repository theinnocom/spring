package com.inTrack.spring.service;

import com.inTrack.spring.dto.requestDTO.CertificateOfFitnessReqDTO;
import com.inTrack.spring.dto.responseDTO.CertificateOfFitnessResDTO;
import com.inTrack.spring.entity.*;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.CertificateOfFitnessRepository;
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
public class CertificateOfFitnessService {

    private final CertificateOfFitnessRepository certificateOfFitnessRepository;
    private final ValidatorService validatorService;
    private final FacilityRepository facilityRepository;
    private final BuildingRepository buildingRepository;

    public CertificateOfFitnessResDTO createCertificateOfFitness(final CertificateOfFitnessReqDTO certificateOfFitnessReqDTO, final Long certificateId) {
        final CertificateOfFitness certificateOfFitness;
        final User user = this.validatorService.validateUser(certificateOfFitnessReqDTO.getUserId());
        if (user == null) {
            throw new ValidationError(ApplicationMessageStore.USER_NOT_FOUND);
        }
        if (ObjectUtils.isEmpty(certificateId)) {
            certificateOfFitness = new CertificateOfFitness();
        } else {
            certificateOfFitness = this.certificateOfFitnessRepository.findByCertificateIdAndIsActive(certificateId, true);
        }
        certificateOfFitness.setCertificateNumber(certificateOfFitnessReqDTO.getCertificateNumber());
        certificateOfFitness.setType(certificateOfFitnessReqDTO.getType());
        certificateOfFitness.setIssuedDate(certificateOfFitnessReqDTO.getIssuedDate());
        certificateOfFitness.setExpiryDate(certificateOfFitnessReqDTO.getExpiryDate());
        certificateOfFitness.setDescription(certificateOfFitnessReqDTO.getDescription());
        certificateOfFitness.setIsActive(certificateOfFitnessReqDTO.getIsActive());
        certificateOfFitness.setPhoneNumber(certificateOfFitnessReqDTO.getPhoneNumber());
        certificateOfFitness.setNote(certificateOfFitnessReqDTO.getNote());
        certificateOfFitness.setName(certificateOfFitnessReqDTO.getName());
        certificateOfFitness.setEquipment(!ObjectUtils.isEmpty(certificateOfFitnessReqDTO.getEquipment()) ? new Equipment().setEquipmentId(certificateOfFitnessReqDTO.getEquipment()) : null);
        final Facility facility = this.facilityRepository.findByFacilityId(certificateOfFitnessReqDTO.getFacility());
        if (facility == null) {
            throw new ValidationError(ApplicationMessageStore.FACILITY_NOT_FOUND);
        }
        certificateOfFitness.setFacility(facility);
        certificateOfFitness.setUser(user);
        return this.setCertificateOfFitness(this.certificateOfFitnessRepository.save(certificateOfFitness));
    }

    public List<CertificateOfFitnessResDTO> getCertificateOfFitness(final Long certificateId) {
        List<CertificateOfFitness> certificateOfFitnessList = new LinkedList<>();
        if (certificateId != null) {
            certificateOfFitnessList.add(this.certificateOfFitnessRepository.findByCertificateIdAndIsActive(certificateId, true));
        } else {
            throw new ValidationError(ApplicationMessageStore.CERTIFICATE_NOT_FOUND);
        }
        if (ObjectUtils.isEmpty(certificateOfFitnessList)) {
            throw new ValidationError(ApplicationMessageStore.CERTIFICATE_NOT_FOUND);
        }
        final List<CertificateOfFitnessResDTO> certificateOfFitnessResDTOS = new LinkedList<>();
        certificateOfFitnessList.forEach(certificateOfFitness -> {
            certificateOfFitnessResDTOS.add(this.setCertificateOfFitness(certificateOfFitness));
        });
        return certificateOfFitnessResDTOS;
    }

    public CertificateOfFitnessResDTO updateCertificateOfFitness(final Long certificateId, final CertificateOfFitnessReqDTO certificateOfFitnessReqDTO) {
        return this.createCertificateOfFitness(certificateOfFitnessReqDTO, certificateId);
    }

    private CertificateOfFitnessResDTO setCertificateOfFitness(final CertificateOfFitness certificateOfFitness) {
        final CertificateOfFitnessResDTO certificateOfFitnessResDTO = new CertificateOfFitnessResDTO();
        certificateOfFitnessResDTO.setCertificateNumber(certificateOfFitness.getCertificateNumber());
        certificateOfFitnessResDTO.setType(certificateOfFitness.getType());
        certificateOfFitnessResDTO.setIssuedDate(certificateOfFitness.getIssuedDate());
        certificateOfFitnessResDTO.setExpiryDate(certificateOfFitness.getExpiryDate());
        certificateOfFitnessResDTO.setDescription(certificateOfFitness.getDescription());
        certificateOfFitnessResDTO.setIsActive(certificateOfFitness.getIsActive());
        certificateOfFitnessResDTO.setUserId(certificateOfFitness.getUser().getUserId());
        certificateOfFitnessResDTO.setPhoneNumber(certificateOfFitness.getPhoneNumber());
        certificateOfFitnessResDTO.setNote(certificateOfFitness.getNote());
        certificateOfFitnessResDTO.setFacilityId(certificateOfFitness.getFacility().getFacilityId());
        certificateOfFitnessResDTO.setName(certificateOfFitness.getName());
        return certificateOfFitnessResDTO;
    }

    public void deleteCertificateOfFitness(final Long certificateId) {
        final CertificateOfFitness certificateOfFitness = this.certificateOfFitnessRepository.findByCertificateIdAndIsActive(certificateId, true);
        if (certificateOfFitness == null) {
            throw new ValidationError(ApplicationMessageStore.CERTIFICATE_NOT_FOUND);
        }
        certificateOfFitness.setIsActive(false);
        this.certificateOfFitnessRepository.save(certificateOfFitness);
    }
}
