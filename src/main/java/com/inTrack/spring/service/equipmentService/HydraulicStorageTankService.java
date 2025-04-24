package com.inTrack.spring.service.equipmentService;

import com.inTrack.spring.dto.requestDTO.EmergencyEgressAndLightingReqDTO;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.HydraulicStorageTankReqDTO;
import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.dto.responseDTO.BoilerCogenAgencyInfoDTO;
import com.inTrack.spring.dto.responseDTO.HydraulicStorageTankResDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.User;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.EmergencyEgressAndLighting;
import com.inTrack.spring.entity.equipmentEntity.HydraulicStorageTank;
import com.inTrack.spring.entity.equipmentEntity.HydraulicStorageTankDropDown.HydraulicStorageTankUsage;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.BoilerCogenAgency;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.InspectionType;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.AgencyRepo.BoilerCogenAgencyInfoRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.BoilerCogenAgencyRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.InspectionTypeRepository;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
import com.inTrack.spring.repository.equipmentRepository.HydraulicStorageTankRepository;
import com.inTrack.spring.repository.equipmentRepository.HydraulicStorageTankUsageRepository;
import com.inTrack.spring.UtilService.CommonUtilService;
import com.inTrack.spring.service.ValidatorService;
import com.inTrack.spring.store.ApplicationMessageStore;
import com.inTrack.spring.store.ConstantStore;
import jakarta.persistence.ManyToOne;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HydraulicStorageTankService {

    private final HydraulicStorageTankRepository hydraulicStorageTankRepository;
    private final HydraulicStorageTankUsageRepository hydraulicStorageTankUsageRepository;
    private final ValidatorService validatorService;
    private final BuildingRepository buildingRepository;
    private final JobFilingInformationRepository jobFilingInformationRepository;
    private final CommonEquipmentService commonEquipmentService;
    private final CommonUtilService commonUtilService;
    private final BoilerCogenAgencyInfoRepository boilerCogenAgencyInfoRepository;
    private final BoilerCogenAgencyRepository boilerCogenAgencyRepository;
    private final InspectionTypeRepository inspectionTypeRepository;

    public HydraulicStorageTankResDTO createStorageTank(final HydraulicStorageTankReqDTO hydraulicStorageTankReqDTO, final Long tankId) {
        final Building building = this.buildingRepository.findByBuildingId(hydraulicStorageTankReqDTO.getBuildingId());
        HydraulicStorageTank hydraulicStorageTank;
        if (tankId != null) {
            hydraulicStorageTank = this.hydraulicStorageTankRepository.findByIdAndIsActive(tankId, true);
            hydraulicStorageTank.setUpdatedAt(System.currentTimeMillis());
        } else {
            final Long uniqueIdCount = this.hydraulicStorageTankRepository.countByUniqueId(hydraulicStorageTankReqDTO.getUniqueId());
            Long jobFillingIdCount = 0L;
            if (!ObjectUtils.isEmpty(hydraulicStorageTankReqDTO.getJobFilingInformationReqDTO())) {
                jobFillingIdCount = this.jobFilingInformationRepository.countByJobNumber(hydraulicStorageTankReqDTO.getJobFilingInformationReqDTO().getJobNumber());
            }
            if (uniqueIdCount > 0 && jobFillingIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.UNIQUE_ID_AND_JOB_FILLING_ID_EXISTS);
            } else if (uniqueIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.UNIQUE_ID_EXISTS);
            } else if (jobFillingIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.JOB_FILLING_ID_EXISTS);
            }
            hydraulicStorageTank = new HydraulicStorageTank();
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            hydraulicStorageTank.setBuilding(building);
            this.commonUtilService.saveEquipment(ConstantStore.ELEVATOR, building, 1L);
            this.commonEquipmentService.createEquipmentFloorRelation(hydraulicStorageTankReqDTO.getFloor(), ConstantStore.ELEVATOR, building);
            hydraulicStorageTank.setUniqueId(hydraulicStorageTankReqDTO.getUniqueId());
            hydraulicStorageTank.setCreatedAt(System.currentTimeMillis());
        }
        if (!ObjectUtils.isEmpty(hydraulicStorageTankReqDTO.getJobFilingInformationReqDTO())) {
            if (!ObjectUtils.isEmpty(hydraulicStorageTankReqDTO.getJobFilingInformationReqDTO().getJobFilingId())) {
                this.commonEquipmentService.saveJobFiling(hydraulicStorageTankReqDTO.getJobFilingInformationReqDTO());
            } else {
                hydraulicStorageTank.setJobFilingInformation(this.saveJobFilingInformation(this.setJobFilingInfo(hydraulicStorageTankReqDTO.getJobFilingInformationReqDTO())));
            }
        }
        hydraulicStorageTank.setActive(true);
        hydraulicStorageTank.setStatus(!ObjectUtils.isEmpty(hydraulicStorageTankReqDTO.getStatus()) ? new ElevatorStatus().setStatusId(hydraulicStorageTankReqDTO.getStatus()) : null);
        hydraulicStorageTank.setUpdatedAt(System.currentTimeMillis());
        hydraulicStorageTank.setFloor(hydraulicStorageTankReqDTO.getFloor());
        hydraulicStorageTank.setLocation(hydraulicStorageTankReqDTO.getLocation());
        hydraulicStorageTank.setLandmark(hydraulicStorageTankReqDTO.getLandmark());
        hydraulicStorageTank.setInstalledOn(hydraulicStorageTankReqDTO.getInstalledOn());
        hydraulicStorageTank.setInstalledBy(hydraulicStorageTankReqDTO.getInstalledBy());
        hydraulicStorageTank.setManagement(!ObjectUtils.isEmpty(hydraulicStorageTankReqDTO.getManagement()) ? new ManagementType().setId(hydraulicStorageTankReqDTO.getManagement()) : null);
        hydraulicStorageTank.setManagedBy(hydraulicStorageTankReqDTO.getManagedBy());
        hydraulicStorageTank.setManagementNote(hydraulicStorageTankReqDTO.getManagementNote());
        hydraulicStorageTank.setDisconnectedOn(hydraulicStorageTankReqDTO.getDisconnectedOn());
        hydraulicStorageTank.setUsage(!ObjectUtils.isEmpty(hydraulicStorageTankReqDTO.getUsage()) ? new HydraulicStorageTankUsage().setId(hydraulicStorageTankReqDTO.getUsage()) : null);
        hydraulicStorageTank.setSecondaryContainment(hydraulicStorageTankReqDTO.isSecondaryContainment());
        hydraulicStorageTank.setAgencyApprovalRequired(hydraulicStorageTankReqDTO.isAgencyApprovalRequired());
        hydraulicStorageTank.setTankNumber(hydraulicStorageTankReqDTO.getTankNumber());
        hydraulicStorageTank.setSpillKitAvailable(hydraulicStorageTankReqDTO.isSpillKitAvailable());
        hydraulicStorageTank.setComments(hydraulicStorageTankReqDTO.getComments());
        final HydraulicStorageTank tank = this.hydraulicStorageTankRepository.save(hydraulicStorageTank);
        this.saveAgency(hydraulicStorageTankReqDTO, tank);
        return this.setStorageTank(tank, null);
    }

    public List<HydraulicStorageTankResDTO> getStorageTank(final Long tankId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        List<HydraulicStorageTankResDTO> hydraulicStorageTankResDTOS = new LinkedList<>();
        final List<BoilerCogenAgencyInfo> boilerCogenAgencyInfoList = new LinkedList<>();
//        if (equipmentDetailsReqVO.getEquipmentType() != null && !equipmentDetailsReqVO.getEquipmentType().equalsIgnoreCase(ConstantStore.ELEVATOR)) {
//            throw new ValidationError(ApplicationMessageStore.EQUIPMENT_TYPE_NOT_MATCH);
//        }
        if (tankId == null) {
            final Building building = this.buildingRepository.findByBuildingId(equipmentDetailsReqVO.getBuildingId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            if (equipmentDetailsReqVO.getFloor() == null) {
                hydraulicStorageTankResDTOS = this.hydraulicStorageTankRepository.findAllByBuilding(building)
                        .stream()
                        .map(tank -> this.setStorageTank(tank, boilerCogenAgencyInfoList))
                        .toList();
            } else {
                hydraulicStorageTankResDTOS = this.hydraulicStorageTankRepository.findAllByBuildingAndFloor(building, equipmentDetailsReqVO.getFloor())
                        .stream()
                        .map(tank -> this.setStorageTank(tank, boilerCogenAgencyInfoList))
                        .toList();
            }
        } else {
            final HydraulicStorageTank tank = this.hydraulicStorageTankRepository.findById(tankId).orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
            final List<BoilerCogenAgencyInfo> agencyInfoList = this.boilerCogenAgencyInfoRepository.findByHydraulicStorageTank(tank);
            agencyInfoList.forEach(agencyInfo -> {
                agencyInfo.setHydraulicStorageTank(null);
                boilerCogenAgencyInfoList.add(agencyInfo);
            });
            hydraulicStorageTankResDTOS.add(this.setStorageTank(tank, boilerCogenAgencyInfoList));
        }
        return hydraulicStorageTankResDTOS;
    }

    public HydraulicStorageTankResDTO updateStorageTank(final HydraulicStorageTankReqDTO hydraulicStorageTankReqDTO, final Long tankId) {
        if (tankId == null) {
            throw new ValidationError(ApplicationMessageStore.ELEVATOR_NOT_FOUND);
        }
        return this.createStorageTank(hydraulicStorageTankReqDTO, tankId);
    }

    public void deleteStorageTank(final Long tankId, final boolean activeStatus, final boolean isPermanentDelete) {
        final User user = this.validatorService.validateUser();
        final String userRole = user.getRole().getRole();
        final HydraulicStorageTank hydraulicStorageTank;
        if (isPermanentDelete && (userRole.equalsIgnoreCase(ConstantStore.ADMIN_SUPER_USER) || userRole.equalsIgnoreCase(ConstantStore.ADMIN_USER))) {
            hydraulicStorageTank = this.hydraulicStorageTankRepository.findById(tankId).orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
            this.hydraulicStorageTankRepository.delete(hydraulicStorageTank);
            this.commonUtilService.removeEquipment(ConstantStore.ELEVATOR, hydraulicStorageTank.getBuilding(), 1L);
        } else {
            hydraulicStorageTank = this.hydraulicStorageTankRepository.findByIdAndIsActive(tankId, activeStatus);
            this.hydraulicStorageTankRepository.delete(hydraulicStorageTank);
        }
    }


    private JobFilingInformation saveJobFilingInformation(final JobFilingInformation jobFilingInformation) {
        return this.jobFilingInformationRepository.save(jobFilingInformation);
    }

    private HydraulicStorageTankResDTO setStorageTank(final HydraulicStorageTank hydraulicStorageTank, final List<BoilerCogenAgencyInfo> agencyInfoList) {
        final HydraulicStorageTankResDTO hydraulicStorageTankResDTO = new HydraulicStorageTankResDTO();
        hydraulicStorageTankResDTO.setId(hydraulicStorageTank.getId());
        hydraulicStorageTankResDTO.setManagement(!ObjectUtils.isEmpty(hydraulicStorageTank.getManagement()) ? hydraulicStorageTank.getManagement().getId() : null);
        hydraulicStorageTankResDTO.setStatus(!ObjectUtils.isEmpty(hydraulicStorageTank.getStatus()) ? hydraulicStorageTank.getStatus().getStatusId() : null);
        hydraulicStorageTankResDTO.setFloor(hydraulicStorageTank.getFloor());
        hydraulicStorageTankResDTO.setLocation(hydraulicStorageTank.getLocation());
        hydraulicStorageTankResDTO.setLandmark(hydraulicStorageTank.getLandmark());
        hydraulicStorageTankResDTO.setInstalledOn(hydraulicStorageTank.getInstalledOn());
        hydraulicStorageTankResDTO.setInstalledBy(hydraulicStorageTank.getInstalledBy());
        hydraulicStorageTankResDTO.setManagement(!ObjectUtils.isEmpty(hydraulicStorageTank.getManagement()) ? hydraulicStorageTank.getManagement().getId() : null);
        hydraulicStorageTankResDTO.setManagedBy(hydraulicStorageTank.getManagedBy());
        hydraulicStorageTankResDTO.setManagementNote(hydraulicStorageTank.getManagementNote());
        hydraulicStorageTankResDTO.setDisconnectedOn(hydraulicStorageTank.getDisconnectedOn());
        hydraulicStorageTankResDTO.setUsage(!ObjectUtils.isEmpty(hydraulicStorageTank.getUsage()) ? hydraulicStorageTank.getUsage().getId() : null);
        hydraulicStorageTankResDTO.setSecondaryContainment(hydraulicStorageTank.isSecondaryContainment());
        hydraulicStorageTankResDTO.setAgencyApprovalRequired(hydraulicStorageTank.isAgencyApprovalRequired());
        hydraulicStorageTankResDTO.setTankNumber(hydraulicStorageTank.getTankNumber());
        hydraulicStorageTankResDTO.setSpillKitAvailable(hydraulicStorageTank.isSpillKitAvailable());
        hydraulicStorageTankResDTO.setComments(hydraulicStorageTank.getComments());
        hydraulicStorageTankResDTO.setUniqueId(hydraulicStorageTank.getUniqueId());
        hydraulicStorageTankResDTO.setJobFilingInformation(!ObjectUtils.isEmpty(hydraulicStorageTank.getJobFilingInformation()) ? hydraulicStorageTank.getJobFilingInformation() : null);
        hydraulicStorageTankResDTO.setBuildingId(hydraulicStorageTank.getBuilding().getBuildingId());
        hydraulicStorageTankResDTO.setAgencyInfoList(!ObjectUtils.isEmpty(agencyInfoList) ?  this.setBoilerCogenAgencyInfoDTOS(agencyInfoList) : null);
        return hydraulicStorageTankResDTO;
    }


    private void saveAgency(final HydraulicStorageTankReqDTO equipmentReqDTO, final HydraulicStorageTank hydraulicStorageTank) {
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
                boilerCogenAgencyInfo.setHydraulicStorageTank(hydraulicStorageTank);
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
