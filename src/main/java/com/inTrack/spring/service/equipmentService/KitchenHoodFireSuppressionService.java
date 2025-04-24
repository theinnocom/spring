package com.inTrack.spring.service.equipmentService;

import com.inTrack.spring.UtilService.CommonUtilService;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.dto.requestDTO.KitchenHoodFireSuppressionDTO;
import com.inTrack.spring.dto.responseDTO.BoilerCogenAgencyInfoDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.User;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.KitchenHoodFireSuppression;
import com.inTrack.spring.entity.equipmentEntity.TypeOfSuppression;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.BoilerCogenAgency;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.InspectionType;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.AgencyRepo.BoilerCogenAgencyInfoRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.BoilerCogenAgencyRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.InspectionTypeRepository;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
import com.inTrack.spring.repository.equipmentRepository.KitchenHoodFireSuppressionRepository;
import com.inTrack.spring.repository.equipmentRepository.TypeOfSuppressionRepository;
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
public class KitchenHoodFireSuppressionService {

    private final KitchenHoodFireSuppressionRepository kitchenHoodFireSuppressionRepository;
    private final ValidatorService validatorService;
    private final BuildingRepository buildingRepository;
    private final CommonUtilService commonUtilService;
    private final JobFilingInformationRepository jobFilingInformationRepository;
    private final CommonEquipmentService commonEquipmentService;
    private final TypeOfSuppressionRepository typeOfSuppressionRepository;
    private final BoilerCogenAgencyInfoRepository boilerCogenAgencyInfoRepository;
    private final BoilerCogenAgencyRepository boilerCogenAgencyRepository;
    private final InspectionTypeRepository inspectionTypeRepository;

