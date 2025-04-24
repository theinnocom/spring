package com.inTrack.spring.service;

import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobFilingInfoService {

    private final JobFilingInformationRepository jobFilingInformationRepository;
    private final BuildingRepository buildingRepository;

    public JobFilingInformationReqDTO createJobFilingInformation(final JobFilingInformationReqDTO jobFilingInformationReqDTO) {
        JobFilingInformation jobFilingInformation;
        if (!ObjectUtils.isEmpty(jobFilingInformationReqDTO.getJobFilingId())) {
            jobFilingInformation = this.jobFilingInformationRepository.findByJobFilingId(jobFilingInformationReqDTO.getJobFilingId());
            if (jobFilingInformation == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
        } else {
            jobFilingInformation = new JobFilingInformation();
        }
        jobFilingInformation.setJobNumber(jobFilingInformationReqDTO.getJobNumber());
        jobFilingInformation.setType(jobFilingInformationReqDTO.getType());
        jobFilingInformation.setStatus(jobFilingInformationReqDTO.getStatus());
        jobFilingInformation.setDescription(jobFilingInformationReqDTO.getDescription());
        jobFilingInformation.setApprovalDate(jobFilingInformationReqDTO.getApprovalDate());
        jobFilingInformation.setSignOffDate(jobFilingInformationReqDTO.getSignOffDate());
        jobFilingInformation.setCommands(jobFilingInformationReqDTO.getCommands());
        jobFilingInformation.setNotes(jobFilingInformationReqDTO.getNotes());
        jobFilingInformation.setEquipmentName(jobFilingInformationReqDTO.getEquipmentName());
        if (!ObjectUtils.isEmpty(jobFilingInformationReqDTO.getBuildingId())) {
            final Building building = this.buildingRepository.findByBuildingId(jobFilingInformationReqDTO.getBuildingId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            jobFilingInformation.setBuilding(building);
        }
        return this.setJobFilingInformationReqDTO(this.jobFilingInformationRepository.save(jobFilingInformation));
    }

    public JobFilingInformationReqDTO getJobFilingInformation(final Long id) {
        final JobFilingInformation jobFilingInformation = this.jobFilingInformationRepository.findByJobFilingId(id);
        if (jobFilingInformation == null) {
            throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
        }
        return this.setJobFilingInformationReqDTO(jobFilingInformation);
    }

    public List<JobFilingInformationReqDTO> getAllJobFilingInformation(final Long buildingId) {
        final Building building = this.buildingRepository.findByBuildingId(buildingId);
        if (building == null) {
            throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
        }
        final List<JobFilingInformation> jobFilingInformations = this.jobFilingInformationRepository.findByBuilding(building);
        return jobFilingInformations.stream().map(this::setJobFilingInformationReqDTO).collect(Collectors.toList());
    }

    public JobFilingInformationReqDTO updateJobFilingInformation(final JobFilingInformationReqDTO jobFilingInformationReqDTO) {
        if (ObjectUtils.isEmpty(jobFilingInformationReqDTO.getJobFilingId())) {
            throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
        }
        return this.createJobFilingInformation(jobFilingInformationReqDTO);
    }

    public void deleteJobFilingInformation(final Long id) {
        this.jobFilingInformationRepository.deleteById(id);
    }

    private JobFilingInformationReqDTO setJobFilingInformationReqDTO(final JobFilingInformation jobFilingInformation) {
        final JobFilingInformationReqDTO jobFilingInformationReqDTO = new JobFilingInformationReqDTO();
        jobFilingInformationReqDTO.setJobNumber(jobFilingInformation.getJobNumber());
        jobFilingInformationReqDTO.setType(jobFilingInformation.getType());
        jobFilingInformationReqDTO.setStatus(jobFilingInformation.getStatus());
        jobFilingInformationReqDTO.setDescription(jobFilingInformation.getDescription());
        jobFilingInformationReqDTO.setApprovalDate(jobFilingInformation.getApprovalDate());
        jobFilingInformationReqDTO.setSignOffDate(jobFilingInformation.getSignOffDate());
        jobFilingInformationReqDTO.setCommands(jobFilingInformation.getCommands());
        jobFilingInformationReqDTO.setNotes(jobFilingInformation.getNotes());
        jobFilingInformationReqDTO.setEquipmentName(jobFilingInformation.getEquipmentName());
        jobFilingInformationReqDTO.setBuildingId(!ObjectUtils.isEmpty(jobFilingInformation.getBuilding()) ? jobFilingInformation.getBuilding().getBuildingId() : null);
        return jobFilingInformationReqDTO;
    }
}
