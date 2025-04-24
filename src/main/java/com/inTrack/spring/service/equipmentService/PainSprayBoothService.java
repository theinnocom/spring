package com.inTrack.spring.service.equipmentService;

import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.FumeHoodReqDTO;
import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.dto.requestDTO.PaintSprayBoothReqDTO;
import com.inTrack.spring.dto.responseDTO.BoilerCogenAgencyInfoDTO;
import com.inTrack.spring.dto.responseDTO.EquipmentResDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.User;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.FumeHood;
import com.inTrack.spring.entity.equipmentEntity.PaintSprayBooth;
import com.inTrack.spring.entity.equipmentEntity.Stack;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.BoilerCogenAgency;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.InspectionType;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.AgencyRepo.BoilerCogenAgencyInfoRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.BoilerCogenAgencyRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.InspectionTypeRepository;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
import com.inTrack.spring.repository.equipmentRepository.PainSprayBoothRepository;
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

/**
 * @author vijayan
 */

@Service
@RequiredArgsConstructor
public class PainSprayBoothService {

    private final PainSprayBoothRepository painSprayBoothRepository;
    private final ValidatorService validatorService;
    private final BuildingRepository buildingRepository;
    private final CommonUtilService commonUtilService;
    private final JobFilingInformationRepository jobFilingInformationRepository;
    private final CommonEquipmentService commonEquipmentService;
    private final BoilerCogenAgencyInfoRepository boilerCogenAgencyInfoRepository;
    private final StackRepository stackRepository;
    private final BoilerCogenAgencyRepository boilerCogenAgencyRepository;
    private final InspectionTypeRepository inspectionTypeRepository;