    @Transactional(rollbackOn = Exception.class)
    public KitchenHoodFireSuppressionDTO createKitchenHoodFireSuppression(final KitchenHoodFireSuppressionDTO equipmentReqDTO, final Long kitchenHoodId) {
        final KitchenHoodFireSuppression kitchenHoodFireSuppression;
        final Building building = this.buildingRepository.findByBuildingId(equipmentReqDTO.getBuildingId());
        if (kitchenHoodId != null) {
            kitchenHoodFireSuppression = this.kitchenHoodFireSuppressionRepository.findByKitchenHoodId(kitchenHoodId);
            kitchenHoodFireSuppression.getJobFilingInformation().setStatus(equipmentReqDTO.getJobFilingInformationReqDTO().getStatus());
        } else {
            final Long uniqueIdCount = this.kitchenHoodFireSuppressionRepository.countByUniqueId(equipmentReqDTO.getUniqueId());
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
            kitchenHoodFireSuppression = new KitchenHoodFireSuppression();
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            kitchenHoodFireSuppression.setBuilding(building);
            kitchenHoodFireSuppression.setUniqueId(equipmentReqDTO.getUniqueId());
            this.commonUtilService.saveEquipment(ConstantStore.KITCHEN_HOOD_FIR_SUPPRESSION, building, 1L);
            this.commonEquipmentService.createEquipmentFloorRelation(equipmentReqDTO.getFloor(), ConstantStore.KITCHEN_HOOD_FIR_SUPPRESSION, building);
        }
        if (equipmentReqDTO.getJobFilingInformationReqDTO() != null) {
            if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformationReqDTO().getJobFilingId())) {
                this.commonEquipmentService.saveJobFiling(equipmentReqDTO.getJobFilingInformationReqDTO());
            } else {
                kitchenHoodFireSuppression.setJobFilingInformation(this.saveJobFilingInformation(this.setJobFilingInfo(equipmentReqDTO.getJobFilingInformationReqDTO())));
            }
        }
        kitchenHoodFireSuppression.setInternalManagement(equipmentReqDTO.getInternalManagement());
        kitchenHoodFireSuppression.setManagement(!ObjectUtils.isEmpty(equipmentReqDTO.getManagement()) ? new ManagementType().setId(equipmentReqDTO.getManagement()) : null);
        kitchenHoodFireSuppression.setModel(equipmentReqDTO.getModel());
        kitchenHoodFireSuppression.setManagementNote(equipmentReqDTO.getManagementNote());
        kitchenHoodFireSuppression.setModel(equipmentReqDTO.getModel());
        kitchenHoodFireSuppression.setCapacity(equipmentReqDTO.getCapacity());
        kitchenHoodFireSuppression.setDeviceType(ConstantStore.KITCHEN_HOOD_FIR_SUPPRESSION);
        kitchenHoodFireSuppression.setDisconnectedOn(equipmentReqDTO.getDisconnectedOn());
        kitchenHoodFireSuppression.setIsPortableFireExtinguisher(equipmentReqDTO.isPortableFireExtinguisher());
        kitchenHoodFireSuppression.setLandmark(equipmentReqDTO.getLandmark());
        kitchenHoodFireSuppression.setTypeOfSuppression(!ObjectUtils.isEmpty(equipmentReqDTO.getTypeOfSuppression()) ? new TypeOfSuppression().setSuppressionTypeId(equipmentReqDTO.getTypeOfSuppression()) : null);
        kitchenHoodFireSuppression.setInspectionTestFrequency(equipmentReqDTO.getInspectionTestFrequency());
        kitchenHoodFireSuppression.setIsInspectionTagAttached(equipmentReqDTO.getIsInspectionTagAttached());
        kitchenHoodFireSuppression.setPermitDate(equipmentReqDTO.getPermitDate());
        kitchenHoodFireSuppression.setPermitNumber(equipmentReqDTO.getPermitNumber());
        kitchenHoodFireSuppression.setRegulatoryAuthority(equipmentReqDTO.getRegulatoryAuthority());
        kitchenHoodFireSuppression.setFloor(equipmentReqDTO.getFloor());
        kitchenHoodFireSuppression.setLocation(equipmentReqDTO.getLocation());
        kitchenHoodFireSuppression.setInstalledOn(equipmentReqDTO.getInstalledOn());
        kitchenHoodFireSuppression.setMake(equipmentReqDTO.getMake());
        kitchenHoodFireSuppression.setInstalledBy(equipmentReqDTO.getInstalledBy());
        kitchenHoodFireSuppression.setManagedBy(equipmentReqDTO.getManagedBy());
        kitchenHoodFireSuppression.setSerialNo(equipmentReqDTO.getSerialNo());
        kitchenHoodFireSuppression.setStatus(!ObjectUtils.isEmpty(equipmentReqDTO.getStatus()) ? new ElevatorStatus().setStatusId(equipmentReqDTO.getStatus()) : null);
        final KitchenHoodFireSuppression kitchenHood = this.kitchenHoodFireSuppressionRepository.save(kitchenHoodFireSuppression);
        this.saveAgency(equipmentReqDTO, kitchenHood);
        return this.setEquipment(kitchenHood, null);
    }

    public List<KitchenHoodFireSuppressionDTO> getKitchenHoodFireSuppression(final Long kitchenHoodId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        final List<KitchenHoodFireSuppression> kitchenHoodFireSuppressionList = new LinkedList<>();
        final List<KitchenHoodFireSuppressionDTO> kitchenHoodFireSuppressionDTOS = new LinkedList<>();
        List<BoilerCogenAgencyInfo> boilerCogenAgencyInfoList = new LinkedList<>();
        if (equipmentDetailsReqVO.getEquipmentType() != null && !equipmentDetailsReqVO.getEquipmentType().equalsIgnoreCase(ConstantStore.KITCHEN_HOOD_FIR_SUPPRESSION)) {
            throw new ValidationError(ApplicationMessageStore.EQUIPMENT_TYPE_NOT_MATCH);
        }
        if (kitchenHoodId != null) {
            final KitchenHoodFireSuppression kitchenHoodFireSuppression = this.kitchenHoodFireSuppressionRepository.findByKitchenHoodId(kitchenHoodId);
            final List<BoilerCogenAgencyInfo> boilerCogenAgencyInfos = this.boilerCogenAgencyInfoRepository.findByKitchenHoodFireSuppression(kitchenHoodFireSuppression);
            boilerCogenAgencyInfos.forEach(boilerCogenAgencyInfo -> {
                boilerCogenAgencyInfo.setKitchenHoodFireSuppression(null);
                boilerCogenAgencyInfoList.add(boilerCogenAgencyInfo);
            });
            kitchenHoodFireSuppressionList.add(kitchenHoodFireSuppression);
        } else {
            final Building building = this.buildingRepository.findByBuildingId(equipmentDetailsReqVO.getBuildingId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            if (equipmentDetailsReqVO.getFloor() == null) {
                kitchenHoodFireSuppressionList.addAll(this.kitchenHoodFireSuppressionRepository.findAllByBuilding(building));
            } else {
                kitchenHoodFireSuppressionList.addAll(this.kitchenHoodFireSuppressionRepository.findAllByBuildingAndFloor(building, equipmentDetailsReqVO.getFloor()));
            }
        }
        if (kitchenHoodFireSuppressionList.isEmpty()) {
            throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
        }
        kitchenHoodFireSuppressionList.forEach(kitchenHoodFireSuppression -> kitchenHoodFireSuppressionDTOS.add(this.setEquipment(kitchenHoodFireSuppression, boilerCogenAgencyInfoList)));
        return kitchenHoodFireSuppressionDTOS;
    }

    public KitchenHoodFireSuppressionDTO updateKitchenHoodFireSuppression(final Long kitchenHoodId, final KitchenHoodFireSuppressionDTO equipmentReqDTO) {
        return this.createKitchenHoodFireSuppression(equipmentReqDTO, kitchenHoodId);
    }

    public void deleteKitchenHoodFireSuppression(final Long kitchenHoodId, final boolean activeStatus, final boolean isPermanentDelete) {
        final User user = this.validatorService.validateUser();
        final String userRole = user.getRole().getRole();
        final KitchenHoodFireSuppression kitchenHoodFireSuppression;
        if (isPermanentDelete && (userRole.equalsIgnoreCase(ConstantStore.ADMIN_SUPER_USER) || userRole.equalsIgnoreCase(ConstantStore.ADMIN_USER))) {
            kitchenHoodFireSuppression = this.kitchenHoodFireSuppressionRepository.findByKitchenHoodId(kitchenHoodId);
            if (kitchenHoodId == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            this.kitchenHoodFireSuppressionRepository.delete(kitchenHoodFireSuppression);
            this.commonUtilService.removeEquipment(ConstantStore.KITCHEN_HOOD_FIR_SUPPRESSION, kitchenHoodFireSuppression.getBuilding(), 1L);
        } else {
            kitchenHoodFireSuppression = this.kitchenHoodFireSuppressionRepository.findByKitchenHoodIdAndIsActive(kitchenHoodId, true);
            if (kitchenHoodFireSuppression == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            kitchenHoodFireSuppression.setIsActive(activeStatus);
            this.kitchenHoodFireSuppressionRepository.save(kitchenHoodFireSuppression);
        }
    }

    private JobFilingInformation saveJobFilingInformation(final JobFilingInformation jobFilingInformation) {
        return this.jobFilingInformationRepository.save(jobFilingInformation);
    }

    private KitchenHoodFireSuppressionDTO setEquipment(final KitchenHoodFireSuppression kitchenHoodFireSuppression, final List<BoilerCogenAgencyInfo> agencyInfoList) {
        final KitchenHoodFireSuppressionDTO equipmentResDTO = new KitchenHoodFireSuppressionDTO();
        equipmentResDTO.setId(kitchenHoodFireSuppression.getKitchenHoodId());
        equipmentResDTO.setBuildingId(kitchenHoodFireSuppression.getBuilding().getBuildingId());
        equipmentResDTO.setUniqueId(kitchenHoodFireSuppression.getUniqueId());
        equipmentResDTO.setInternalManagement(kitchenHoodFireSuppression.getInternalManagement());
        equipmentResDTO.setManagement(!ObjectUtils.isEmpty(kitchenHoodFireSuppression.getManagement()) ? kitchenHoodFireSuppression.getManagement().getId() : null);
        equipmentResDTO.setModel(kitchenHoodFireSuppression.getModel());
        equipmentResDTO.setManagementNote(kitchenHoodFireSuppression.getManagementNote());
        equipmentResDTO.setModel(kitchenHoodFireSuppression.getModel());
        equipmentResDTO.setCapacity(kitchenHoodFireSuppression.getCapacity());
        equipmentResDTO.setDeviceType(ConstantStore.KITCHEN_HOOD_FIR_SUPPRESSION);
        equipmentResDTO.setDisconnectedOn(kitchenHoodFireSuppression.getDisconnectedOn());
        equipmentResDTO.setPortableFireExtinguisher(kitchenHoodFireSuppression.getIsPortableFireExtinguisher());
        equipmentResDTO.setLandmark(kitchenHoodFireSuppression.getLandmark());
        equipmentResDTO.setTypeOfSuppression(!ObjectUtils.isEmpty(kitchenHoodFireSuppression.getTypeOfSuppression()) ? kitchenHoodFireSuppression.getTypeOfSuppression().getSuppressionTypeId() : null);
        equipmentResDTO.setInspectionTestFrequency(kitchenHoodFireSuppression.getInspectionTestFrequency());
        equipmentResDTO.setIsInspectionTagAttached(kitchenHoodFireSuppression.getIsInspectionTagAttached());
        equipmentResDTO.setPermitDate(kitchenHoodFireSuppression.getPermitDate());
        equipmentResDTO.setPermitNumber(kitchenHoodFireSuppression.getPermitNumber());
        equipmentResDTO.setRegulatoryAuthority(kitchenHoodFireSuppression.getRegulatoryAuthority());
        equipmentResDTO.setFloor(kitchenHoodFireSuppression.getFloor());
        equipmentResDTO.setLocation(kitchenHoodFireSuppression.getLocation());
        equipmentResDTO.setInstalledOn(kitchenHoodFireSuppression.getInstalledOn());
        equipmentResDTO.setMake(kitchenHoodFireSuppression.getMake());
        equipmentResDTO.setInstalledBy(kitchenHoodFireSuppression.getInstalledBy());
        equipmentResDTO.setManagedBy(kitchenHoodFireSuppression.getManagedBy());
        equipmentResDTO.setSerialNo(kitchenHoodFireSuppression.getSerialNo());
        equipmentResDTO.setStatus(!ObjectUtils.isEmpty(kitchenHoodFireSuppression.getStatus()) ? kitchenHoodFireSuppression.getStatus().getStatusId() : null);
        equipmentResDTO.setAgencyInfoList(!ObjectUtils.isEmpty(agencyInfoList) ? this.setBoilerCogenAgencyInfoDTOS(agencyInfoList) : null);
        equipmentResDTO.setJobFilingInformationReqDTO(this.setJobFilingInfo(kitchenHoodFireSuppression.getJobFilingInformation()));
        return equipmentResDTO;
    }

    public List<TypeOfSuppression> getSuppressionType() {
        return typeOfSuppressionRepository.findAll();
    }


    private void saveAgency(final KitchenHoodFireSuppressionDTO equipmentReqDTO, final KitchenHoodFireSuppression kitchenHoodFireSuppression) {
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
                boilerCogenAgencyInfo.setKitchenHoodFireSuppression(kitchenHoodFireSuppression);
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
