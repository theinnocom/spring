package com.inTrack.spring.service;

import com.inTrack.spring.dto.common.DERStaffCertificationDTO;
import com.inTrack.spring.dto.common.PBSCapacityDTO;
import com.inTrack.spring.dto.common.SpccDto;
import com.inTrack.spring.entity.AuditCycle;
import com.inTrack.spring.entity.CertificateType;
import com.inTrack.spring.entity.Facility;
import com.inTrack.spring.entity.Spcc;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.AuditCycleRepository;
import com.inTrack.spring.repository.CertificateTypeRepository;
import com.inTrack.spring.repository.FacilityRepository;
import com.inTrack.spring.repository.SpccRepository;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpccService {

    private final SpccRepository spccRepository;
    private final FacilityRepository facilityRepository;
    private final AuditCycleRepository auditCycleRepository;
    private final DERStaffCertificationService derStaffCertificationService;
    private final CertificateTypeRepository certificateTypeRepository;

    public SpccDto createSpcc(final SpccDto spccDto) {
        Spcc spcc;
        if (!ObjectUtils.isEmpty(spccDto.getId())) {
            spcc = this.spccRepository.findById(spccDto.getId())
                    .orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
        } else {
            spcc = new Spcc();
        }
        spcc.setPbsNumber(spccDto.getPbsNumber());
        spcc.setPbsExpiration(spccDto.getPbsExpiration());
        spcc.setCumulativeAst(spccDto.getCumulativeAst());
        spcc.setCumulativeUst(spccDto.getCumulativeUst());
        spcc.setSpccPlanRequired(spccDto.getSpccPlanRequired());
        spcc.setLastPlanDate(spccDto.getLastPlanDate());
        spcc.setNextPlanDate(spccDto.getNextPlanDate());
        spcc.setSpccTrainingRequired(spccDto.getSpccTrainingRequired());
        spcc.setLastTrainingDate(spccDto.getLastTrainingDate());
        spcc.setNextTrainingDate(spccDto.getNextTrainingDate());
        spcc.setSpccAuditRequired(spccDto.getSpccAuditRequired());
        spcc.setDer40CertifiedStaffCount(spccDto.getDer40CertifiedStaffCount());
        spcc.setJobNumber(spccDto.getJobNumber());
        spcc.setNote(spccDto.getNote());
        spcc.setAuditCycle(!ObjectUtils.isEmpty(spccDto.getAuditCycle()) ? new AuditCycle().setId(spccDto.getAuditCycle()) : null);
        if (!ObjectUtils.isEmpty(spccDto.getFacility())) {
            final Facility facility = this.facilityRepository.findByFacilityId(spccDto.getFacility());
            if (facility == null) {
                throw new ValidationError(ApplicationMessageStore.FACILITY_NOT_FOUND);
            }
            spcc.setFacility(facility);
        }
        final Spcc saveSpcc = this.spccRepository.save(spcc);
        final List<DERStaffCertificationDTO> derStaffCertificationDTOList = this.derStaffCertificationService.createDerStaffCertification(spccDto.getDerStaffCertificationDTOS(), saveSpcc);
        return this.setSpccDto(saveSpcc, derStaffCertificationDTOList);
    }

    public List<SpccDto> getAllSpccByFacility(final Long facilityId) {
        final List<SpccDto> spccDtoList = new LinkedList<>();
        final Facility facility = this.facilityRepository.findByFacilityId(facilityId);
        if (facility == null) {
            throw new ValidationError(ApplicationMessageStore.FACILITY_NOT_FOUND);
        }
        final List<Spcc> spccList = this.spccRepository.findByFacility(facility);
        spccList.forEach(spcc -> spccDtoList.add(this.setSpccDto(spcc, null)));
        return spccDtoList;
    }

    public SpccDto getSpccById(final Long id) {
        final Spcc spcc = this.spccRepository.findById(id)
                .orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
        final List<DERStaffCertificationDTO> derStaffCertificationDTOS = this.derStaffCertificationService.getAllDerStaffCertificationBySpcc(spcc.getId());
        return this.setSpccDto(spcc, derStaffCertificationDTOS);
    }

    public SpccDto updateSpcc(final SpccDto spccDto) {
        if (ObjectUtils.isEmpty(spccDto.getId())) {
            throw new ValidationError(ApplicationMessageStore.ID_MANDATORY);
        }
        return this.createSpcc(spccDto);
    }

    public void deleteSpcc(final Long id) {
        final Spcc spcc = this.spccRepository.findById(id)
                .orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
        this.derStaffCertificationService.deleteDerStaffCertification(spcc);
        this.spccRepository.delete(spcc);
    }

    public List<AuditCycle> getAuditCycle() {
        return this.auditCycleRepository.findAll();
    }

    public List<CertificateType> getAllCertificateTypes() {
        return this.certificateTypeRepository.findAll();
    }

    public List<PBSCapacityDTO> getPbsCapacity(final Long pbsNo, final Long facilityId) {
        final List<PBSCapacityDTO> pbsCapacityDTOS = new LinkedList<>();
        final List<Object[]> pbsCapacity = this.spccRepository.getPbsCapacity(pbsNo, facilityId);
        pbsCapacity.forEach(pbs -> {
            final PBSCapacityDTO pbsCapacityDTO = new PBSCapacityDTO();
            pbsCapacityDTO.setCapacity(Double.parseDouble(pbs[0].toString()));
            pbsCapacityDTO.setTankLocation(pbs[1].toString());
            pbsCapacityDTOS.add(pbsCapacityDTO);
        });
        return pbsCapacityDTOS;
    }

    private SpccDto setSpccDto(final Spcc spcc, List<DERStaffCertificationDTO> derStaffCertificationDTOS) {
        final SpccDto spccDto = new SpccDto();
        spccDto.setId(spcc.getId());
        spccDto.setPbsNumber(spcc.getPbsNumber());
        spccDto.setPbsExpiration(spcc.getPbsExpiration());
        spccDto.setCumulativeAst(spcc.getCumulativeAst());
        spccDto.setCumulativeUst(spcc.getCumulativeUst());
        spccDto.setSpccPlanRequired(spcc.getSpccPlanRequired());
        spccDto.setLastPlanDate(spcc.getLastPlanDate());
        spccDto.setNextPlanDate(spcc.getNextPlanDate());
        spccDto.setSpccTrainingRequired(spcc.getSpccTrainingRequired());
        spccDto.setLastTrainingDate(spcc.getLastTrainingDate());
        spccDto.setNextTrainingDate(spcc.getNextTrainingDate());
        spccDto.setSpccAuditRequired(spcc.getSpccAuditRequired());
        spccDto.setDer40CertifiedStaffCount(spcc.getDer40CertifiedStaffCount());
        spccDto.setJobNumber(spcc.getJobNumber());
        spccDto.setNote(spcc.getNote());
        spccDto.setAuditCycle(!ObjectUtils.isEmpty(spcc.getAuditCycle()) ? spcc.getAuditCycle().getId() : null);
        spccDto.setFacility(!ObjectUtils.isEmpty(spcc.getFacility()) ? spcc.getFacility().getFacilityId() : null);
        if (!ObjectUtils.isEmpty(derStaffCertificationDTOS)) {
            spccDto.setDerStaffCertificationDTOS(derStaffCertificationDTOS);
        }
        return spccDto;
    }
}
