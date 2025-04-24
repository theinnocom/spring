package com.inTrack.spring.service.equipmentService;

import com.inTrack.spring.dto.requestDTO.EmergencyEgressAndLightingReqDTO;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.dto.requestDTO.LandFillReqDTO;
import com.inTrack.spring.dto.responseDTO.BoilerCogenAgencyInfoDTO;
import com.inTrack.spring.dto.responseDTO.LandFillResDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.User;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.EmergencyEgressAndLighting;
import com.inTrack.spring.entity.equipmentEntity.LandFill;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.BoilerCogenAgency;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.InspectionType;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.AgencyRepo.BoilerCogenAgencyInfoRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.BoilerCogenAgencyRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.InspectionTypeRepository;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
import com.inTrack.spring.repository.equipmentRepository.LandFillRepository;
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
public class LandFillService {

    private final LandFillRepository landFillRepository;
    private final ValidatorService validatorService;
    private final BuildingRepository buildingRepository;
    private final CommonUtilService commonUtilService;
    private final JobFilingInformationRepository jobFilingInformationRepository;
    private final CommonEquipmentService commonEquipmentService;
    private final BoilerCogenAgencyInfoRepository boilerCogenAgencyInfoRepository;
    private final BoilerCogenAgencyRepository boilerCogenAgencyRepository;
    private final InspectionTypeRepository inspectionTypeRepository;

