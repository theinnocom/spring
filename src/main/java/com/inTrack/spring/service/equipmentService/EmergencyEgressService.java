package com.inTrack.spring.service.equipmentService;

import com.inTrack.spring.dto.requestDTO.EmergencyEgressAndLightingReqDTO;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.dto.requestDTO.KitchenHoodFireSuppressionDTO;
import com.inTrack.spring.dto.responseDTO.BoilerCogenAgencyInfoDTO;
import com.inTrack.spring.dto.responseDTO.EmergencyEgressAndLightingResDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.User;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.EmergencyEgressAndLighting;
import com.inTrack.spring.entity.equipmentEntity.KitchenHoodFireSuppression;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.BoilerCogenAgency;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.InspectionType;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.AgencyRepo.BoilerCogenAgencyInfoRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.BoilerCogenAgencyRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.InspectionTypeRepository;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
import com.inTrack.spring.repository.equipmentRepository.EmergencyEgressRepository;
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
public class EmergencyEgressService {

    private final EmergencyEgressRepository emergencyEgressRepository;
    private final ValidatorService validatorService;
    private final BuildingRepository buildingRepository;
    private final CommonUtilService commonUtilService;
    private final JobFilingInformationRepository jobFilingInformationRepository;
    private final BoilerCogenAgencyInfoRepository boilerCogenAgencyInfoRepository;
    private final CommonEquipmentService commonEquipmentService;
    private final BoilerCogenAgencyRepository boilerCogenAgencyRepository;
    private final InspectionTypeRepository inspectionTypeRepository;

