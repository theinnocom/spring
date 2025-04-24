package com.inTrack.spring.service.equipmentService;

import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.dto.requestDTO.LandFillReqDTO;
import com.inTrack.spring.dto.requestDTO.PrintingPressReqDTO;
import com.inTrack.spring.dto.responseDTO.BoilerCogenAgencyInfoDTO;
import com.inTrack.spring.dto.responseDTO.PrintingPressResDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.LandFill;
import com.inTrack.spring.entity.equipmentEntity.PrintingPress;
import com.inTrack.spring.entity.User;
import com.inTrack.spring.entity.equipmentEntity.Stack;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.BoilerCogenAgency;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.InspectionType;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.AgencyRepo.BoilerCogenAgencyInfoRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.BoilerCogenAgencyRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.InspectionTypeRepository;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.equipmentRepository.PrintingPressRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
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

@Service
@RequiredArgsConstructor
public class PrintingPressService {

    private final PrintingPressRepository printingPressRepository;
    private final ValidatorService validatorService;
    private final BuildingRepository buildingRepository;
    private final CommonUtilService commonUtilService;
    private final JobFilingInformationRepository jobFilingInformationRepository;
    private final CommonEquipmentService commonEquipmentService;
    private final BoilerCogenAgencyInfoRepository boilerCogenAgencyInfoRepository;
    private final BoilerCogenAgencyRepository boilerCogenAgencyRepository;
    private final InspectionTypeRepository inspectionTypeRepository;
    private final StackRepository stackRepository;