    @Transactional(rollbackOn = Exception.class)
    public LandFillResDTO createLandFill(final LandFillReqDTO equipmentReqDTO, final Long landFillId) {
        final LandFill landFill;
        final Building building = this.buildingRepository.findByBuildingId(equipmentReqDTO.getBuildingId());
        if (landFillId != null) {
            landFill = this.landFillRepository.findByLandFillId(landFillId);
        } else {
            landFill = new LandFill();
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            landFill.setBuilding(building);
            Long jobFillingIdCount = 0L;
            if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformationReqDTO())) {
                jobFillingIdCount = this.jobFilingInformationRepository.countByJobNumber(equipmentReqDTO.getJobFilingInformationReqDTO().getJobNumber());
            }
            if (jobFillingIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.JOB_FILLING_ID_EXISTS);
            }
            landFill.setUniqueId(equipmentReqDTO.getUniqueId());
            this.commonUtilService.saveEquipment(ConstantStore.LAND_FILL, building, 1L);
            this.commonEquipmentService.createEquipmentFloorRelation(equipmentReqDTO.getFloor(), ConstantStore.LAND_FILL, building);
        }
        if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformationReqDTO())) {
            if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformationReqDTO().getJobFilingId())) {
                this.commonEquipmentService.saveJobFiling(equipmentReqDTO.getJobFilingInformationReqDTO());
            } else {
                landFill.setJobFilingInformation(this.saveJobFilingInformation(this.setJobFilingInfo(equipmentReqDTO.getJobFilingInformationReqDTO())));
            }
        }
        landFill.setEquipmentStatus(!ObjectUtils.isEmpty(equipmentReqDTO.getStatus()) ? new ElevatorStatus().setStatusId(equipmentReqDTO.getStatus()) : null);
        landFill.setDisconnectedOn(equipmentReqDTO.getDisconnectedOn());
        landFill.setLandmark(equipmentReqDTO.getLandmark());
        landFill.setManagement(!ObjectUtils.isEmpty(equipmentReqDTO.getManagement()) ? new ManagementType().setId(equipmentReqDTO.getManagement()) : null);
        landFill.setManagementNote(equipmentReqDTO.getManagementNote());
        landFill.setFloor(equipmentReqDTO.getFloor());
        landFill.setLocation(equipmentReqDTO.getLocation());
        landFill.setInstalledOn(equipmentReqDTO.getInstalledOn());
        landFill.setMake(equipmentReqDTO.getMake());
        landFill.setInstalledBy(equipmentReqDTO.getInstalledBy());
        landFill.setManagedBy(equipmentReqDTO.getManagedBy());
        landFill.setSerialNo(equipmentReqDTO.getSerialNo());
        landFill.setApplicationId(equipmentReqDTO.getApplicationId());
        landFill.setOperationCommenceDate(equipmentReqDTO.getOperationCommenceDate());
        landFill.setStatusOfSource(equipmentReqDTO.getStatusOfSource());
        landFill.setLinkedToCogenOrTurbines(equipmentReqDTO.isLinkedToCogenOrTurbines());
        landFill.setLinkedEquipmentList(equipmentReqDTO.getLinkedEquipmentList());
        landFill.setCapOnLfgMonthlyProductionQuantity(equipmentReqDTO.isCapOnLfgMonthlyProductionQuantity());
        landFill.setLfgMonthlyProductionQuantityCap(equipmentReqDTO.getLfgMonthlyProductionQuantityCap());
        landFill.setCapOnLfgCombustionByCogenOrTurbineUnit(equipmentReqDTO.isCapOnLfgCombustionByCogenOrTurbineUnit());
        landFill.setLfgCombustionCap(equipmentReqDTO.getLfgCombustionCap());
        landFill.setMonitoringRequired(equipmentReqDTO.isMonitoringRequired());
        landFill.setFivePercentMeasuredAllowableValue(equipmentReqDTO.getFivePercentMeasuredAllowableValue());
        landFill.setCh4PercentMeasuredAllowableValue(equipmentReqDTO.getCh4PercentMeasuredAllowableValue());
        landFill.setFlueGasTemperatureLimit(equipmentReqDTO.isFlueGasTemperatureLimit());
        landFill.setFlueGasTemperatureLimitTestedAllowableValue(equipmentReqDTO.getFlueGasTemperatureLimitTestedAllowableValue());
        landFill.setNoxTestedAllowableEmission(equipmentReqDTO.getNoxTestedAllowableEmission());
        landFill.setCoTestedAllowableEmission(equipmentReqDTO.getCoTestedAllowableEmission());
        landFill.setOthersTestedAllowableEmission(equipmentReqDTO.getOthersTestedAllowableEmission());
        landFill.setLimitOnYearlyOperatingHours(equipmentReqDTO.isLimitOnYearlyOperatingHours());
        landFill.setYearlyOperatingHours(equipmentReqDTO.getYearlyOperatingHours());
        landFill.setComments(equipmentReqDTO.getComments());
        final LandFill saveLandFill = this.landFillRepository.save(landFill);
        this.saveAgency(equipmentReqDTO, saveLandFill);
        return this.setEquipment(saveLandFill, null);
    }

    public List<LandFillResDTO> getLandFill(final Long landFillId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        final List<LandFill> landFills = new LinkedList<>();
        final List<BoilerCogenAgencyInfo> boilerCogenAgencyInfoList = new LinkedList<>();
        if (equipmentDetailsReqVO.getEquipmentType() != null && !equipmentDetailsReqVO.getEquipmentType().equalsIgnoreCase(ConstantStore.LAND_FILL)) {
            throw new ValidationError(ApplicationMessageStore.EQUIPMENT_TYPE_NOT_MATCH);
        }
        if (landFillId != null) {
            final LandFill landFill = this.landFillRepository.findByLandFillId(landFillId);
            landFills.add(landFill);
            final List<BoilerCogenAgencyInfo> boilerCogenAgencyInfos = this.boilerCogenAgencyInfoRepository.findByLandFill(landFill);
            boilerCogenAgencyInfos.forEach(boilerCogenAgencyInfo -> {
                boilerCogenAgencyInfo.setLandFill(null);
                boilerCogenAgencyInfoList.add(boilerCogenAgencyInfo);
            });
        } else {
            final Building building = this.buildingRepository.findByBuildingId(equipmentDetailsReqVO.getBuildingId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            if (equipmentDetailsReqVO.getFloor() == null) {
                landFills.addAll(this.landFillRepository.findAllByBuilding(building));
            } else {
                landFills.addAll(this.landFillRepository.findAllByBuildingAndFloor(building, equipmentDetailsReqVO.getFloor()));
            }
        }
        if (landFills.isEmpty()) {
            throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
        }
        return landFills.stream().map(landFill -> this.setEquipment(landFill, boilerCogenAgencyInfoList)).toList();
    }

    public LandFillResDTO updateLandFill(final Long landFillId, final LandFillReqDTO equipmentReqDTO) {
        return this.createLandFill(equipmentReqDTO, landFillId);
    }

    public void deleteLandFill(final Long landFillId, final boolean activeStatus, final boolean isPermanentDelete) {
        final User user = this.validatorService.validateUser();
        final String userRole = user.getRole().getRole();
        final LandFill landFill;
        if (isPermanentDelete && (userRole.equalsIgnoreCase(ConstantStore.ADMIN_SUPER_USER) || userRole.equalsIgnoreCase(ConstantStore.ADMIN_USER))) {
            landFill = this.landFillRepository.findByLandFillId(landFillId);
            if (landFillId == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            this.landFillRepository.delete(landFill);
            this.commonUtilService.removeEquipment(ConstantStore.LAND_FILL, landFill.getBuilding(), 1L);
        } else {
            landFill = this.landFillRepository.findByLandFillIdAndIsActive(landFillId, true);
            if (landFill == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            landFill.setIsActive(activeStatus);
            this.landFillRepository.save(landFill);
        }
    }

    private JobFilingInformation saveJobFilingInformation(final JobFilingInformation jobFilingInformation) {
        return this.jobFilingInformationRepository.save(jobFilingInformation);
    }

    private LandFillResDTO setEquipment(final LandFill landFill, final List<BoilerCogenAgencyInfo> agencyInfoList) {
        final LandFillResDTO equipmentResDTO = new LandFillResDTO();
        equipmentResDTO.setLandFillId(landFill.getLandFillId());
        equipmentResDTO.setUniqueId(landFill.getUniqueId());
        equipmentResDTO.setStatus(!ObjectUtils.isEmpty(landFill.getEquipmentStatus()) ? landFill.getEquipmentStatus().getStatusId() : null);
        equipmentResDTO.setBuildingId(landFill.getBuilding().getBuildingId());
        equipmentResDTO.setFloor(landFill.getFloor());
        equipmentResDTO.setLocation(landFill.getLocation());
        equipmentResDTO.setInstalledOn(landFill.getInstalledOn());
        equipmentResDTO.setMake(landFill.getMake());
        equipmentResDTO.setInstalledBy(landFill.getInstalledBy());
        equipmentResDTO.setManagedBy(landFill.getManagedBy());
        equipmentResDTO.setSerialNo(landFill.getSerialNo());
        equipmentResDTO.setDeviceType(ConstantStore.LAND_FILL);
        equipmentResDTO.setDisconnectedOn(landFill.getDisconnectedOn());
        equipmentResDTO.setLandmark(landFill.getLandmark());
        equipmentResDTO.setManagement(!ObjectUtils.isEmpty(landFill.getManagement()) ? landFill.getManagement().getId() : null);
        equipmentResDTO.setManagementNote(landFill.getManagementNote());
        equipmentResDTO.setApplicationId(landFill.getApplicationId());
        equipmentResDTO.setOperationCommenceDate(landFill.getOperationCommenceDate());
        equipmentResDTO.setStatusOfSource(landFill.getStatusOfSource());
        equipmentResDTO.setLinkedToCogenOrTurbines(landFill.isLinkedToCogenOrTurbines());
        equipmentResDTO.setLinkedEquipmentList(landFill.getLinkedEquipmentList());
        equipmentResDTO.setCapOnLfgMonthlyProductionQuantity(landFill.isCapOnLfgMonthlyProductionQuantity());
        equipmentResDTO.setLfgMonthlyProductionQuantityCap(landFill.getLfgMonthlyProductionQuantityCap());
        equipmentResDTO.setCapOnLfgCombustionByCogenOrTurbineUnit(landFill.isCapOnLfgCombustionByCogenOrTurbineUnit());
        equipmentResDTO.setLfgCombustionCap(landFill.getLfgCombustionCap());
        equipmentResDTO.setMonitoringRequired(landFill.isMonitoringRequired());
        equipmentResDTO.setFivePercentMeasuredAllowableValue(landFill.getFivePercentMeasuredAllowableValue());
        equipmentResDTO.setCh4PercentMeasuredAllowableValue(landFill.getCh4PercentMeasuredAllowableValue());
        equipmentResDTO.setFlueGasTemperatureLimit(landFill.isFlueGasTemperatureLimit());
        equipmentResDTO.setFlueGasTemperatureLimitTestedAllowableValue(landFill.getFlueGasTemperatureLimitTestedAllowableValue());
        equipmentResDTO.setNoxTestedAllowableEmission(landFill.getNoxTestedAllowableEmission());
        equipmentResDTO.setCoTestedAllowableEmission(landFill.getCoTestedAllowableEmission());
        equipmentResDTO.setOthersTestedAllowableEmission(landFill.getOthersTestedAllowableEmission());
        equipmentResDTO.setLimitOnYearlyOperatingHours(landFill.isLimitOnYearlyOperatingHours());
        equipmentResDTO.setYearlyOperatingHours(landFill.getYearlyOperatingHours());
        equipmentResDTO.setComments(landFill.getComments());
        equipmentResDTO.setJobFilingInformation(!ObjectUtils.isEmpty(landFill.getJobFilingInformation()) ? landFill.getJobFilingInformation() : null);
        equipmentResDTO.setAgencyInfoList(!ObjectUtils.isEmpty(agencyInfoList) ? this.setBoilerCogenAgencyInfoDTOS(agencyInfoList) : null);
        return equipmentResDTO;
    }


    private void saveAgency(final LandFillReqDTO equipmentReqDTO, final LandFill landFill) {
        if (equipmentReqDTO.getAgencyInfoList() != null) {
            final List<BoilerCogenAgencyInfoDTO> agencyInfoList = equipmentReqDTO.getAgencyInfoList();
            final List<BoilerCogenAgencyInfo> boilerCogenAgencyInfos = new LinkedList<>();
            for (BoilerCogenAgencyInfoDTO agencyInfoDTO : agencyInfoList) {
                BoilerCogenAgencyInfo boilerCogenAgencyInfo;
                if (agencyInfoDTO.getId() != null) {
                    boilerCogenAgencyInfo = boilerCogenAgencyInfoRepository.getByAgencyId(agencyInfoDTO.getId());
                } else {
                    boilerCogenAgencyInfo = new BoilerCogenAgencyInfo();
                }
                for (final Field field : BoilerCogenAgencyInfo.class.getDeclaredFields()) {
                    field.setAccessible(true);
                    if ((agencyInfoDTO.getId() == null && field.getName().equalsIgnoreCase("id")) || field.isAnnotationPresent(ManyToOne.class)) {
                        continue;
                    }
                    try {
                        final Object value = this.getFieldValue(agencyInfoDTO, field.getName());
                        if (value != null) {
                            field.set(boilerCogenAgencyInfo, value);
                        }
                    } catch (final NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                        throw new ValidationError("Error setting field: " + field.getName());
                    }
                }
                boilerCogenAgencyInfo.setLandFill(landFill);
                this.setBoilerCogenAgency(agencyInfoDTO, boilerCogenAgencyInfo);
                this.setInspectionType(agencyInfoDTO, boilerCogenAgencyInfo);
                boilerCogenAgencyInfos.add(boilerCogenAgencyInfo);
            }
            this.boilerCogenAgencyInfoRepository.saveAll(boilerCogenAgencyInfos);
        }
    }

    private void setBoilerCogenAgency(final BoilerCogenAgencyInfoDTO agencyInfoDTO, final BoilerCogenAgencyInfo boilerCogenAgencyInfo) {
        final Long boilerCogenAgencyId = agencyInfoDTO.getBoilerCogenAgency();
        if (boilerCogenAgencyId != null) {
            final BoilerCogenAgency boilerCogenAgency = this.boilerCogenAgencyRepository.findById(boilerCogenAgencyId);
            if (boilerCogenAgency == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            boilerCogenAgencyInfo.setBoilerCogenAgency(boilerCogenAgency);
        }
    }

    private void setInspectionType(final BoilerCogenAgencyInfoDTO agencyInfoDTO, final BoilerCogenAgencyInfo boilerCogenAgencyInfo) {
        final Long inspectionTypeId = agencyInfoDTO.getInspectionType();
        if (inspectionTypeId != null) {
            final InspectionType inspectionType = this.inspectionTypeRepository.findById(inspectionTypeId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid InspectionType ID: " + inspectionTypeId));
            boilerCogenAgencyInfo.setInspectionType(inspectionType);
        }
    }

    private Object getFieldValue(final BoilerCogenAgencyInfoDTO agencyInfoDTO, final String fieldName) throws NoSuchFieldException, IllegalAccessException {
        final Field field = BoilerCogenAgencyInfoDTO.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(agencyInfoDTO);
    }

    private List<BoilerCogenAgencyInfoDTO> setBoilerCogenAgencyInfoDTOS(final List<BoilerCogenAgencyInfo> boilerCogenAgencyInfos) {
        final List<BoilerCogenAgencyInfoDTO> boilerCogenAgencyInfoDTOS = new LinkedList<>();
        boilerCogenAgencyInfos.forEach(boilerCogenAgencyInfo -> {
            BoilerCogenAgencyInfoDTO boilerCogenAgencyInfoDTO = BoilerCogenAgencyInfoDTO.builder()
                    .id(boilerCogenAgencyInfo.getId())
                    .agencyNumber(boilerCogenAgencyInfo.getAgencyNumber())
                    .issueDate(boilerCogenAgencyInfo.getIssueDate())
                    .expirationDate(boilerCogenAgencyInfo.getExpirationDate())
                    .status(boilerCogenAgencyInfo.getStatus())
                    .submittedDate(boilerCogenAgencyInfo.getSubmittedDate())
                    .note(boilerCogenAgencyInfo.getNote())
                    .lastInspectionDate(boilerCogenAgencyInfo.getLastInspectionDate())
                    .nextInspectionDate(boilerCogenAgencyInfo.getNextInspectionDate())
                    .inspectedBy(boilerCogenAgencyInfo.getInspectedBy())
                    .fdDate(boilerCogenAgencyInfo.getFdDate())
                    .datePerformed(boilerCogenAgencyInfo.getDatePerformed())
                    .performedBy(boilerCogenAgencyInfo.getPerformedBy())
                    .isRecordKept(boilerCogenAgencyInfo.getIsRecordKept())
                    .issuedDate(boilerCogenAgencyInfo.getIssuedDate())
                    .isPaintSprayBooth(boilerCogenAgencyInfo.getIsPaintSprayBooth())
                    .isInspectionTag(boilerCogenAgencyInfo.getIsInspectionTag())
                    .dobIssueDate(boilerCogenAgencyInfo.getDobIssueDate())
                    .lastTestDate(boilerCogenAgencyInfo.getLastTestDate())
                    .nextTestDate(boilerCogenAgencyInfo.getNextTestDate())
                    .testDoneBy(boilerCogenAgencyInfo.getTestDoneBy())
                    .fdnyCertificateNo(boilerCogenAgencyInfo.getFdnyCertificateNo())
                    .firmInspected(boilerCogenAgencyInfo.getFirmInspected())
                    .inspectionDurationType(!ObjectUtils.isEmpty(boilerCogenAgencyInfo.getInspectionDurationType()) ? boilerCogenAgencyInfo.getInspectionDurationType().getId() : null)
                    .boilerCogenAgency(!ObjectUtils.isEmpty(boilerCogenAgencyInfo.getBoilerCogenAgency()) ? boilerCogenAgencyInfo.getBoilerCogenAgency().getId() : null)
                    .inspectionType(!ObjectUtils.isEmpty(boilerCogenAgencyInfo.getInspectionType()) ? boilerCogenAgencyInfo.getInspectionType().getId() : null)
                    .build();
            boilerCogenAgencyInfoDTOS.add(boilerCogenAgencyInfoDTO);
        });
        return boilerCogenAgencyInfoDTOS;
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
