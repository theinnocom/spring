package com.inTrack.spring.service.equipmentService;

import com.inTrack.spring.dto.requestDTO.CogenTurbineReqDTO;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.FumeHoodReqDTO;
import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.dto.responseDTO.BoilerCogenAgencyInfoDTO;
import com.inTrack.spring.dto.responseDTO.FumeHoodResDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.User;
import com.inTrack.spring.entity.equipmentEntity.*;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.BoilerCogenAgency;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.InspectionType;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.AgencyRepo.BoilerCogenAgencyInfoRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.BoilerCogenAgencyRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.InspectionTypeRepository;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
import com.inTrack.spring.repository.equipmentRepository.FumeHoodChemicalDetailRepository;
import com.inTrack.spring.repository.equipmentRepository.FumeHoodRepository;
import com.inTrack.spring.UtilService.CommonUtilService;
import com.inTrack.spring.repository.equipmentRepository.StackRepository;
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
import java.util.stream.Collectors;

/**
 * @author vijayan
 */

@Service
@RequiredArgsConstructor
public class FumeHoodService {

    private final FumeHoodRepository fumeHoodRepository;
    private final ValidatorService validatorService;
    private final BuildingRepository buildingRepository;
    private final CommonUtilService commonUtilService;
    private final JobFilingInformationRepository jobFilingInformationRepository;
    private final CommonEquipmentService commonEquipmentService;
    private final FumeHoodChemicalDetailRepository fumeHoodChemicalDetailRepository;
    private final BoilerCogenAgencyInfoRepository boilerCogenAgencyInfoRepository;
    private final StackRepository stackRepository;
    private final BoilerCogenAgencyRepository boilerCogenAgencyRepository;
    private final InspectionTypeRepository inspectionTypeRepository;

    @Transactional(rollbackOn = Exception.class)
    public FumeHoodResDTO createFumeHood(final FumeHoodReqDTO equipmentReqDTO, final Long fumeHoodId) {
        final FumeHood fumeHood;
        final Building building = this.buildingRepository.findByBuildingId(equipmentReqDTO.getBuildingId());
        if (fumeHoodId != null) {
            fumeHood = this.fumeHoodRepository.findByFumeHoodId(fumeHoodId);
        } else {
            fumeHood = new FumeHood();
            fumeHood.setIsActive(true);
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            fumeHood.setBuilding(building);
            fumeHood.setUniqueId(equipmentReqDTO.getUniqueId());
            if (equipmentReqDTO.getJobFilingInformationReqDTO() != null) {
                final Long jobFillingIdCount = this.jobFilingInformationRepository.countByJobNumber(equipmentReqDTO.getJobFilingInformationReqDTO().getJobNumber());
                if (jobFillingIdCount > 0) {
                    throw new ValidationError(ApplicationMessageStore.JOB_FILLING_ID_EXISTS);
                }
                final JobFilingInformation jobFilingInformation = new JobFilingInformation();
                final JobFilingInformationReqDTO jobFilingInformationReqDTO = equipmentReqDTO.getJobFilingInformationReqDTO();
                jobFilingInformation.setJobNumber(jobFilingInformationReqDTO.getJobNumber());
                jobFilingInformation.setStatus(jobFilingInformationReqDTO.getStatus());
                jobFilingInformation.setType(jobFilingInformationReqDTO.getType());
                jobFilingInformation.setDescription(jobFilingInformationReqDTO.getDescription());
                jobFilingInformation.setCommands(jobFilingInformationReqDTO.getCommands());
                jobFilingInformation.setApprovalDate(jobFilingInformationReqDTO.getApprovalDate());
                jobFilingInformation.setApprovalDate(jobFilingInformationReqDTO.getApprovalDate());
                jobFilingInformation.setSignOffDate(jobFilingInformationReqDTO.getSignOffDate());
                jobFilingInformation.setType(jobFilingInformationReqDTO.getType());
                jobFilingInformation.setEquipmentName(jobFilingInformationReqDTO.getEquipmentName());
                fumeHood.setJobFilingInformation(this.saveJobFilingInformation(jobFilingInformation));
            }
            this.commonUtilService.saveEquipment(ConstantStore.FUME_HOOD, building, 1L);
            this.commonEquipmentService.createEquipmentFloorRelation(equipmentReqDTO.getFloor(), ConstantStore.FUME_HOOD, building);
        }
        fumeHood.setFloor(equipmentReqDTO.getFloor());
        fumeHood.setLocation(equipmentReqDTO.getLocation());
        fumeHood.setInstalledOn(equipmentReqDTO.getInstalledOn());
        fumeHood.setMakeBy(equipmentReqDTO.getMakeBy());
        fumeHood.setInstalledBy(equipmentReqDTO.getInstalledBy());
        fumeHood.setManagedBy(equipmentReqDTO.getManagedBy());
        fumeHood.setSerialNo(equipmentReqDTO.getSerialNo());
        fumeHood.setStatus(!ObjectUtils.isEmpty(equipmentReqDTO.getStatus()) ? new ElevatorStatus().setStatusId(equipmentReqDTO.getStatus()) : null);
        fumeHood.setType(equipmentReqDTO.getType());
        fumeHood.setManagement(!ObjectUtils.isEmpty(equipmentReqDTO.getManagement()) ? new ManagementType().setId(equipmentReqDTO.getManagement()) : null);
        fumeHood.setHoursOfOperation(equipmentReqDTO.getHoursOfOperation());
        if (equipmentReqDTO.getStack() != null) {
            final Stack stack = this.stackRepository.findByStackId(equipmentReqDTO.getStack());
            if (stack == null) {
                throw new ValidationError(ApplicationMessageStore.STACK_NOT_FOUND);
            }
            fumeHood.setStack(stack);
        }
        FumeHood fumeHoodObj = this.fumeHoodRepository.save(fumeHood);
        final List<FumeHoodChemicalDetail> chemicalDetailList = new LinkedList<>();
        if (equipmentReqDTO.getFumeHoodChemicalDetails() != null) {
            final List<FumeHoodChemicalDetail> fumeHoodChemicalDetails = new LinkedList<>();
            equipmentReqDTO.getFumeHoodChemicalDetails().forEach(chemicalDetail -> {
                chemicalDetail.setFumeHood(fumeHoodObj);
                fumeHoodChemicalDetails.add(chemicalDetail);
            });
            chemicalDetailList.addAll(this.fumeHoodChemicalDetailRepository.saveAll(fumeHoodChemicalDetails));
        }
        this.saveAgency(equipmentReqDTO, fumeHoodObj);
        return this.setEquipment(fumeHoodObj, chemicalDetailList, null);
    }

