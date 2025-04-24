package com.inTrack.spring.service;

import com.inTrack.spring.dto.common.DERStaffCertificationDTO;
import com.inTrack.spring.entity.CertificateType;
import com.inTrack.spring.entity.DERStaffCertification;
import com.inTrack.spring.entity.Spcc;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.DERStaffCertificationRepository;
import com.inTrack.spring.repository.SpccRepository;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DERStaffCertificationService {

    private final DERStaffCertificationRepository derStaffCertificationRepository;
    private final SpccRepository spccRepository;


    public List<DERStaffCertificationDTO> createDerStaffCertification(final List<DERStaffCertificationDTO> derStaffCertificationDTOS, final Spcc spcc) {
        final List<DERStaffCertification> derStaffCertifications = new LinkedList<>();
        derStaffCertificationDTOS.forEach(derStaffCertificationDTO -> {
            DERStaffCertification derStaffCertification;
            if (!ObjectUtils.isEmpty(derStaffCertificationDTO.getId())) {
                derStaffCertification = this.derStaffCertificationRepository.findById(derStaffCertificationDTO.getId())
                        .orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
            } else {
                derStaffCertification = new DERStaffCertification();
            }
            derStaffCertification.setStaffName(derStaffCertificationDTO.getStaffName());
            derStaffCertification.setCertificationAvailable(derStaffCertificationDTO.getCertificationAvailable());
            derStaffCertification.setIssueDate(derStaffCertificationDTO.getIssueDate());
            derStaffCertification.setAuthorizationNumber(derStaffCertificationDTO.getAuthorizationNumber());
            derStaffCertification.setCertificateType(!ObjectUtils.isEmpty(derStaffCertificationDTO.getCertificateType()) ? new CertificateType().setId(derStaffCertificationDTO.getCertificateType()) : null);
            derStaffCertification.setSpcc(spcc);
            derStaffCertifications.add(derStaffCertification);
        });
        final List<DERStaffCertification> derStaffCertificationList = this.derStaffCertificationRepository.saveAll(derStaffCertifications);
        return derStaffCertificationList.stream().map(this::setDerStaffCertificationDTO).collect(Collectors.toList());
    }

    public List<DERStaffCertificationDTO> getAllDerStaffCertificationBySpcc(final Long spccId) {
        final List<DERStaffCertificationDTO> derStaffCertificationDTOS = new LinkedList<>();
        final Spcc spcc = this.spccRepository.findById(spccId).orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
        final List<DERStaffCertification> derStaffCertifications = this.derStaffCertificationRepository.findBySpcc(spcc);
        derStaffCertifications.forEach(derStaffCertification -> derStaffCertificationDTOS.add(this.setDerStaffCertificationDTO(derStaffCertification)));
        return derStaffCertificationDTOS;
    }

    public DERStaffCertificationDTO getDerStaffCertificationById(final Long id) {
        final DERStaffCertification derStaffCertification = this.derStaffCertificationRepository.findById(id)
                .orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
        return this.setDerStaffCertificationDTO(derStaffCertification);
    }

    public void deleteDerStaffCertification(final Spcc spcc) {
        final List<DERStaffCertification> derStaffCertifications = this.derStaffCertificationRepository.findBySpcc(spcc);
        this.derStaffCertificationRepository.deleteAll(derStaffCertifications);
    }

    private DERStaffCertificationDTO setDerStaffCertificationDTO(final DERStaffCertification derStaffCertification) {
        final DERStaffCertificationDTO derStaffCertificationDTO = new DERStaffCertificationDTO();
        derStaffCertificationDTO.setId(derStaffCertification.getId());
        derStaffCertificationDTO.setStaffName(derStaffCertification.getStaffName());
        derStaffCertificationDTO.setCertificationAvailable(derStaffCertification.getCertificationAvailable());
        derStaffCertificationDTO.setIssueDate(derStaffCertification.getIssueDate());
        derStaffCertificationDTO.setAuthorizationNumber(derStaffCertification.getAuthorizationNumber());
        derStaffCertificationDTO.setCertificateType(!ObjectUtils.isEmpty(derStaffCertification.getCertificateType()) ? derStaffCertification.getCertificateType().getId() : null);
        derStaffCertificationDTO.setSpcc(derStaffCertification.getSpcc().getId());
        return derStaffCertificationDTO;
    }
}