    @Transactional(rollbackOn = Exception.class)
    public EmergencyEgressAndLightingResDTO createEmergencyEgress(final EmergencyEgressAndLightingReqDTO equipmentReqDTO, final Long landFillId) {
        final EmergencyEgressAndLighting emergencyEgressAndLighting;
        final Building building = this.buildingRepository.findByBuildingId(equipmentReqDTO.getBuildingId());
        if (landFillId != null) {
            emergencyEgressAndLighting = this.emergencyEgressRepository.findByEmergencyEgressId(landFillId);
        } else {
            final Long uniqueIdCount = this.emergencyEgressRepository.countByUniqueId(equipmentReqDTO.getUniqueId());
            Long jobFillingIdCount = 0L;
            if (equipmentReqDTO.getJobFilingInformationReqDTO() != null) {
                jobFillingIdCount = this.jobFilingInformationRepository.countByJobNumber(equipmentReqDTO.getJobFilingInformationReqDTO().getJobNumber());
            }
            if (uniqueIdCount > 0 && jobFillingIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.UNIQUE_ID_AND_JOB_FILLING_ID_EXISTS);
            } else if (uniqueIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.UNIQUE_ID_EXISTS);
            } else if (jobFillingIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.JOB_FILLING_ID_EXISTS);
            }
            emergencyEgressAndLighting = new EmergencyEgressAndLighting();
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            emergencyEgressAndLighting.setBuilding(building);
            emergencyEgressAndLighting.setUniqueId(equipmentReqDTO.getUniqueId());
            emergencyEgressAndLighting.setDeviceType(ConstantStore.EMERGENCY_EGRESS_AND_LIGHTING);
            if (equipmentReqDTO.getJobFilingInformationReqDTO() != null) {
                emergencyEgressAndLighting.setJobFilingInformation(this.saveJobFilingInformation(this.setJobFilingInfo(equipmentReqDTO.getJobFilingInformationReqDTO())));
            }
            this.commonUtilService.saveEquipment(ConstantStore.EMERGENCY_EGRESS_AND_LIGHTING, building, 1L);
            this.commonEquipmentService.createEquipmentFloorRelation(equipmentReqDTO.getFloor(), ConstantStore.EMERGENCY_EGRESS_AND_LIGHTING, building);
        }
        emergencyEgressAndLighting.setFloor(equipmentReqDTO.getFloor());
        emergencyEgressAndLighting.setLocation(equipmentReqDTO.getLocation());
        emergencyEgressAndLighting.setInstalledOn(equipmentReqDTO.getInstalledOn());
        emergencyEgressAndLighting.setMake(equipmentReqDTO.getMake());
        emergencyEgressAndLighting.setInstalledBy(equipmentReqDTO.getInstalledBy());
        emergencyEgressAndLighting.setManagedBy(equipmentReqDTO.getManagedBy());
        emergencyEgressAndLighting.setSerialNo(equipmentReqDTO.getSerialNo());
        emergencyEgressAndLighting.setDisconnectedOn(equipmentReqDTO.getDisconnectedOn());
        emergencyEgressAndLighting.setLandmark(equipmentReqDTO.getLandmark());
        emergencyEgressAndLighting.setModel(equipmentReqDTO.getModel());
        emergencyEgressAndLighting.setManagement(!ObjectUtils.isEmpty(equipmentReqDTO.getManagement()) ? new ManagementType().setId(equipmentReqDTO.getManagement()) : null);
        emergencyEgressAndLighting.setPermitNumber(equipmentReqDTO.getPermitNumber());
        emergencyEgressAndLighting.setNumberOfSignBoards(equipmentReqDTO.getNumberOfSignBoards());
        emergencyEgressAndLighting.setManagementNote(equipmentReqDTO.getManagementNote());
        emergencyEgressAndLighting.setManagedBy(equipmentReqDTO.getManagedBy());
        emergencyEgressAndLighting.setPermitDate(equipmentReqDTO.getPermitDate());
        emergencyEgressAndLighting.setStatus(!ObjectUtils.isEmpty(equipmentReqDTO.getStatus()) ? new ElevatorStatus().setStatusId(equipmentReqDTO.getStatus()) : null);
        final EmergencyEgressAndLighting egressAndLighting = this.emergencyEgressRepository.save(emergencyEgressAndLighting);
        this.saveAgency(equipmentReqDTO, egressAndLighting);
        return this.setEquipment(egressAndLighting, null);
    }

    public List<EmergencyEgressAndLightingResDTO> getEmergencyEgress(final Long emergencyEgressId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        final List<EmergencyEgressAndLighting> emergencyEgressAndLightingList = new LinkedList<>();
        final List<BoilerCogenAgencyInfo> agencyInfoList = new LinkedList<>();
        if (equipmentDetailsReqVO.getEquipmentType() != null && !equipmentDetailsReqVO.getEquipmentType().equalsIgnoreCase(ConstantStore.EMERGENCY_EGRESS_AND_LIGHTING)) {
            throw new ValidationError(ApplicationMessageStore.EQUIPMENT_TYPE_NOT_MATCH);
        }
        if (emergencyEgressId != null) {
            final EmergencyEgressAndLighting emergencyEgressAndLighting = this.emergencyEgressRepository.findByEmergencyEgressId(emergencyEgressId);
            emergencyEgressAndLightingList.add(emergencyEgressAndLighting);
            final List<BoilerCogenAgencyInfo> boilerCogenAgencyInfoList = this.boilerCogenAgencyInfoRepository.findByEmergencyEgress(emergencyEgressAndLighting);
            boilerCogenAgencyInfoList.forEach(boilerCogenAgencyInfo -> {
                boilerCogenAgencyInfo.setEmergencyEgress(null);
                agencyInfoList.add(boilerCogenAgencyInfo);
            });
        } else {
            final Building building = this.buildingRepository.findByBuildingId(equipmentDetailsReqVO.getBuildingId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            if (equipmentDetailsReqVO.getFloor() == null) {
                emergencyEgressAndLightingList.addAll(this.emergencyEgressRepository.findAllByBuilding(building));
            } else {
                emergencyEgressAndLightingList.addAll(this.emergencyEgressRepository.findAllByBuildingAndFloor(building, equipmentDetailsReqVO.getFloor()));
            }
        }
        if (emergencyEgressAndLightingList.isEmpty()) {
            throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
        }
        return emergencyEgressAndLightingList.stream().map(egressAndLighting -> this.setEquipment(egressAndLighting, agencyInfoList)).toList();
    }

    public EmergencyEgressAndLightingResDTO updateEmergencyEgress(final Long emergencyEgressId, final EmergencyEgressAndLightingReqDTO equipmentReqDTO) {
        return this.createEmergencyEgress(equipmentReqDTO, emergencyEgressId);
    }

    public void deleteEmergencyEgress(final Long emergencyEgressId, final boolean activeStatus, final boolean isPermanentDelete) {
        final User user = this.validatorService.validateUser();
        final String userRole = user.getRole().getRole();
        final EmergencyEgressAndLighting emergencyEgressAndLighting;
        if (isPermanentDelete && (userRole.equalsIgnoreCase(ConstantStore.ADMIN_SUPER_USER) || userRole.equalsIgnoreCase(ConstantStore.ADMIN_USER))) {
            emergencyEgressAndLighting = this.emergencyEgressRepository.findByEmergencyEgressId(emergencyEgressId);
            if (emergencyEgressId == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            this.emergencyEgressRepository.delete(emergencyEgressAndLighting);
            this.commonUtilService.removeEquipment(ConstantStore.LAND_FILL, emergencyEgressAndLighting.getBuilding(), 1L);
        } else {
            emergencyEgressAndLighting = this.emergencyEgressRepository.findByEmergencyEgressIdAndIsActive(emergencyEgressId, true);
            if (emergencyEgressAndLighting == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            emergencyEgressAndLighting.setIsActive(activeStatus);
            this.emergencyEgressRepository.save(emergencyEgressAndLighting);
        }
    }

    private JobFilingInformation saveJobFilingInformation(final JobFilingInformation jobFilingInformation) {
        return this.jobFilingInformationRepository.save(jobFilingInformation);
    }

    private EmergencyEgressAndLightingResDTO setEquipment(final EmergencyEgressAndLighting emergencyEgressAndLighting, final List<BoilerCogenAgencyInfo> agencyInfoList) {
        return EmergencyEgressAndLightingResDTO.builder()
                .emergencyEgressId(emergencyEgressAndLighting.getEmergencyEgressId())
                .floor(emergencyEgressAndLighting.getFloor())
                .location(emergencyEgressAndLighting.getLocation())
                .isActive(emergencyEgressAndLighting.getIsActive())
                .installedOn(emergencyEgressAndLighting.getInstalledOn())
                .make(emergencyEgressAndLighting.getMake())
                .installedBy(emergencyEgressAndLighting.getInstalledBy())
                .managedBy(emergencyEgressAndLighting.getManagedBy())
                .serialNo(emergencyEgressAndLighting.getSerialNo())
                .disconnectedOn(emergencyEgressAndLighting.getDisconnectedOn())
                .landmark(emergencyEgressAndLighting.getLandmark())
                .model(emergencyEgressAndLighting.getModel())
                .management(!ObjectUtils.isEmpty(emergencyEgressAndLighting.getManagement()) ? emergencyEgressAndLighting.getManagement().getId() : null)
                .permitNumber(emergencyEgressAndLighting.getPermitNumber())
                .numberOfSignBoards(emergencyEgressAndLighting.getNumberOfSignBoards())
                .managementNote(emergencyEgressAndLighting.getManagementNote())
                .managedBy(emergencyEgressAndLighting.getManagedBy())
                .uniqueId(emergencyEgressAndLighting.getUniqueId())
                .deviceType(emergencyEgressAndLighting.getDeviceType())
                .permitDate(emergencyEgressAndLighting.getPermitDate())
                .buildingId(emergencyEgressAndLighting.getBuilding().getBuildingId())
                .jobFilingInformation(!ObjectUtils.isEmpty(emergencyEgressAndLighting.getJobFilingInformation()) ? emergencyEgressAndLighting.getJobFilingInformation() : null)
                .status(!ObjectUtils.isEmpty(emergencyEgressAndLighting.getStatus()) ? emergencyEgressAndLighting.getStatus().getStatusId() : null)
                .agencyInfoList(!ObjectUtils.isEmpty(agencyInfoList) ? this.setBoilerCogenAgencyInfoDTOS(agencyInfoList) : null)
                .build();
    }


    private void saveAgency(final EmergencyEgressAndLightingReqDTO equipmentReqDTO, final EmergencyEgressAndLighting emergencyEgressAndLighting) {
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
                boilerCogenAgencyInfo.setEmergencyEgress(emergencyEgressAndLighting);
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