    @Transactional(rollbackOn = Exception.class)
    public PrintingPressResDTO createPrintingPress(final PrintingPressReqDTO equipmentReqDTO, final Long printerId) {
        final PrintingPress printingPress;
        final Building building = this.buildingRepository.findByBuildingId(equipmentReqDTO.getBuildingId());
        if (printerId != null) {
            printingPress = this.printingPressRepository.findByPrinterId(printerId);
        } else {
            printingPress = new PrintingPress();
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            printingPress.setBuilding(building);
            printingPress.setUniqueId(equipmentReqDTO.getUniqueId());
            Long jobFillingIdCount = 0L;
            if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformationReqDTO())) {
                jobFillingIdCount = this.jobFilingInformationRepository.countByJobNumber(equipmentReqDTO.getJobFilingInformationReqDTO().getJobNumber());
            }
            if (jobFillingIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.JOB_FILLING_ID_EXISTS);
            }
            if (!ObjectUtils.isEmpty(equipmentReqDTO.getStack())) {
                final Stack stack = this.stackRepository.findByStackId(equipmentReqDTO.getStack());
                if (stack == null) {
                    throw new ValidationError(ApplicationMessageStore.STACK_NOT_FOUND);
                }
                printingPress.setStack(stack);
            }
            this.commonUtilService.saveEquipment(ConstantStore.PRINTING_PRESS, building, 1L);
            this.commonEquipmentService.createEquipmentFloorRelation(equipmentReqDTO.getFloor(), ConstantStore.PRINTING_PRESS, building);
        }
        if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformationReqDTO())) {
            if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformationReqDTO().getJobFilingId())) {
                this.commonEquipmentService.saveJobFiling(equipmentReqDTO.getJobFilingInformationReqDTO());
            } else {
                printingPress.setJobFilingInformation(this.saveJobFilingInformation(this.setJobFilingInfo(equipmentReqDTO.getJobFilingInformationReqDTO())));
            }
        }
        printingPress.setFloor(equipmentReqDTO.getFloor());
        printingPress.setLocation(equipmentReqDTO.getLocation());
        printingPress.setInstalledOn(equipmentReqDTO.getInstalledOn());
        printingPress.setMake(equipmentReqDTO.getMake());
        printingPress.setInstalledBy(equipmentReqDTO.getInstalledBy());
        printingPress.setManagedBy(equipmentReqDTO.getManagedBy());
        printingPress.setSerialNo(equipmentReqDTO.getSerialNo());
        printingPress.setApplicationId(equipmentReqDTO.getApplicationId());
        printingPress.setTotalVOC(equipmentReqDTO.getTotalVOC());
        printingPress.setStatus(!ObjectUtils.isEmpty(equipmentReqDTO.getStatus()) ? new ElevatorStatus().setStatusId(equipmentReqDTO.getStatus()) : null);
        printingPress.setManagement(!ObjectUtils.isEmpty(equipmentReqDTO.getManagement()) ? new ManagementType().setId(equipmentReqDTO.getManagement()) : null);
        printingPress.setStackExhaustSearchButton(equipmentReqDTO.getStackExhaustSearchButton());
        printingPress.setStackExhaustHeight(equipmentReqDTO.getStackExhaustHeight());
        printingPress.setStackExhaustDiameter(equipmentReqDTO.getStackExhaustDiameter());
        printingPress.setStackExhaustVelocity(equipmentReqDTO.getStackExhaustVelocity());
        printingPress.setFilterUsedType(equipmentReqDTO.getFilterUsedType());
        printingPress.setEfficiencyPercentage(equipmentReqDTO.getEfficiencyPercentage());
        printingPress.setInkOrSolventUsageCap(equipmentReqDTO.isInkOrSolventUsageCap());
        printingPress.setPermittedMaxVOC(equipmentReqDTO.getPermittedMaxVOC());
        final PrintingPress savePrintingPress = this.printingPressRepository.save(printingPress);
        this.saveAgency(equipmentReqDTO, savePrintingPress);
        return this.setEquipment(savePrintingPress, null);
    }

    public List<PrintingPressResDTO> getPrintingPress(final Long printerId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        final List<PrintingPress> printingPresses = new LinkedList<>();
        final List<BoilerCogenAgencyInfo> boilerCogenAgencyInfoList = new LinkedList<>();
        if (equipmentDetailsReqVO.getEquipmentType() != null && !equipmentDetailsReqVO.getEquipmentType().equalsIgnoreCase(ConstantStore.PRINTING_PRESS)) {
            throw new ValidationError(ApplicationMessageStore.EQUIPMENT_TYPE_NOT_MATCH);
        }
        if (printerId != null) {
            final PrintingPress printingPress = this.printingPressRepository.findByPrinterId(printerId);
            printingPresses.add(printingPress);
            final List<BoilerCogenAgencyInfo> agencyInfoList = this.boilerCogenAgencyInfoRepository.findByPrintingPress(printingPress);
            agencyInfoList.forEach(agencyInfo -> {
                agencyInfo.setPrintingPress(null);
                boilerCogenAgencyInfoList.add(agencyInfo);
            });
        } else {
            final Building building = this.buildingRepository.findByBuildingId(equipmentDetailsReqVO.getBuildingId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            if (equipmentDetailsReqVO.getFloor() == null) {
                printingPresses.addAll(this.printingPressRepository.findAllByBuilding(building));
            } else {
                printingPresses.addAll(this.printingPressRepository.findAllByBuildingAndFloor(building, equipmentDetailsReqVO.getFloor()));
            }
        }
        return printingPresses.stream().map(printingPress -> this.setEquipment(printingPress, boilerCogenAgencyInfoList)).toList();
    }

    public PrintingPressResDTO updatePrintingPress(final Long printerId, final PrintingPressReqDTO equipmentReqDTO) {
        return this.createPrintingPress(equipmentReqDTO, printerId);
    }

    public void deletePrintingPress(final Long printerId, final boolean activeStatus, final boolean isPermanentDelete) {
        final User user = this.validatorService.validateUser();
        final String userRole = user.getRole().getRole();
        final PrintingPress printingPress;
        if (isPermanentDelete && (userRole.equalsIgnoreCase(ConstantStore.ADMIN_SUPER_USER) || userRole.equalsIgnoreCase(ConstantStore.ADMIN_USER))) {
            printingPress = this.printingPressRepository.findByPrinterId(printerId);
            if (printingPress == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            this.printingPressRepository.delete(printingPress);
            this.commonUtilService.removeEquipment(ConstantStore.PRINTING_PRESS, printingPress.getBuilding(), 1L);
        } else {
            printingPress = this.printingPressRepository.findByPrinterIdAndIsActive(printerId, true);
            if (printingPress == null) {
                throw new ValidationError(ApplicationMessageStore.BOILER_NOT_FOUND);
            }
            printingPress.setIsActive(activeStatus);
            this.printingPressRepository.save(printingPress);
        }
    }

    private JobFilingInformation saveJobFilingInformation(final JobFilingInformation jobFilingInformation) {
        return this.jobFilingInformationRepository.save(jobFilingInformation);
    }

    private PrintingPressResDTO setEquipment(final PrintingPress printingPress, final List<BoilerCogenAgencyInfo> boilerCogenAgencyInfoList) {
        final PrintingPressResDTO equipmentResDTO = new PrintingPressResDTO();
        equipmentResDTO.setPrinterId(printingPress.getPrinterId());
        equipmentResDTO.setFloor(printingPress.getFloor());
        equipmentResDTO.setUniqueId(printingPress.getUniqueId());
        equipmentResDTO.setLocation(printingPress.getLocation());
        equipmentResDTO.setInstalledOn(printingPress.getInstalledOn());
        equipmentResDTO.setMake(printingPress.getMake());
        equipmentResDTO.setInstalledBy(printingPress.getInstalledBy());
        equipmentResDTO.setManagedBy(printingPress.getManagedBy());
        equipmentResDTO.setSerialNo(printingPress.getSerialNo());
        equipmentResDTO.setDeviceType(ConstantStore.PRINTING_PRESS);
        equipmentResDTO.setJobFilingInformation(printingPress.getJobFilingInformation());
        equipmentResDTO.setTotalVOC(printingPress.getTotalVOC());
        equipmentResDTO.setStatus(!ObjectUtils.isEmpty(printingPress.getStatus()) ? printingPress.getStatus().getStatusId() : null);
        equipmentResDTO.setStackExhaustSearchButton(printingPress.getStackExhaustSearchButton());
        equipmentResDTO.setStackExhaustHeight(printingPress.getStackExhaustHeight());
        equipmentResDTO.setStackExhaustDiameter(printingPress.getStackExhaustDiameter());
        equipmentResDTO.setStackExhaustVelocity(printingPress.getStackExhaustVelocity());
        equipmentResDTO.setFilterUsedType(printingPress.getFilterUsedType());
        equipmentResDTO.setEfficiencyPercentage(printingPress.getEfficiencyPercentage());
        equipmentResDTO.setInkOrSolventUsageCap(printingPress.isInkOrSolventUsageCap());
        equipmentResDTO.setPermittedMaxVOC(printingPress.getPermittedMaxVOC());
        equipmentResDTO.setStack(!ObjectUtils.isEmpty(printingPress.getStack()) ? printingPress.getStack().getStackId() : null);
        equipmentResDTO.setManagement(!ObjectUtils.isEmpty(printingPress.getManagement()) ? printingPress.getManagement().getId() : null);
        equipmentResDTO.setJobFilingInformation(!ObjectUtils.isEmpty(printingPress.getJobFilingInformation()) ? printingPress.getJobFilingInformation() : null);
        equipmentResDTO.setAgencyInfoList(!ObjectUtils.isEmpty(boilerCogenAgencyInfoList) ? this.setBoilerCogenAgencyInfoDTOS(boilerCogenAgencyInfoList) : null);
        return equipmentResDTO;
    }


    private void saveAgency(final PrintingPressReqDTO equipmentReqDTO, final PrintingPress printingPress) {
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
                boilerCogenAgencyInfo.setPrintingPress(printingPress);
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