    @Transactional(rollbackOn = Exception.class)
    public EquipmentResDTO createPaintSprayBooth(final PaintSprayBoothReqDTO equipmentReqDTO, final Long painSprayId) {
        final PaintSprayBooth painSprayBooth;
        final Building building = this.buildingRepository.findByBuildingId(equipmentReqDTO.getBuildingId());
        if (painSprayId != null) {
            painSprayBooth = this.painSprayBoothRepository.findByPainSprayId(painSprayId);
        } else {
            final Long uniqueIdCount = this.painSprayBoothRepository.countByUniqueId(equipmentReqDTO.getUniqueId());
            final Long jobFillingIdCount = this.jobFilingInformationRepository.countByJobNumber(equipmentReqDTO.getJobFilingInformationReqDTO().getJobNumber());
            if (uniqueIdCount > 0 && jobFillingIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.UNIQUE_ID_AND_JOB_FILLING_ID_EXISTS);
            } else if (uniqueIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.UNIQUE_ID_EXISTS);
            } else if (jobFillingIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.JOB_FILLING_ID_EXISTS);
            }
            painSprayBooth = new PaintSprayBooth();
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            painSprayBooth.setUniqueId(equipmentReqDTO.getUniqueId());
            painSprayBooth.setBuilding(building);
            if (equipmentReqDTO.getJobFilingInformationReqDTO() != null) {
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
                painSprayBooth.setJobFilingInformation(this.saveJobFilingInformation(jobFilingInformation));
            }
            this.commonUtilService.saveEquipment(ConstantStore.PAIN_SPRAY_BOOTH, building, 1L);
            this.commonEquipmentService.createEquipmentFloorRelation(equipmentReqDTO.getFloor(), ConstantStore.PAIN_SPRAY_BOOTH, building);
        }
        painSprayBooth.setManagement(!ObjectUtils.isEmpty(equipmentReqDTO.getManagement()) ? new ManagementType().setId(equipmentReqDTO.getManagement()) : null);
        painSprayBooth.setApplicationId(equipmentReqDTO.getApplicationId());
        painSprayBooth.setFloor(equipmentReqDTO.getFloor());
        painSprayBooth.setLocation(equipmentReqDTO.getLocation());
        painSprayBooth.setInstalledOn(equipmentReqDTO.getInstalledOn());
        painSprayBooth.setMakeBy(equipmentReqDTO.getMakeBy());
        painSprayBooth.setInstalledBy(equipmentReqDTO.getInstalledBy());
        painSprayBooth.setManagedBy(equipmentReqDTO.getManagedBy());
        painSprayBooth.setSerialNo(equipmentReqDTO.getSerialNo());
        painSprayBooth.setMake(equipmentReqDTO.getMake());
        painSprayBooth.setManagementNote(equipmentReqDTO.getManagementNote());
        painSprayBooth.setModel(equipmentReqDTO.getModel());
        painSprayBooth.setSerialNumber(equipmentReqDTO.getSerialNumber());
        if (!ObjectUtils.isEmpty(equipmentReqDTO.getStackExhausting())) {
            final Stack stack = this.stackRepository.findByStackId(equipmentReqDTO.getStackExhausting());
            if (stack == null) {
                throw new ValidationError(ApplicationMessageStore.STACK_NOT_FOUND);
            }
            painSprayBooth.setStack(stack);
        }
        painSprayBooth.setHoursOfOperationPerDay(equipmentReqDTO.getHoursOfOperationPerDay());
        painSprayBooth.setDaysOfOperationPerWeek(equipmentReqDTO.getDaysOfOperationPerWeek());
        painSprayBooth.setTotalVocContentPerMonth(equipmentReqDTO.getTotalVocContentPerMonth());
        painSprayBooth.setTotalAnnualVocContent(equipmentReqDTO.getTotalAnnualVocContent());
        painSprayBooth.setCapOnVocContent(equipmentReqDTO.isCapOnVocContent());
        painSprayBooth.setPaintLbPerGal(equipmentReqDTO.getPaintLbPerGal());
        painSprayBooth.setSolventLbPerGal(equipmentReqDTO.getSolventLbPerGal());
        painSprayBooth.setInkLbPerGal(equipmentReqDTO.getInkLbPerGal());
        painSprayBooth.setMonthlyLimitGalPerMonth(equipmentReqDTO.getMonthlyLimitGalPerMonth());
        painSprayBooth.setComments(equipmentReqDTO.getComments());
        painSprayBooth.setStatus(!ObjectUtils.isEmpty(equipmentReqDTO.getStatus()) ? new ElevatorStatus().setStatusId(equipmentReqDTO.getStatus()) : null);
        final PaintSprayBooth paintSprayBooth = this.painSprayBoothRepository.save(painSprayBooth);
        this.saveAgency(equipmentReqDTO, paintSprayBooth);
        return this.setEquipment(paintSprayBooth, null);
    }

    public List<EquipmentResDTO> getPaintSprayBooth(final Long painSprayId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        final List<PaintSprayBooth> painSprayBooths = new LinkedList<>();
        final List<BoilerCogenAgencyInfo> boilerCogenAgencyInfoList = new LinkedList<>();
        if (equipmentDetailsReqVO.getEquipmentType() != null && !equipmentDetailsReqVO.getEquipmentType().equalsIgnoreCase(ConstantStore.PAIN_SPRAY_BOOTH)) {
            throw new ValidationError(ApplicationMessageStore.EQUIPMENT_TYPE_NOT_MATCH);
        }
        if (painSprayId != null) {
            final PaintSprayBooth paintSprayBooth = this.painSprayBoothRepository.findByPainSprayId(painSprayId);
            painSprayBooths.add(paintSprayBooth);
            final List<BoilerCogenAgencyInfo> boilerCogenAgencyInfos = this.boilerCogenAgencyInfoRepository.findByPaintSprayBooth(paintSprayBooth);
            boilerCogenAgencyInfos.forEach(boilerCogenAgencyInfo -> {
                boilerCogenAgencyInfo.setPaintSprayBooth(null);
                boilerCogenAgencyInfoList.add(boilerCogenAgencyInfo);
            });
        } else {
            final Building building = this.buildingRepository.findByBuildingId(equipmentDetailsReqVO.getBuildingId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            if (equipmentDetailsReqVO.getFloor() == null) {
                painSprayBooths.addAll(this.painSprayBoothRepository.findAllByBuilding(building));
            } else {
                painSprayBooths.addAll(this.painSprayBoothRepository.findAllByBuildingAndFloor(building, equipmentDetailsReqVO.getFloor()));
            }
        }
        return painSprayBooths.stream().map(paintSprayBooth -> this.setEquipment(paintSprayBooth, boilerCogenAgencyInfoList)).toList();
    }

    public EquipmentResDTO updatePaintSprayBooth(final Long painSprayId, final PaintSprayBoothReqDTO equipmentReqDTO) {
        return this.createPaintSprayBooth(equipmentReqDTO, painSprayId);
    }

    public void deletePaintSprayBooth(final Long painSprayId, final boolean activeStatus, final boolean isPermanentDelete) {
        final User user = this.validatorService.validateUser();
        final String userRole = user.getRole().getRole();
        final PaintSprayBooth painSprayBooth;
        if (isPermanentDelete && (userRole.equalsIgnoreCase(ConstantStore.ADMIN_SUPER_USER) || userRole.equalsIgnoreCase(ConstantStore.ADMIN_USER))) {
            painSprayBooth = this.painSprayBoothRepository.findByPainSprayId(painSprayId);
            if (painSprayId == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            this.painSprayBoothRepository.delete(painSprayBooth);
            this.commonUtilService.removeEquipment(ConstantStore.PAIN_SPRAY_BOOTH, painSprayBooth.getBuilding(), 1L);
        } else {
            painSprayBooth = this.painSprayBoothRepository.findByPainSprayIdAndIsActive(painSprayId, true);
            if (painSprayBooth == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            painSprayBooth.setIsActive(activeStatus);
            this.painSprayBoothRepository.save(painSprayBooth);
        }
    }

    private JobFilingInformation saveJobFilingInformation(final JobFilingInformation jobFilingInformation) {
        return this.jobFilingInformationRepository.save(jobFilingInformation);
    }

    private EquipmentResDTO setEquipment(final PaintSprayBooth painSprayBooth, List<BoilerCogenAgencyInfo> boilerCogenAgencyInfoList) {
        final EquipmentResDTO equipmentResDTO = new EquipmentResDTO();
        equipmentResDTO.setId(painSprayBooth.getPainSprayId());
        equipmentResDTO.setFloor(painSprayBooth.getFloor());
        equipmentResDTO.setLocation(painSprayBooth.getLocation());
        equipmentResDTO.setInstalledOn(painSprayBooth.getInstalledOn());
        equipmentResDTO.setMakeBy(painSprayBooth.getMakeBy());
        equipmentResDTO.setInstalledBy(painSprayBooth.getInstalledBy());
        equipmentResDTO.setManagedBy(painSprayBooth.getManagedBy());
        equipmentResDTO.setSerialNo(painSprayBooth.getSerialNo());
        equipmentResDTO.setDeviceType(ConstantStore.PAIN_SPRAY_BOOTH);
        equipmentResDTO.setApplicationId(painSprayBooth.getApplicationId());
        equipmentResDTO.setJobFilingInformation(painSprayBooth.getJobFilingInformation());
        equipmentResDTO.setMake(painSprayBooth.getMake());
        equipmentResDTO.setManagementNote(painSprayBooth.getManagementNote());
        equipmentResDTO.setModel(painSprayBooth.getModel());
        equipmentResDTO.setSerialNumber(painSprayBooth.getSerialNumber());
        equipmentResDTO.setStackExhausting(!ObjectUtils.isEmpty(painSprayBooth.getStack()) ? painSprayBooth.getStack().getStackId() : null);
        equipmentResDTO.setManagement(!ObjectUtils.isEmpty(painSprayBooth.getManagement()) ? painSprayBooth.getManagement().getId() : null);
        equipmentResDTO.setStatus(!ObjectUtils.isEmpty(painSprayBooth.getStatus()) ? painSprayBooth.getStatus().getStatusId() : null);
        equipmentResDTO.setHoursOfOperationPerDay(painSprayBooth.getHoursOfOperationPerDay());
        equipmentResDTO.setDaysOfOperationPerWeek(painSprayBooth.getDaysOfOperationPerWeek());
        equipmentResDTO.setTotalVocContentPerMonth(painSprayBooth.getTotalVocContentPerMonth());
        equipmentResDTO.setTotalAnnualVocContent(painSprayBooth.getTotalAnnualVocContent());
        equipmentResDTO.setCapOnVocContent(painSprayBooth.isCapOnVocContent());
        equipmentResDTO.setPaintLbPerGal(painSprayBooth.getPaintLbPerGal());
        equipmentResDTO.setSolventLbPerGal(painSprayBooth.getSolventLbPerGal());
        equipmentResDTO.setInkLbPerGal(painSprayBooth.getInkLbPerGal());
        equipmentResDTO.setMonthlyLimitGalPerMonth(painSprayBooth.getMonthlyLimitGalPerMonth());
        equipmentResDTO.setComments(painSprayBooth.getComments());
        equipmentResDTO.setBuildingId(painSprayBooth.getBuilding().getBuildingId());
        equipmentResDTO.setMake(painSprayBooth.getMake());
        equipmentResDTO.setModel(painSprayBooth.getModel());
        equipmentResDTO.setUniqueId(painSprayBooth.getUniqueId());
        equipmentResDTO.setAgencyInfoList(!ObjectUtils.isEmpty(boilerCogenAgencyInfoList) ? this.setBoilerCogenAgencyInfoDTOS(boilerCogenAgencyInfoList) : null);
        return equipmentResDTO;
    }


    private void saveAgency(final PaintSprayBoothReqDTO equipmentReqDTO, final PaintSprayBooth paintSprayBooth) {
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
                boilerCogenAgencyInfo.setPaintSprayBooth(paintSprayBooth);
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
