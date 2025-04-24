package com.inTrack.spring.service;

import com.inTrack.spring.UtilService.CommonUtilService;
import com.inTrack.spring.UtilService.PaginationUtil;
import com.inTrack.spring.dto.PostLogDTO;
import com.inTrack.spring.dto.requestDTO.CommonListReqDTO;
import com.inTrack.spring.dto.responseDTO.CommonListResDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.Facility;
import com.inTrack.spring.entity.FacilityAgency.PostLogAgency;
import com.inTrack.spring.entity.PostLog;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.FacilityRepository;
import com.inTrack.spring.repository.PostLogAgencyRepository;
import com.inTrack.spring.repository.PostLogRepository;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostLogService {

    private final PostLogRepository postLogRepository;
    private final FacilityRepository facilityRepository;
    private final PostLogAgencyRepository postLogAgencyRepository;
    private final CommonUtilService commonUtilService;
    private final BuildingRepository buildingRepository;

    public PostLogDTO createPostLog(final Long employeeId, final PostLogDTO postLogDTO) {
        final PostLog postLog;
        if (!ObjectUtils.isEmpty(employeeId)) {
            postLog = this.postLogRepository.findByEmployeeId(employeeId);
        } else {
            postLog = new PostLog();
            final Facility facility = this.facilityRepository.findByFacilityId(postLogDTO.getFacilityId());
            if (facility == null) {
                throw new ValidationError(ApplicationMessageStore.FACILITY_NOT_FOUND);
            }
            postLog.setFacility(facility);
        }
        postLog.setEmployeeNo(postLogDTO.getEmployeeNo());
        postLog.setEmployeeName(postLogDTO.getEmployeeName());
        postLog.setJobTitle(postLogDTO.getJobTitle());
        postLog.setDateOfInjuryOrIllness(postLogDTO.getDateOfInjuryOrIllness());
        postLog.setIncidentTime(postLogDTO.getIncidentTime());
        postLog.setIncidentLocation(postLogDTO.getIncidentLocation());
        postLog.setInjuryOrIllnessDescription(postLogDTO.getInjuryOrIllnessDescription());
        postLog.setHarmfulObjectOrSubstance(postLogDTO.getHarmfulObjectOrSubstance());
        postLog.setActivityBeforeIncident(postLogDTO.getActivityBeforeIncident());
        postLog.setPreviousIllnessOrInjuryDescription(postLogDTO.getPreviousIllnessOrInjuryDescription());
        postLog.setHealthCareProfessional(postLogDTO.getHealthCareProfessional());
        postLog.setInjuryOrIllnessCategory(postLogDTO.getInjuryOrIllnessCategory());
        postLog.setDaysAwayFromWork(postLogDTO.getDaysAwayFromWork());
        postLog.setDescription(postLogDTO.getDescription());
        postLog.setNote(postLogDTO.getNote());
        postLog.setAnnualLogInspected(postLogDTO.getAnnualLogInspected());
        postLog.setLastInspectionDate(postLogDTO.getLastInspectionDate());
        postLog.setNextInspectionDate(postLogDTO.getNextInspectionDate());
        postLog.setInspectedBy(postLogDTO.getInspectedBy());
        final PostLog log = this.postLogRepository.save(postLog);
        return this.setPostLog(log);
    }

    public CommonListResDTO getPostLogByFacility(final Long facilityId, final CommonListReqDTO commonListReqDTO) {
        final Facility facility = this.facilityRepository.findByFacilityId(facilityId);
        if (facility == null) {
            throw new ValidationError(ApplicationMessageStore.FACILITY_NOT_FOUND);
        }
        final Pageable pageable = PaginationUtil.pageableWithoutShorting(commonListReqDTO.getPageNumber(), commonListReqDTO.getItemsPerPage());
        final Page<PostLog> postLogs = this.postLogRepository.findByFacility(facility.getFacilityId(), pageable);
        final List<PostLogDTO> postLogList = postLogs.stream().map(this::setPostLog).collect(Collectors.toList());
        return this.commonUtilService.setCommonListResDTO(postLogs, postLogList, true);
    }

    public PostLogDTO getPostLogById(final Long employeeId) {
        final PostLog postLog = this.postLogRepository.findByEmployeeId(employeeId);
        return this.setPostLog(postLog);
    }

    public PostLogDTO updatePostLog(final PostLogDTO postLogDTO) {
        return this.createPostLog(postLogDTO.getEmployeeId(), postLogDTO);
    }

    public void deletePostLog(final Long employeeId) {
        final PostLog postLog = this.postLogRepository.findByEmployeeId(employeeId);
        if (ObjectUtils.isEmpty(postLog)) {
            throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
        }
        final PostLogAgency postLogAgency = this.postLogAgencyRepository.findByPostLog(postLog);
        if (!ObjectUtils.isEmpty(postLogAgency)) {
            this.postLogAgencyRepository.delete(postLogAgency);
        }
        this.postLogRepository.delete(postLog);
    }

    private PostLogDTO setPostLog(final PostLog postLog) {
        final PostLogDTO postLogDTO = new PostLogDTO();
        postLogDTO.setEmployeeId(postLog.getEmployeeId());
        postLogDTO.setEmployeeNo(postLog.getEmployeeNo());
        postLogDTO.setEmployeeName(postLog.getEmployeeName());
        postLogDTO.setJobTitle(postLog.getJobTitle());
        postLogDTO.setDateOfInjuryOrIllness(postLog.getDateOfInjuryOrIllness());
        postLogDTO.setIncidentTime(postLog.getIncidentTime());
        postLogDTO.setIncidentLocation(postLog.getIncidentLocation());
        postLogDTO.setInjuryOrIllnessDescription(postLog.getInjuryOrIllnessDescription());
        postLogDTO.setHarmfulObjectOrSubstance(postLog.getHarmfulObjectOrSubstance());
        postLogDTO.setActivityBeforeIncident(postLog.getActivityBeforeIncident());
        postLogDTO.setPreviousIllnessOrInjuryDescription(postLog.getPreviousIllnessOrInjuryDescription());
        postLogDTO.setHealthCareProfessional(postLog.getHealthCareProfessional());
        postLogDTO.setInjuryOrIllnessCategory(postLog.getInjuryOrIllnessCategory());
        postLogDTO.setDaysAwayFromWork(postLog.getDaysAwayFromWork());
        postLogDTO.setDescription(postLog.getDescription());
        postLogDTO.setNote(postLog.getNote());
        postLogDTO.setFacilityId(postLog.getFacility().getFacilityId());
        postLogDTO.setAnnualLogInspected(postLog.getAnnualLogInspected());
        postLogDTO.setLastInspectionDate(postLog.getLastInspectionDate());
        postLogDTO.setNextInspectionDate(postLog.getNextInspectionDate());
        postLogDTO.setInspectedBy(postLog.getInspectedBy());
        return postLogDTO;
    }
}