    public List<FumeHoodResDTO> getFumeHood(final Long fumeHoodId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        final List<FumeHood> fumeHoods = new LinkedList<>();
        final List<BoilerCogenAgencyInfo> boilerCogenAgencyInfoList = new LinkedList<>();
        if (equipmentDetailsReqVO.getEquipmentType() != null && !equipmentDetailsReqVO.getEquipmentType().equalsIgnoreCase(ConstantStore.FUME_HOOD)) {
            throw new ValidationError(ApplicationMessageStore.EQUIPMENT_TYPE_NOT_MATCH);
        }
        List<FumeHoodChemicalDetail> fumeHoodChemicalDetail;
        if (fumeHoodId != null) {
            final FumeHood fumeHood = this.fumeHoodRepository.findByFumeHoodId(fumeHoodId);
            fumeHoodChemicalDetail = this.fumeHoodChemicalDetailRepository.findByFumeHood(fumeHood);
            final List<BoilerCogenAgencyInfo> agencyInfoList = this.boilerCogenAgencyInfoRepository.findByFumeHood(fumeHood);
            agencyInfoList.forEach(agencyInfo -> {
                agencyInfo.setFumeHood(null);
                boilerCogenAgencyInfoList.add(agencyInfo);
            });
            fumeHoods.add(fumeHood);
        } else {
            fumeHoodChemicalDetail = null;
            final Building building = this.buildingRepository.findByBuildingId(equipmentDetailsReqVO.getBuildingId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            if (equipmentDetailsReqVO.getFloor() == null) {
                fumeHoods.addAll(this.fumeHoodRepository.findAllByBuilding(building));
            } else {
                fumeHoods.addAll(this.fumeHoodRepository.findAllByBuildingAndFloor(building, equipmentDetailsReqVO.getFloor()));
            }
        }
        if (fumeHoods.isEmpty()) {
            throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
        }
        return fumeHoods.stream()
                .map(fumeHood -> setEquipment(fumeHood, fumeHoodChemicalDetail, boilerCogenAgencyInfoList))
                .collect(Collectors.toList());
    }

    public FumeHoodResDTO updateFumeHood(final Long fumeHoodId, final FumeHoodReqDTO equipmentReqDTO) {
        return this.createFumeHood(equipmentReqDTO, fumeHoodId);
    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteFumeHood(final Long fumeHoodId, final boolean activeStatus, final boolean isPermanentDelete) {
        final User user = this.validatorService.validateUser();
        final String userRole = user.getRole().getRole();
        final FumeHood fumeHood;
        if (isPermanentDelete && (userRole.equalsIgnoreCase(ConstantStore.ADMIN_SUPER_USER) || userRole.equalsIgnoreCase(ConstantStore.ADMIN_USER))) {
            fumeHood = this.fumeHoodRepository.findByFumeHoodId(fumeHoodId);
            if (fumeHoodId == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            this.fumeHoodChemicalDetailRepository.deleteByFumeHood(fumeHood);
            this.fumeHoodRepository.delete(fumeHood);
            this.commonUtilService.removeEquipment(ConstantStore.FUME_HOOD, fumeHood.getBuilding(), 1L);
        } else {
            fumeHood = this.fumeHoodRepository.findByFumeHoodIdAndIsActive(fumeHoodId, true);
            if (fumeHood == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            fumeHood.setIsActive(activeStatus);
            this.fumeHoodRepository.save(fumeHood);
        }
    }

    private JobFilingInformation saveJobFilingInformation(final JobFilingInformation jobFilingInformation) {
        return this.jobFilingInformationRepository.save(jobFilingInformation);
    }

    private FumeHoodResDTO setEquipment(final FumeHood fumeHood, final List<FumeHoodChemicalDetail> chemicalDetails, final List<BoilerCogenAgencyInfo> boilerCogenAgencyInfoList) {
        final List<FumeHoodChemicalDetail> fumeHoodChemicalDetails = new LinkedList<>();
        if (!ObjectUtils.isEmpty(chemicalDetails)) {
            chemicalDetails.forEach(fumeHoodChemicalDetail -> {
                final FumeHoodChemicalDetail chemicalDetail = new FumeHoodChemicalDetail();
                chemicalDetail.setId(fumeHoodChemicalDetail.getId());
                chemicalDetail.setChemicalName(fumeHoodChemicalDetail.getChemicalName());
                chemicalDetail.setVoc(fumeHoodChemicalDetail.getVoc());
                chemicalDetail.setVolume(fumeHoodChemicalDetail.getVolume());
                chemicalDetail.setDensity(fumeHoodChemicalDetail.getDensity());
                chemicalDetail.setVocPercentage(fumeHoodChemicalDetail.getVocPercentage());
                fumeHoodChemicalDetails.add(chemicalDetail);
            });
        }
        final FumeHoodResDTO equipmentResDTO = new FumeHoodResDTO();
        equipmentResDTO.setId(fumeHood.getFumeHoodId());
        equipmentResDTO.setActiveStatus(fumeHood.getIsActive());
        equipmentResDTO.setUniqueId(fumeHood.getUniqueId());
        equipmentResDTO.setFloor(fumeHood.getFloor());
        equipmentResDTO.setLocation(fumeHood.getLocation());
        equipmentResDTO.setInstalledOn(fumeHood.getInstalledOn());
        equipmentResDTO.setMakeBy(fumeHood.getMakeBy());
        equipmentResDTO.setInstalledBy(fumeHood.getInstalledBy());
        equipmentResDTO.setManagedBy(fumeHood.getManagedBy());
        equipmentResDTO.setSerialNo(fumeHood.getSerialNo());
        equipmentResDTO.setDeviceType(ConstantStore.FUME_HOOD);
        equipmentResDTO.setManagement(!ObjectUtils.isEmpty(fumeHood.getManagement()) ? fumeHood.getManagement().getId() : null);
        equipmentResDTO.setType(fumeHood.getType());
        equipmentResDTO.setStack(fumeHood.getStack() != null ? fumeHood.getStack().getStackId() : null);
        equipmentResDTO.setHoursOfOperation(fumeHood.getHoursOfOperation());
        equipmentResDTO.setStatus(!ObjectUtils.isEmpty(fumeHood.getStatus()) ? fumeHood.getStatus().getStatusId() : null);
        equipmentResDTO.setFumeHoodChemicalDetails(fumeHoodChemicalDetails);
        equipmentResDTO.setJobFilingInformation(!ObjectUtils.isEmpty(fumeHood.getJobFilingInformation()) ? fumeHood.getJobFilingInformation() : null);
        equipmentResDTO.setAgencyInfoList(boilerCogenAgencyInfoList != null ? this.setBoilerCogenAgencyInfoDTOS(boilerCogenAgencyInfoList) : null);
        return equipmentResDTO;
    }


    private void saveAgency(final FumeHoodReqDTO equipmentReqDTO, final FumeHood fumeHood) {
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
                boilerCogenAgencyInfo.setFumeHood(fumeHood);
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
}
