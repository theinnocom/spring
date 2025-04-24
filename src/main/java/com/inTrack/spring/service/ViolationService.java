package com.inTrack.spring.service;

import com.inTrack.spring.UtilService.CommonUtilService;
import com.inTrack.spring.UtilService.PaginationUtil;
import com.inTrack.spring.dto.requestDTO.ViolationReqDTO;
import com.inTrack.spring.dto.requestDTO.ViolationSearchDTO;
import com.inTrack.spring.dto.responseDTO.*;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.HearingStatus;
import com.inTrack.spring.entity.IssuingAgency;
import com.inTrack.spring.entity.Violation;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.*;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vijayan
 */

@Service
@RequiredArgsConstructor
public class ViolationService {

    private final ViolationRepository violationRepository;
    private final BuildingRepository buildingRepository;
    private final AgencyRepository agencyRepository;
    private final CertificateOfCorrectionRepository certificateOfCorrectionRepository;
    private final IssuingAgencyRepository issuingAgencyRepository;
    private final HearingStatusRepository hearingStatusRepository;
    private final CommonUtilService commonUtilService;

    public ViolationResDTO createViolation(final ViolationReqDTO violationReqDTO, final Long violationId) {
        final Violation violation;
        if (violationId == null) {
            violation = new Violation();
            violation.setCreatedAt(System.currentTimeMillis());
            violation.setViolationNo(violationReqDTO.getViolationNo());
        } else {
            violation = this.violationRepository.findByViolationIdAndIsDelete(violationId, false);
            violation.setUpdatedAt(System.currentTimeMillis());
        }
        if (violationReqDTO.getIssuingAgency() == null) {
            throw new ValidationError(ApplicationMessageStore.ISSUING_AGENCY_MANDATORY);
        }
        violation.setIssuingAgency(!ObjectUtils.isEmpty(violationReqDTO.getIssuingAgency()) ? new IssuingAgency().setId(violationReqDTO.getIssuingAgency()) : null);
        violation.setIssuedDate(violationReqDTO.getIssuedDate());
        violation.setHearingDate(violationReqDTO.getHearingDate());
        violation.setHearingResult(violationReqDTO.getHearingResult());
        violation.setHearingStatus(!ObjectUtils.isEmpty(violationReqDTO.getHearingStatus()) ? new HearingStatus().setId(violationReqDTO.getHearingStatus()) : null);
        violation.setCodeSection(violationReqDTO.getCodeSection());
        violation.setCode(violationReqDTO.getCode());
        violation.setComplianceDate(violationReqDTO.getComplianceDate());
        violation.setDescription(violationReqDTO.getDescription());
        violation.setBalanceDue(violationReqDTO.getBalanceDue());
        violation.setPaidAmount(violationReqDTO.getPaidAmount());
        violation.setPenaltyImposed(violationReqDTO.getPenaltyImposed());
        violation.setRespondentName(violationReqDTO.getRespondentName());
        violation.setNote(violationReqDTO.getNote());
        violation.setHearingAttended(violationReqDTO.getHearingAttended());
        violation.setFinePaid(violationReqDTO.getFinePaid());
        violation.setWaived(violationReqDTO.getWaived());
        violation.setDismissed(violationReqDTO.getDismissed());
        violation.setStatusComments(violationReqDTO.getStatusComments());
        violation.setCertificateOfCorrection(violationReqDTO.getCertificateOfCorrection());
        final Building building = this.buildingRepository.findByBuildingIdAndIsActive(violationReqDTO.getBuildingId(), true);
        if (building == null) {
            throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
        }
        violation.setBuilding(building);
        return this.setViolation(this.violationRepository.save(violation));
    }

    public List<ViolationResDTO> getViolations(final Long violationId, final Long buildingId, final Boolean isDelete) {
        final List<Violation> violations = new LinkedList<>();
        if (violationId != null) {
            final Violation violation = this.violationRepository.findByViolationIdAndIsDelete(violationId, isDelete);
            if (violation == null) {
                throw new ValidationError(ApplicationMessageStore.VIOLATION_NOT_FOUND);
            }
            violations.add(violation);
        } else {
            final Building building = this.buildingRepository.findByBuildingIdAndIsActive(buildingId, true);
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            final List<Violation> violationList = this.violationRepository.findAllByBuildingAndIsDelete(building, isDelete);
            if (violationList == null || violationList.isEmpty()) {
                throw new ValidationError(ApplicationMessageStore.VIOLATION_NOT_FOUND);
            }
            violations.addAll(violationList);
        }
        return violations.stream()
                .map(this::setViolation)
                .collect(Collectors.toList());
    }

    public ViolationResDTO updateViolation(final Long violationId, final ViolationReqDTO violationReqDTO) {
        if (violationId == null) {
            throw new ValidationError(ApplicationMessageStore.VIOLATION_NOT_FOUND);
        }
        return this.createViolation(violationReqDTO, violationId);
    }

    public void deleteViolation(final Long violationId) {
        if (violationId == null) {
            throw new ValidationError(ApplicationMessageStore.VIOLATION_NOT_FOUND);
        }
        final Violation violation = this.violationRepository.findByViolationIdAndIsDelete(violationId, false);
        violation.setIsDelete(true);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        Date futureDate = calendar.getTime();
        violation.setDeletionExpirationDate(futureDate.getTime());
        this.violationRepository.save(violation);
    }

