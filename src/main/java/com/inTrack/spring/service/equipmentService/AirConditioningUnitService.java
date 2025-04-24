package com.inTrack.spring.service.equipmentService;

import com.inTrack.spring.dto.common.ACAgencyDetailDTO;
import com.inTrack.spring.dto.common.GeneratorAgencyInfoDTO;
import com.inTrack.spring.dto.requestDTO.ACUnitDTO;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.GeneratorReqDTO;
import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.*;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.User;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.ACAgencyDetail;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.GeneratorAgencyInfo;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.AgencyRepo.ACAgencyDetailRepository;
import com.inTrack.spring.repository.equipmentRepository.AirConditioningUnitRepository;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
import com.inTrack.spring.UtilService.CommonUtilService;
import com.inTrack.spring.service.ValidatorService;
import com.inTrack.spring.store.ApplicationMessageStore;
import com.inTrack.spring.store.ConstantStore;
import jakarta.persistence.ManyToOne;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/**
 * @author vijayan
 */

@Service
@RequiredArgsConstructor
public class AirConditioningUnitService {

    private final AirConditioningUnitRepository airConditioningUnitRepository;
    private final ValidatorService validatorService;
    private final BuildingRepository buildingRepository;
    private final CommonUtilService commonUtilService;
    private final JobFilingInformationRepository jobFilingInformationRepository;
    private final CommonEquipmentService commonEquipmentService;
    private final ACAgencyDetailRepository acAgencyDetailRepository;

