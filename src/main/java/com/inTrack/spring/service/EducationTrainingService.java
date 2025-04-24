package com.inTrack.spring.service;

import com.inTrack.spring.dto.common.EducationTrainingDTO;
import com.inTrack.spring.entity.EducationTraining;
import com.inTrack.spring.entity.Facility;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.EducationTrainingRepository;
import com.inTrack.spring.repository.FacilityRepository;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationTrainingService {

    private final EducationTrainingRepository educationTrainingRepository;
    private final FacilityRepository facilityRepository;

    public EducationTrainingDTO createEducationTraining(final EducationTrainingDTO educationTrainingDTO) {
        final EducationTraining educationTraining;
        if (!ObjectUtils.isEmpty(educationTrainingDTO.getId())) {
            educationTraining = this.educationTrainingRepository.findById(educationTrainingDTO.getId())
                    .orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
        } else {
            educationTraining = new EducationTraining();
            final Facility facility = this.facilityRepository.findByFacilityId(educationTrainingDTO.getFacility());
            if (facility == null) {
                throw new ValidationError(ApplicationMessageStore.FACILITY_NOT_FOUND);
            }
            educationTraining.setFacility(facility);
        }
        educationTraining.setLastTrainingDate(educationTrainingDTO.getLastTrainingDate());
        educationTraining.setTrainingFrequency(educationTrainingDTO.getTrainingFrequency());
        educationTraining.setTrainingOfficerName(educationTrainingDTO.getTrainingOfficerName());
        educationTraining.setTrainingTitle(educationTrainingDTO.getTrainingTitle());
        educationTraining.setDescription(educationTrainingDTO.getDescription());
        educationTraining.setNote(educationTrainingDTO.getNote());
        educationTraining.setRegulatoryAuthority(educationTrainingDTO.getRegulatoryAuthority());
        return this.setEducationTraining(this.educationTrainingRepository.save(educationTraining));
    }

    public List<EducationTrainingDTO> getEducationTrainingList(final Long facilityId) {
        final Facility facility = this.facilityRepository.findByFacilityId(facilityId);
        if (facility == null) {
            throw new ValidationError(ApplicationMessageStore.FACILITY_NOT_FOUND);
        }
        final List<EducationTraining> educationTrainings = this.educationTrainingRepository.findByFacility(facility);
        return educationTrainings.stream()
                .map(this::setEducationTraining).collect(Collectors.toList());
    }

    public EducationTrainingDTO getEducationTraining(final Long trainingId) {
        final EducationTraining educationTraining = this.educationTrainingRepository.findById(trainingId)
                .orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
        return this.setEducationTraining(educationTraining);
    }

    public EducationTrainingDTO updateEducationTraining(final EducationTrainingDTO educationTrainingDTO) {
        if (ObjectUtils.isEmpty(educationTrainingDTO.getId())) {
            throw new ValidationError(ApplicationMessageStore.ID_MANDATORY);
        }
        return this.createEducationTraining(educationTrainingDTO);
    }

    public void deleteEducationTraining(final Long trainingId) {
        this.educationTrainingRepository.deleteById(trainingId);
    }

    private EducationTrainingDTO setEducationTraining(final EducationTraining educationTraining) {
        final EducationTrainingDTO educationTrainingDTO = new EducationTrainingDTO();
        educationTrainingDTO.setId(educationTraining.getId());
        educationTrainingDTO.setLastTrainingDate(educationTraining.getLastTrainingDate());
        educationTrainingDTO.setTrainingFrequency(educationTraining.getTrainingFrequency());
        educationTrainingDTO.setTrainingOfficerName(educationTraining.getTrainingOfficerName());
        educationTrainingDTO.setTrainingTitle(educationTraining.getTrainingTitle());
        educationTrainingDTO.setDescription(educationTraining.getDescription());
        educationTrainingDTO.setNote(educationTraining.getNote());
        educationTrainingDTO.setRegulatoryAuthority(educationTraining.getRegulatoryAuthority());
        educationTrainingDTO.setFacility(educationTraining.getFacility().getFacilityId());
        return educationTrainingDTO;
    }
}
