package com.inTrack.spring.service;

import com.inTrack.spring.dto.common.WorkPermitDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.WorkPermit;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.WorkPermitRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkPermitService {

    private final WorkPermitRepository workPermitRepository;
    private final BuildingRepository buildingRepository;
    private final JobFilingInformationRepository jobFilingInformationRepository;

    public WorkPermitDTO createWorkPermit(final WorkPermitDTO workPermitDTO) {
        WorkPermit workPermit;
        if (!ObjectUtils.isEmpty(workPermitDTO.getId())) {
            workPermit = this.workPermitRepository.findById(workPermitDTO.getId())
                    .orElseThrow(() -> new RuntimeException(ApplicationMessageStore.DATA_NOT_FOUND));
        } else {
            workPermit = new WorkPermit();
        }
        workPermit.setPermitNumber(workPermitDTO.getPermitNumber());
        workPermit.setWorkType(workPermitDTO.getWorkType());
        workPermit.setJobType(workPermitDTO.getJobType());
        workPermit.setDescription(workPermitDTO.getDescription());
        workPermit.setFilingDate(workPermitDTO.getFilingDate());
        workPermit.setJobStartDate(workPermitDTO.getJobStartDate());
        workPermit.setExpiryDate(workPermitDTO.getExpiryDate());
        workPermit.setStatus(workPermitDTO.getStatus());
        workPermit.setIssuedTo(workPermitDTO.getIssuedTo());
        workPermit.setRegistrationNumber(workPermitDTO.getRegistrationNumber());
        workPermit.setBusinessName(workPermitDTO.getBusinessName());
        workPermit.setPhoneNumber(workPermitDTO.getPhoneNumber());
        workPermit.setNote(workPermitDTO.getNote());
        if (!ObjectUtils.isEmpty(workPermitDTO.getBuilding())) {
            final Building building = this.buildingRepository.findByBuildingId(workPermitDTO.getBuilding());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            workPermit.setBuilding(building);
        }
        if (!ObjectUtils.isEmpty(workPermitDTO.getJobFilingInformation())) {
            final JobFilingInformation jobFilingInformation = this.jobFilingInformationRepository.findByJobFilingId(workPermitDTO.getJobFilingInformation());
            if (jobFilingInformation == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            workPermit.setJobFilingInformation(jobFilingInformation);
        }
        return this.setWorkPermitDTO(this.workPermitRepository.save(workPermit));
    }

    public WorkPermitDTO getWorkPermit(final Long id) {
        final WorkPermit workPermit = this.workPermitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ApplicationMessageStore.DATA_NOT_FOUND));
        return this.setWorkPermitDTO(workPermit);
    }

    public WorkPermitDTO updateWorkPermit(final WorkPermitDTO workPermitDTO) {
        if (ObjectUtils.isEmpty(workPermitDTO.getId())) {
            throw new ValidationError(ApplicationMessageStore.ID_MANDATORY);
        }
        return this.createWorkPermit(workPermitDTO);
    }

    public void deleteWorkPermit(final Long id) {
        this.workPermitRepository.deleteById(id);
    }

    public List<WorkPermitDTO> getAllWorkPermits(final Long buildingId) {
        final Building building = this.buildingRepository.findByBuildingId(buildingId);
        if (building == null) {
            throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
        }
        final List<WorkPermit> workPermits = this.workPermitRepository.findByBuilding(building);
        return workPermits.stream().map(this::setWorkPermitDTO).collect(Collectors.toList());
    }

    private WorkPermitDTO setWorkPermitDTO(final WorkPermit workPermit) {
        final WorkPermitDTO workPermitDTO = new WorkPermitDTO();
        workPermitDTO.setId(workPermit.getId());
        workPermitDTO.setPermitNumber(workPermit.getPermitNumber());
        workPermitDTO.setWorkType(workPermit.getWorkType());
        workPermitDTO.setJobType(workPermit.getJobType());
        workPermitDTO.setDescription(workPermit.getDescription());
        workPermitDTO.setFilingDate(workPermit.getFilingDate());
        workPermitDTO.setJobStartDate(workPermit.getJobStartDate());
        workPermitDTO.setExpiryDate(workPermit.getExpiryDate());
        workPermitDTO.setStatus(workPermit.getStatus());
        workPermitDTO.setIssuedTo(workPermit.getIssuedTo());
        workPermitDTO.setRegistrationNumber(workPermit.getRegistrationNumber());
        workPermitDTO.setBusinessName(workPermit.getBusinessName());
        workPermitDTO.setPhoneNumber(workPermit.getPhoneNumber());
        workPermitDTO.setNote(workPermit.getNote());
        workPermitDTO.setBuilding(!ObjectUtils.isEmpty(workPermit.getBuilding()) ? workPermit.getBuilding().getBuildingId() : null);
        workPermitDTO.setJobFilingInformation(!ObjectUtils.isEmpty(workPermit.getJobFilingInformation()) ? workPermit.getJobFilingInformation().getJobFilingId() : null);
        return workPermitDTO;
    }
}