    @Transactional(rollbackOn = Exception.class)
    public ACUnitDTO createAirConditioningUnit(final ACUnitDTO equipmentReqDTO, final Long acUnitId) {
        final AirConditioningUnit airConditioningUnit;
        final Building building = this.buildingRepository.findByBuildingId(equipmentReqDTO.getBuildingId());
        if (acUnitId != null) {
            airConditioningUnit = this.airConditioningUnitRepository.findByAcUnitId(acUnitId);
            airConditioningUnit.getJobFilingInformation().setStatus(equipmentReqDTO.getJobFilingInformation().getStatus());
        } else {
            airConditioningUnit = new AirConditioningUnit();
            airConditioningUnit.setUniqueId(equipmentReqDTO.getUniqueId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            airConditioningUnit.setBuilding(building);
            Long jobFillingIdCount = 0L;
            if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformation())) {
                jobFillingIdCount = this.jobFilingInformationRepository.countByJobNumber(equipmentReqDTO.getJobFilingInformation().getJobNumber());
            }
            if (jobFillingIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.JOB_FILLING_ID_EXISTS);
            }
            this.commonUtilService.saveEquipment(ConstantStore.AIR_CONDITIONING_UNIT, building, 1L);
            this.commonEquipmentService.createEquipmentFloorRelation(equipmentReqDTO.getFloor(), ConstantStore.AIR_CONDITIONING_UNIT, building);
        }
        if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformation())) {
            if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformation().getJobFilingId())) {
                this.commonEquipmentService.saveJobFiling(equipmentReqDTO.getJobFilingInformation());
            } else {
                airConditioningUnit.setJobFilingInformation(this.jobFilingInformationRepository.save(equipmentReqDTO.getJobFilingInformation()));
            }
        }
        airConditioningUnit.setDeviceType(ConstantStore.AIR_CONDITIONING_UNIT);
        airConditioningUnit.setManagement(!ObjectUtils.isEmpty(equipmentReqDTO.getManagement()) ? new ManagementType().setId(equipmentReqDTO.getManagement()) : null);
        airConditioningUnit.setManagementNote(equipmentReqDTO.getManagementNote());
        airConditioningUnit.setLandmark(equipmentReqDTO.getLandmark());
        airConditioningUnit.setDisconnectedOn(equipmentReqDTO.getDisconnectedOn());
        airConditioningUnit.setModel(equipmentReqDTO.getModel());
        airConditioningUnit.setInspector(equipmentReqDTO.getInspector());
        airConditioningUnit.setInspectionDate(equipmentReqDTO.getInspectionDate());
        airConditioningUnit.setFDNYInspectionDate(equipmentReqDTO.getFdnyInspectionDate());
        airConditioningUnit.setEUPCardAvailable(equipmentReqDTO.getEupcardAvailable());
        airConditioningUnit.setCapacity(equipmentReqDTO.getCapacity());
        airConditioningUnit.setChillerGroup(!ObjectUtils.isEmpty(equipmentReqDTO.getChillerGroup()) ? new ChillerGroup().setChillerGroupId(equipmentReqDTO.getChillerGroup()) : null);
        airConditioningUnit.setTypeOfChillerOthersList(equipmentReqDTO.getTypeOfChillerOthersList());
        airConditioningUnit.setTypeOfChiller(!ObjectUtils.isEmpty(equipmentReqDTO.getTypeOfChiller()) ? new TypeOfChiller().setChillerTypeId(equipmentReqDTO.getTypeOfChiller()) : null);
        airConditioningUnit.setEPARefrigerationRecoveryInvolved(equipmentReqDTO.isEpaRefrigerationRecoveryInvolved());
        airConditioningUnit.setEPARecordMaintained(equipmentReqDTO.isEpaRecordMaintained());
        airConditioningUnit.setRefrigerationRecoveryInvolved(equipmentReqDTO.isRefrigerationRecoveryInvolved());
        airConditioningUnit.setEPAApprovalDate(equipmentReqDTO.getEpaApprovalDate());
        airConditioningUnit.setEPASubmittedDate(equipmentReqDTO.getEpaSubmittedDate());
        airConditioningUnit.setFloor(equipmentReqDTO.getFloor());
        airConditioningUnit.setLocation(equipmentReqDTO.getLocation());
        airConditioningUnit.setInstalledOn(equipmentReqDTO.getInstalledOn());
        airConditioningUnit.setMakeBy(equipmentReqDTO.getMakeBy());
        airConditioningUnit.setInstalledBy(equipmentReqDTO.getInstalledBy());
        airConditioningUnit.setManagedBy(equipmentReqDTO.getManagedBy());
        airConditioningUnit.setSerialNo(equipmentReqDTO.getSerialNo());
        airConditioningUnit.setStatus(!ObjectUtils.isEmpty(equipmentReqDTO.getStatus()) ? new ElevatorStatus().setStatusId(equipmentReqDTO.getStatus()) : null);
        final AirConditioningUnit conditioningUnit = this.airConditioningUnitRepository.save(airConditioningUnit);
        this.saveAgency(equipmentReqDTO, conditioningUnit);
        return this.setEquipment(conditioningUnit, null);
    }

    public List<ACUnitDTO> getAirConditioningUnit(final Long acUnitId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        final List<AirConditioningUnit> equipmentList = new LinkedList<>();
        final List<ACAgencyDetail> acAgencyDetails = new LinkedList<>();
        final List<ACUnitDTO> acUnitDTOS = new LinkedList<>();
        if (equipmentDetailsReqVO.getEquipmentType() != null && !equipmentDetailsReqVO.getEquipmentType().equalsIgnoreCase(ConstantStore.AIR_CONDITIONING_UNIT)) {
            throw new ValidationError(ApplicationMessageStore.EQUIPMENT_TYPE_NOT_MATCH);
        }
        if (acUnitId != null) {
            final AirConditioningUnit airConditioningUnit = this.airConditioningUnitRepository.findByAcUnitId(acUnitId);
            equipmentList.add(airConditioningUnit);
            final List<ACAgencyDetail> agencyDetails = this.acAgencyDetailRepository.findByAirConditioningUnit(airConditioningUnit);
            agencyDetails.forEach(acAgencyDetail -> {
                acAgencyDetail.setAirConditioningUnit(null);
                acAgencyDetails.add(acAgencyDetail);
            });
        } else {
            final Building building = this.buildingRepository.findByBuildingId(equipmentDetailsReqVO.getBuildingId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            if (equipmentDetailsReqVO.getFloor() == null) {
                equipmentList.addAll(this.airConditioningUnitRepository.findAllByBuilding(building));
            } else {
                equipmentList.addAll(this.airConditioningUnitRepository.findAllByBuildingAndFloor(building, equipmentDetailsReqVO.getFloor()));
            }
        }
        equipmentList.forEach(airConditioningUnit -> acUnitDTOS.add(this.setEquipment(airConditioningUnit, acAgencyDetails)));
        return acUnitDTOS;
    }

    public ACUnitDTO updateAirConditioningUnit(final Long acUnitId, final ACUnitDTO equipmentReqDTO) {
        return this.createAirConditioningUnit(equipmentReqDTO, acUnitId);
    }

    public void deleteAirConditioningUnit(final Long acUnitId, final boolean activeStatus, final boolean isPermanentDelete) {
        final User user = this.validatorService.validateUser();
        final String userRole = user.getRole().getRole();
        final AirConditioningUnit airConditioningUnit;
        if (isPermanentDelete && (userRole.equalsIgnoreCase(ConstantStore.ADMIN_SUPER_USER) || userRole.equalsIgnoreCase(ConstantStore.ADMIN_USER))) {
            airConditioningUnit = this.airConditioningUnitRepository.findByAcUnitId(acUnitId);
            if (airConditioningUnit == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            this.airConditioningUnitRepository.delete(airConditioningUnit);
            this.commonUtilService.removeEquipment(ConstantStore.AIR_CONDITIONING_UNIT, airConditioningUnit.getBuilding(), 1L);
        } else {
            airConditioningUnit = this.airConditioningUnitRepository.findByAcUnitIdAndIsActive(acUnitId, true);
            if (airConditioningUnit == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            airConditioningUnit.setIsActive(activeStatus);
            this.airConditioningUnitRepository.save(airConditioningUnit);
        }
    }

    private JobFilingInformation saveJobFilingInformation(final JobFilingInformation jobFilingInformation) {
        return this.jobFilingInformationRepository.save(jobFilingInformation);
    }

    private ACUnitDTO setEquipment(final AirConditioningUnit airConditioningUnit, final List<ACAgencyDetail> acAgencyDetails) {
        final ACUnitDTO equipmentResDTO = new ACUnitDTO();
        equipmentResDTO.setId(airConditioningUnit.getAcUnitId());
        equipmentResDTO.setUniqueId(airConditioningUnit.getUniqueId());
        equipmentResDTO.setFloor(airConditioningUnit.getFloor());
        equipmentResDTO.setLocation(airConditioningUnit.getLocation());
        equipmentResDTO.setInstalledOn(airConditioningUnit.getInstalledOn());
        equipmentResDTO.setMakeBy(airConditioningUnit.getMakeBy());
        equipmentResDTO.setInstalledBy(airConditioningUnit.getInstalledBy());
        equipmentResDTO.setManagedBy(airConditioningUnit.getManagedBy());
        equipmentResDTO.setSerialNo(airConditioningUnit.getSerialNo());
        equipmentResDTO.setManagement(!ObjectUtils.isEmpty(airConditioningUnit.getManagement()) ? airConditioningUnit.getManagement().getId() : null);
        equipmentResDTO.setManagementNote(airConditioningUnit.getManagementNote());
        equipmentResDTO.setLandmark(airConditioningUnit.getLandmark());
        equipmentResDTO.setDisconnectedOn(airConditioningUnit.getDisconnectedOn());
        equipmentResDTO.setModel(airConditioningUnit.getModel());
        equipmentResDTO.setInspector(airConditioningUnit.getInspector());
        equipmentResDTO.setInspectionDate(airConditioningUnit.getInspectionDate());
        equipmentResDTO.setCapacity(airConditioningUnit.getCapacity());
        equipmentResDTO.setChillerGroup(!ObjectUtils.isEmpty(airConditioningUnit.getChillerGroup()) ? airConditioningUnit.getChillerGroup().getChillerGroupId() : null);
        equipmentResDTO.setTypeOfChillerOthersList(airConditioningUnit.getTypeOfChillerOthersList());
        equipmentResDTO.setTypeOfChiller(!ObjectUtils.isEmpty(airConditioningUnit.getTypeOfChiller()) ? airConditioningUnit.getTypeOfChiller().getChillerTypeId() : null);
        equipmentResDTO.setRefrigerationRecoveryInvolved(airConditioningUnit.getRefrigerationRecoveryInvolved());
        equipmentResDTO.setFloor(airConditioningUnit.getFloor());
        equipmentResDTO.setLocation(airConditioningUnit.getLocation());
        equipmentResDTO.setInstalledOn(airConditioningUnit.getInstalledOn());
        equipmentResDTO.setMakeBy(airConditioningUnit.getMakeBy());
        equipmentResDTO.setInstalledBy(airConditioningUnit.getInstalledBy());
        equipmentResDTO.setManagedBy(airConditioningUnit.getManagedBy());
        equipmentResDTO.setSerialNo(airConditioningUnit.getSerialNo());
        equipmentResDTO.setJobFilingInformation(!ObjectUtils.isEmpty(airConditioningUnit.getJobFilingInformation()) ? airConditioningUnit.getJobFilingInformation() : null);
        equipmentResDTO.setAcAgencyDetails(!ObjectUtils.isEmpty(acAgencyDetails) ? this.setAcAgencyDetailDTO(acAgencyDetails) : null);
        equipmentResDTO.setStatus(!ObjectUtils.isEmpty(airConditioningUnit.getStatus()) ? airConditioningUnit.getStatus().getStatusId() : null);
        return equipmentResDTO;
    }

    private void saveAgency(final ACUnitDTO equipmentReqDTO, final AirConditioningUnit airConditioningUnit) {
        if (equipmentReqDTO.getAcAgencyDetails() != null) {
            final List<ACAgencyDetailDTO> agencyInfoList = equipmentReqDTO.getAcAgencyDetails();
            final List<ACAgencyDetail> agencyDetails = new LinkedList<>();
            for (ACAgencyDetailDTO agencyInfoDTO : agencyInfoList) {
                ACAgencyDetail agencyDetail;
                if (agencyInfoDTO.getId() != null) {
                    agencyDetail = acAgencyDetailRepository.findById(agencyInfoDTO.getId())
                            .orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
                } else {
                    agencyDetail = new ACAgencyDetail();
                }
                for (final Field field : ACAgencyDetail.class.getDeclaredFields()) {
                    field.setAccessible(true);
                    if ((agencyInfoDTO.getId() == null && field.getName().equalsIgnoreCase("id")) || field.isAnnotationPresent(ManyToOne.class)) {
                        continue;
                    }
                    try {
                        final Object value = this.getFieldValue(agencyInfoDTO, field.getName());
                        if (value != null) {
                            field.set(agencyDetail, value);
                        }
                    } catch (final NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                        throw new ValidationError("Error setting field: " + field.getName());
                    }
                }
                agencyDetail.setAirConditioningUnit(airConditioningUnit);
                agencyDetails.add(agencyDetail);
            }
            this.acAgencyDetailRepository.saveAll(agencyDetails);
        }
    }

    private Object getFieldValue(final ACAgencyDetailDTO agencyInfoDTO, final String fieldName) throws NoSuchFieldException, IllegalAccessException {
        final Field field = ACAgencyDetailDTO.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(agencyInfoDTO);
    }

    private List<ACAgencyDetailDTO> setAcAgencyDetailDTO(final List<ACAgencyDetail> acAgencyDetails) {
        final List<ACAgencyDetailDTO> acAgencyDetailDTOS = new LinkedList<>();
        acAgencyDetails.forEach(acAgencyDetail -> {
            final ACAgencyDetailDTO acAgencyDetailDTO = new ACAgencyDetailDTO();
            acAgencyDetailDTO.setId(acAgencyDetail.getId());
            acAgencyDetailDTO.setAgencyId(acAgencyDetail.getAgencyId());
            acAgencyDetailDTO.setFdnyInspectionDate(acAgencyDetail.getFdnyInspectionDate());
            acAgencyDetailDTO.setFdnyInspectedBy(acAgencyDetail.getFdnyInspectedBy());
            acAgencyDetailDTO.setFdnyNote(acAgencyDetail.getFdnyNote());
            acAgencyDetailDTO.setEupCardAvailable(acAgencyDetail.getEupCardAvailable());
            acAgencyDetailDTO.setEupInspectionDate(acAgencyDetail.getEupInspectionDate());
            acAgencyDetailDTO.setEupNextInspectionDate(acAgencyDetail.getEupNextInspectionDate());
            acAgencyDetailDTO.setEupNote(acAgencyDetail.getEupNote());
            acAgencyDetailDTO.setEpaSubmittedDate(acAgencyDetail.getEpaSubmittedDate());
            acAgencyDetailDTO.setIsEPARefrigerationRecoveryRegistered(acAgencyDetail.getIsEPARefrigerationRecoveryRegistered());
            acAgencyDetailDTO.setEpaApprovalDate(acAgencyDetail.getEpaApprovalDate());
            acAgencyDetailDTO.setEpaNote(acAgencyDetail.getEpaNote());
            acAgencyDetailDTOS.add(acAgencyDetailDTO);
        });
        return acAgencyDetailDTOS;
    }

    private JobFilingInformationReqDTO setJobFilingInfo(final JobFilingInformation jobFilingInformation) {
        final JobFilingInformationReqDTO jobFilingInformationReqDTO = new JobFilingInformationReqDTO();
        jobFilingInformationReqDTO.setJobNumber(jobFilingInformation.getJobNumber());
        jobFilingInformationReqDTO.setStatus(jobFilingInformation.getStatus());
        jobFilingInformationReqDTO.setType(jobFilingInformation.getType());
        jobFilingInformationReqDTO.setDescription(jobFilingInformation.getDescription());
        jobFilingInformationReqDTO.setCommands(jobFilingInformation.getCommands());
        jobFilingInformationReqDTO.setApprovalDate(jobFilingInformation.getApprovalDate());
        jobFilingInformationReqDTO.setApprovalDate(jobFilingInformation.getApprovalDate());
        jobFilingInformationReqDTO.setSignOffDate(jobFilingInformation.getSignOffDate());
        jobFilingInformationReqDTO.setType(jobFilingInformation.getType());
        jobFilingInformationReqDTO.setEquipmentName(jobFilingInformation.getEquipmentName());
        return jobFilingInformationReqDTO;
    }
}