    private ViolationResDTO setViolation(final Violation violation) {
        final ViolationResDTO violationResDTO = new ViolationResDTO();
        violationResDTO.setViolationId(violation.getViolationId());
        violationResDTO.setViolationNo(violation.getViolationNo());
        violationResDTO.setIssuingAgency(!ObjectUtils.isEmpty(violation.getIssuingAgency()) ? violation.getIssuingAgency().getId() : null);
        violationResDTO.setIssuedDate(violation.getIssuedDate());
        violationResDTO.setHearingDate(violation.getHearingDate());
        violationResDTO.setHearingResult(violation.getHearingResult());
        violationResDTO.setHearingStatus(!ObjectUtils.isEmpty(violation.getHearingStatus()) ? violation.getHearingStatus().getId() : null);
        violationResDTO.setCodeSection(violation.getCodeSection());
        violationResDTO.setCode(violation.getCode());
        violationResDTO.setComplianceDate(violation.getComplianceDate());
        violationResDTO.setDescription(violation.getDescription());
        violationResDTO.setBalanceDue(violation.getBalanceDue());
        violationResDTO.setPaidAmount(violation.getPaidAmount());
        violationResDTO.setPenaltyImposed(violation.getPenaltyImposed());
        violationResDTO.setRespondentName(violation.getRespondentName());
        violationResDTO.setNote(violation.getNote());
        violationResDTO.setHearingAttended(violation.getHearingAttended());
        violationResDTO.setFinePaid(violation.getFinePaid());
        violationResDTO.setWaived(violation.getWaived());
        violationResDTO.setDismissed(violation.getDismissed());
        violationResDTO.setStatusComments(violation.getStatusComments());
        violationResDTO.setCertificateOfCorrection(violation.getCertificateOfCorrection());
        violationResDTO.setBuilding(violation.getBuilding());
        violationResDTO.setCreatedAt(violation.getCreatedAt());
        violationResDTO.setUpdatedAt(violation.getUpdatedAt());
        return violationResDTO;
    }

    public List<AgencyResDTO> getAgencies() {
        return agencyRepository.findAll().stream()
                .map(agency -> new AgencyResDTO(
                        agency.getAgencyId(),
                        agency.getAgencyName(),
                        agency.getAgencyShortName(),
                        agency.getCreatedAt(),
                        agency.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    public BuildingDashboardDTO getViolationCount(final Long buildingId) {
        if (buildingId == null) {
            throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
        }
        final List<Object[]> violations = this.violationRepository.getViolationCount(buildingId);
        Long totalViolationCount = 0L;
        final List<ViolationCountDTO> violationCountDTOS = new LinkedList<>();
        for (Object[] violation : violations) {
            if (violation[1] == null) {
                totalViolationCount = Long.parseLong(violation[0].toString());
            } else {
                ViolationCountDTO violationCountDTO = new ViolationCountDTO();
                violationCountDTO.setViolationCount(Long.parseLong(violation[0].toString()));
                violationCountDTO.setIssuesAgencies(violation[1].toString());
                violationCountDTOS.add(violationCountDTO);
            }
        }
        final BuildingDashboardDTO buildingDashboardDTO = new BuildingDashboardDTO();
        buildingDashboardDTO.setTotalViolationCount(totalViolationCount);
        buildingDashboardDTO.setViolationCountDTO(violationCountDTOS);
        return buildingDashboardDTO;
    }

    public List<CertificateOfCorrectionResDTO> getCertificateOfCorrection() {
        return certificateOfCorrectionRepository
                .findAll()
                .stream()
                .map(certificateOfCorrection -> new CertificateOfCorrectionResDTO(
                        certificateOfCorrection.getCOCId(),
                        certificateOfCorrection.getStatus(),
                        certificateOfCorrection.getCreatedAt(),
                        certificateOfCorrection.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    public List<IssuingAgency> getAllIssuingAgencies() {
        return this.issuingAgencyRepository.findAll();
    }

    public List<HearingStatus> getAllHearingStatusList() {
        return this.hearingStatusRepository.findAll();
    }

    public CommonListResDTO getViolations(final ViolationSearchDTO violationSearchDTO) {
        final List<ViolationResDTO> violationResDTOS = new LinkedList<>();
        final boolean isIssuingAgency = violationSearchDTO.getIssuingAgency() != 0L;
        final boolean isHearingStatus = violationSearchDTO.getHearingStatus() != 0L;
        final Pageable pageable = PaginationUtil.pageableWithoutShorting(violationSearchDTO.getPageNumber(), violationSearchDTO.getItemsPerPage());
        final Page<Object[]> violations = this.violationRepository.getViolation(violationSearchDTO.getFrom(), violationSearchDTO.getTo(), isHearingStatus, isIssuingAgency, violationSearchDTO.getHearingStatus(), violationSearchDTO.getIssuingAgency(), pageable);
        violations.forEach(violation -> {
            final ViolationResDTO violationResDTO = new ViolationResDTO();
            violationResDTO.setViolationId(Long.parseLong(violation[0].toString()));
            violationResDTO.setViolationNo(!ObjectUtils.isEmpty(violation[1]) ? violation[1].toString() : null);
            violationResDTO.setIssuedDate(!ObjectUtils.isEmpty(violation[2]) ? Long.parseLong(violation[2].toString()) : null);
            violationResDTO.setHearingDate(!ObjectUtils.isEmpty(violation[3]) ? Long.parseLong(violation[3].toString()) : null);
            violationResDTO.setPenaltyImposed(!ObjectUtils.isEmpty(violation[4]) ? violation[4].toString() : null);
            violationResDTO.setHearingStatus(!ObjectUtils.isEmpty(violation[5]) ? Long.parseLong(violation[5].toString()) : null);
            violationResDTO.setNote(!ObjectUtils.isEmpty(violation[6]) ? violation[6].toString() : null);
            violationResDTOS.add(violationResDTO);
        });
        return this.commonUtilService.setCommonListResDTO(violations, violationResDTOS, true);
    }
}
