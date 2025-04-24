package com.inTrack.spring.service.equipmentService;

import com.inTrack.spring.dto.requestDTO.ETOReqDTO;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.dto.responseDTO.ETOResDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.User;
import com.inTrack.spring.entity.equipmentEntity.ETO;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.GasMixtureType;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.ETOAgencyInfo;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.AgencyRepo.ETOAgencyInfoRepository;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
import com.inTrack.spring.repository.equipmentRepository.ETORepository;
import com.inTrack.spring.UtilService.CommonUtilService;
import com.inTrack.spring.service.ValidatorService;
import com.inTrack.spring.store.ApplicationMessageStore;
import com.inTrack.spring.store.ConstantStore;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author vijayan
 */

@Service
public class ETOService {

    @Autowired
    private ETORepository etoRepository;

    @Autowired
    private ValidatorService validatorService;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private CommonUtilService commonUtilService;

    @Autowired
    private JobFilingInformationRepository jobFilingInformationRepository;

    @Autowired
    private CommonEquipmentService commonEquipmentService;

    @Autowired
    private ETOAgencyInfoRepository etoAgencyInfoRepository;

    @Transactional(rollbackOn = Exception.class)
    public ETOResDTO createETOEquipment(final ETOReqDTO equipmentReqDTO, final Long etoId) {
        final ETO eto;
        final Building building = this.buildingRepository.findByBuildingId(equipmentReqDTO.getBuildingId());
        if (etoId != null) {
            eto = this.etoRepository.findByETOId(etoId);
        } else {
            final Long uniqueIdCount = this.etoRepository.countByUniqueId(equipmentReqDTO.getUniqueId());
            Long jobFillingIdCount = 0L;
            if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformationReqDTO())) {
                jobFillingIdCount = this.jobFilingInformationRepository.countByJobNumber(equipmentReqDTO.getJobFilingInformationReqDTO().getJobNumber());
            }
            if (uniqueIdCount > 0 && jobFillingIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.UNIQUE_ID_AND_JOB_FILLING_ID_EXISTS);
            } else if (uniqueIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.UNIQUE_ID_EXISTS);
            } else if (jobFillingIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.JOB_FILLING_ID_EXISTS);
            }
            eto = new ETO();
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            eto.setBuilding(building);
            eto.setUniqueId(equipmentReqDTO.getUniqueId());
            this.commonUtilService.saveEquipment(ConstantStore.ETO, building, 1L);
            this.commonEquipmentService.createEquipmentFloorRelation(equipmentReqDTO.getFloor(), ConstantStore.ETO, building);
            eto.setDeviceType(ConstantStore.ETO);
        }
        if (equipmentReqDTO.getJobFilingInformationReqDTO() != null) {
            if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformationReqDTO().getJobFilingId())) {
                this.commonEquipmentService.saveJobFiling(equipmentReqDTO.getJobFilingInformationReqDTO());
            } else {
                eto.setJobFilingInformation(this.saveJobFilingInformation(this.setJobFilingInfo(equipmentReqDTO.getJobFilingInformationReqDTO())));
            }
        }
        eto.setManagement(!ObjectUtils.isEmpty(equipmentReqDTO.getManagement()) ? new ManagementType().setId(equipmentReqDTO.getManagement()) : null);
        eto.setFloor(equipmentReqDTO.getFloor());
        eto.setLocation(equipmentReqDTO.getLocation());
        eto.setInstalledOn(equipmentReqDTO.getInstalledOn());
        eto.setMake(equipmentReqDTO.getMake());
        eto.setInstalledBy(equipmentReqDTO.getInstalledBy());
        eto.setManagedBy(equipmentReqDTO.getManagedBy());
        eto.setSerialNo(equipmentReqDTO.getSerialNo());
        eto.setApplicationId(equipmentReqDTO.getApplicationId());
        eto.setStackExhausting(equipmentReqDTO.getStackExhausting());
        eto.setMake(equipmentReqDTO.getMake());
        eto.setModel(equipmentReqDTO.getModel());
        eto.setSerialNumber(equipmentReqDTO.getSerialNumber());
        eto.setVolumeCubicFt(equipmentReqDTO.getVolumeCubicFt());
        eto.setGasMixtureType(!ObjectUtils.isEmpty(equipmentReqDTO.getGasMixtureType()) ? new GasMixtureType().setId(equipmentReqDTO.getGasMixtureType()) : null);
        eto.setWeightOfContainer(equipmentReqDTO.getWeightOfContainer());
        eto.setInstallationDate(equipmentReqDTO.getInstallationDate());
        eto.setAverageUseHoursPerDay(equipmentReqDTO.getAverageUseHoursPerDay());
        eto.setAverageUseDaysPerWeek(equipmentReqDTO.getAverageUseDaysPerWeek());
        eto.setIsAbator(equipmentReqDTO.getIsAbator());
        eto.setAbatorMake(equipmentReqDTO.getAbatorMake());
        eto.setAbatorModel(equipmentReqDTO.getAbatorModel());
        eto.setDailyRecordsAvailable(equipmentReqDTO.isDailyRecordsAvailable());
        eto.setComments(equipmentReqDTO.getComments());
        eto.setStatus(!ObjectUtils.isEmpty(equipmentReqDTO.getStatus()) ? new ElevatorStatus().setStatusId(equipmentReqDTO.getStatus()) : null);
        final ETO saveEto = this.etoRepository.save(eto);
        if (!ObjectUtils.isEmpty(equipmentReqDTO.getEtoAgencyInfos())) {
            final List<ETOAgencyInfo> etoAgencyInfos = new LinkedList<>();
            equipmentReqDTO.getEtoAgencyInfos().forEach(etoAgencyInfo -> {
                if (!ObjectUtils.isEmpty(etoAgencyInfo.getId())) {
                    final ETOAgencyInfo agencyInfo = this.etoAgencyInfoRepository.findById(etoAgencyInfo.getId())
                            .orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
                    agencyInfo.setStatus(etoAgencyInfo.getStatus());
                    agencyInfo.setNote(etoAgencyInfo.getNote());
                    agencyInfo.setExpirationDate(etoAgencyInfo.getExpirationDate());
                    agencyInfo.setIssueDate(etoAgencyInfo.getIssueDate());
                    agencyInfo.setDecPermitObtained(etoAgencyInfo.getDecPermitObtained());
                    agencyInfo.setStackTestDate(etoAgencyInfo.getStackTestDate());
                    agencyInfo.setDepNumber(etoAgencyInfo.getDepNumber());
                    agencyInfo.setNextStackTestDate(etoAgencyInfo.getNextStackTestDate());
                    agencyInfo.setStackTestProtocolSubmitted(etoAgencyInfo.getStackTestProtocolSubmitted());
                    agencyInfo.setTestConductedBy(etoAgencyInfo.getTestConductedBy());
                    agencyInfo.setStackTestRequired(etoAgencyInfo.getStackTestRequired());
                    etoAgencyInfos.add(agencyInfo);
                } else {
                    etoAgencyInfo.setEto(saveEto);
                    etoAgencyInfos.add(etoAgencyInfo);
                }
            });
            this.etoAgencyInfoRepository.saveAll(etoAgencyInfos);
        }
        return this.setEquipment(saveEto, null);
    }

    public List<ETOResDTO> getETOEquipment(final Long etoId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        final List<ETO> etoList = new LinkedList<>();
        List<ETOAgencyInfo> etoAgencyInfos = new LinkedList<>();
        if (equipmentDetailsReqVO.getEquipmentType() != null && !equipmentDetailsReqVO.getEquipmentType().equalsIgnoreCase(ConstantStore.ETO)) {
            throw new ValidationError(ApplicationMessageStore.EQUIPMENT_TYPE_NOT_MATCH);
        }
        if (etoId != null) {
            final ETO eto = this.etoRepository.findByETOId(etoId);
            final ETOAgencyInfo etoAgencyInfo = this.etoAgencyInfoRepository.findByEto(eto);
            if (etoAgencyInfo != null) {
                etoAgencyInfo.setEto(null);
                etoAgencyInfos.add(etoAgencyInfo);
            }
            etoList.add(eto);
        } else {
            final Building building = this.buildingRepository.findByBuildingId(equipmentDetailsReqVO.getBuildingId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            if (equipmentDetailsReqVO.getFloor() == null) {
                etoList.addAll(this.etoRepository.findAllByBuilding(building));
            } else {
                etoList.addAll(this.etoRepository.findAllByBuildingAndFloor(building, equipmentDetailsReqVO.getFloor()));
            }
        }
        if (etoList.isEmpty()) {
            throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
        }
        return etoList.stream().map(eto -> this.setEquipment(eto, etoAgencyInfos)).toList();
    }

    public ETOResDTO updateETOEquipment(final Long etoId, final ETOReqDTO equipmentReqDTO) {
        return this.createETOEquipment(equipmentReqDTO, etoId);
    }

    public void deleteETOEquipment(final Long etoId, final boolean activeStatus, final boolean isPermanentDelete) {
        final User user = this.validatorService.validateUser();
        final String userRole = user.getRole().getRole();
        final ETO eto;
        if (isPermanentDelete && (userRole.equalsIgnoreCase(ConstantStore.ADMIN_SUPER_USER) || userRole.equalsIgnoreCase(ConstantStore.ADMIN_USER))) {
            eto = this.etoRepository.findByETOId(etoId);
            if (etoId == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            this.etoRepository.delete(eto);
            this.commonUtilService.removeEquipment(ConstantStore.ETO, eto.getBuilding(), 1L);
        } else {
            eto = this.etoRepository.findByETOIdAndIsActive(etoId, true);
            if (eto == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            eto.setIsActive(activeStatus);
            this.etoRepository.save(eto);
        }
    }

    private JobFilingInformation saveJobFilingInformation(final JobFilingInformation jobFilingInformation) {
        return this.jobFilingInformationRepository.save(jobFilingInformation);
    }

    private ETOResDTO setEquipment(final ETO eto, final List<ETOAgencyInfo> etoAgencyInfos) {
        final ETOResDTO equipmentResDTO = new ETOResDTO();
        equipmentResDTO.setEtoId(eto.getETOId());
        equipmentResDTO.setUniqueId(eto.getUniqueId());
        equipmentResDTO.setFloor(eto.getFloor());
        equipmentResDTO.setLocation(eto.getLocation());
        equipmentResDTO.setInstalledOn(eto.getInstalledOn());
        equipmentResDTO.setMake(eto.getMake());
        equipmentResDTO.setInstalledBy(eto.getInstalledBy());
        equipmentResDTO.setManagedBy(eto.getManagedBy());
        equipmentResDTO.setSerialNo(eto.getSerialNo());
        equipmentResDTO.setDeviceType(eto.getDeviceType());
        equipmentResDTO.setStackExhausting(eto.getStackExhausting());
        equipmentResDTO.setMake(eto.getMake());
        equipmentResDTO.setApplicationId(eto.getApplicationId());
        equipmentResDTO.setModel(eto.getModel());
        equipmentResDTO.setSerialNumber(eto.getSerialNumber());
        equipmentResDTO.setVolumeCubicFt(eto.getVolumeCubicFt());
        equipmentResDTO.setGasMixtureType(!ObjectUtils.isEmpty(eto.getGasMixtureType()) ? eto.getGasMixtureType().getId() : null);
        equipmentResDTO.setWeightOfContainer(eto.getWeightOfContainer());
        equipmentResDTO.setInstallationDate(eto.getInstallationDate());
        equipmentResDTO.setAverageUseHoursPerDay(eto.getAverageUseHoursPerDay());
        equipmentResDTO.setAverageUseDaysPerWeek(eto.getAverageUseDaysPerWeek());
        equipmentResDTO.setIsAbator(eto.getIsAbator());
        equipmentResDTO.setAbatorMake(eto.getAbatorMake());
        equipmentResDTO.setAbatorModel(eto.getAbatorModel());
        equipmentResDTO.setDailyRecordsAvailable(eto.isDailyRecordsAvailable());
        equipmentResDTO.setComments(eto.getComments());
        equipmentResDTO.setManagement(!ObjectUtils.isEmpty(eto.getManagement()) ? eto.getManagement().getId() : null);
        equipmentResDTO.setJobFilingInformation(!ObjectUtils.isEmpty(eto.getJobFilingInformation()) ? eto.getJobFilingInformation() : null);
        equipmentResDTO.setEtoAgencyInfo(!ObjectUtils.isEmpty(etoAgencyInfos) ? etoAgencyInfos : null);
        equipmentResDTO.setStatus(!ObjectUtils.isEmpty(eto.getStatus()) ? eto.getStatus().getStatusId() : null);
        return equipmentResDTO;
    }

    private JobFilingInformation setJobFilingInfo(final JobFilingInformationReqDTO jobFilingInformationReqDTO) {
        final JobFilingInformation jobFilingInformation = new JobFilingInformation();
        jobFilingInformation.setJobNumber(jobFilingInformationReqDTO.getJobNumber());
        jobFilingInformation.setStatus(jobFilingInformationReqDTO.getStatus());
        jobFilingInformation.setType(jobFilingInformationReqDTO.getType());
        jobFilingInformation.setDescription(jobFilingInformationReqDTO.getDescription());
        jobFilingInformation.setCommands(jobFilingInformationReqDTO.getCommands());
        jobFilingInformation.setApprovalDate(jobFilingInformationReqDTO.getApprovalDate());
        jobFilingInformation.setApprovalDate(jobFilingInformationReqDTO.getApprovalDate());
        jobFilingInformation.setSignOffDate(jobFilingInformationReqDTO.getSignOffDate());
        jobFilingInformation.setType(jobFilingInformationReqDTO.getType());
        jobFilingInformation.setEquipmentName(ConstantStore.KITCHEN_HOOD_FIR_SUPPRESSION);
        return jobFilingInformation;
    }

}
