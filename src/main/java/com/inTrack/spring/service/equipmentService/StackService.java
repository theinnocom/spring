package com.inTrack.spring.service.equipmentService;

import com.inTrack.spring.dto.common.CoolingTowerAgencyInfoDTO;
import com.inTrack.spring.dto.common.IncineratorCrematoriesAgencyInfoDTO;
import com.inTrack.spring.dto.common.StackAgencyInfoDTO;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.IncineratorCrematoriesReqDTO;
import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.dto.requestDTO.StackReqDTO;
import com.inTrack.spring.dto.responseDTO.StackResDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.User;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.IncineratorCrematories;
import com.inTrack.spring.entity.equipmentEntity.Stack;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.CoolingTowerAgencyInfo;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.IncineratorCrematoriesAgencyInfo;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.StackAgencyInfo;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.IncineratorCrematoriesAgency;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.ParameterType;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.StackAgency;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.AgencyRepo.StackAgencyInfoRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.StackAgencyRepository;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
import com.inTrack.spring.repository.equipmentRepository.StackRepository;
import com.inTrack.spring.UtilService.CommonUtilService;
import com.inTrack.spring.service.ValidatorService;
import com.inTrack.spring.store.ApplicationMessageStore;
import com.inTrack.spring.store.ConstantStore;
import jakarta.persistence.ManyToOne;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

@Service
public class StackService {

    @Autowired
    private StackRepository stackRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private ValidatorService validatorService;

    @Autowired
    private CommonUtilService commonUtilService;

    @Autowired
    private JobFilingInformationRepository jobFilingInformationRepository;

    @Autowired
    private CommonEquipmentService commonEquipmentService;

    @Autowired
    private StackAgencyInfoRepository stackAgencyInfoRepository;

    @Autowired
    private StackAgencyRepository stackAgencyRepository;

    @Transactional(rollbackOn = Exception.class)
    public StackResDTO createStack(final StackReqDTO equipmentReqDTO, final Long stackId) {
        final Stack stack;
        final Building building = this.buildingRepository.findByBuildingId(equipmentReqDTO.getBuildingId());
        if (stackId != null) {
            stack = this.stackRepository.findByStackId(stackId);
        } else {
            final Long uniqueIdCount = this.stackRepository.countByUniqueId(equipmentReqDTO.getUniqueId());
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
            stack = new Stack();
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            stack.setBuilding(building);
            stack.setUniqueId(equipmentReqDTO.getUniqueId());
            this.commonUtilService.saveEquipment(ConstantStore.STACK, building, 1L);
            this.commonEquipmentService.createEquipmentFloorRelation(equipmentReqDTO.getFloor(), ConstantStore.STACK, building);
            stack.setDeviceType(ConstantStore.STACK);
        }
        if (equipmentReqDTO.getJobFilingInformationReqDTO() != null) {
            if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformationReqDTO().getJobFilingId())) {
                this.commonEquipmentService.saveJobFiling(equipmentReqDTO.getJobFilingInformationReqDTO());
            } else {
                stack.setJobFilingInformation(this.saveJobFilingInformation(this.setJobFilingInfo(equipmentReqDTO.getJobFilingInformationReqDTO())));
            }
        }
        stack.setStatus(!ObjectUtils.isEmpty(equipmentReqDTO.getStatus()) ? new ElevatorStatus().setStatusId(equipmentReqDTO.getStatus()) : null);
        stack.setManagementNote(equipmentReqDTO.getManagementNote());
        stack.setLandmark(equipmentReqDTO.getLandmark());
        stack.setDisconnectedOn(equipmentReqDTO.getDisconnectedOn());
        stack.setManagement(!ObjectUtils.isEmpty(equipmentReqDTO.getManagement()) ? new ManagementType().setId(equipmentReqDTO.getManagement()) : null);
        stack.setApplicationId(equipmentReqDTO.getApplicationId());
        stack.setHeightFeet(equipmentReqDTO.getHeightFeet());
        stack.setDiameterInches(equipmentReqDTO.getDiameterInches());
        stack.setNumberOfEquipmentsConnected(equipmentReqDTO.getNumberOfEquipmentsConnected());
        stack.setTotalCapacityOfEquipments(equipmentReqDTO.getTotalCapacityOfEquipments());
        stack.setOilFuel(equipmentReqDTO.isOilFuel());
        stack.setGasFuel(equipmentReqDTO.isGasFuel());
        stack.setFlowRate(equipmentReqDTO.getFlowRate());
        stack.setExhaustTemperature(equipmentReqDTO.getExhaustTemperature());
        stack.setVelocity(equipmentReqDTO.getVelocity());
        stack.setNote(equipmentReqDTO.getNote());
        stack.setFloor(equipmentReqDTO.getFloor());
        stack.setLocation(equipmentReqDTO.getLocation());
        stack.setInstalledOn(equipmentReqDTO.getInstalledOn());
        stack.setMake(equipmentReqDTO.getMake());
        stack.setInstalledBy(equipmentReqDTO.getInstalledBy());
        stack.setManagedBy(equipmentReqDTO.getManagedBy());
        stack.setSerialNo(equipmentReqDTO.getSerialNo());
        final Stack saveStack = this.stackRepository.save(stack);
        this.saveAgency(equipmentReqDTO, saveStack);
        return this.setEquipment(saveStack, null);
    }

    public List<StackResDTO> getStack(final Long stackId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        final List<Stack> stacks = new LinkedList<>();
        final List<StackAgencyInfo> stackAgencyInfoList = new LinkedList<>();
        if (equipmentDetailsReqVO.getEquipmentType() != null && !equipmentDetailsReqVO.getEquipmentType().equalsIgnoreCase(ConstantStore.STACK)) {
            throw new ValidationError(ApplicationMessageStore.EQUIPMENT_TYPE_NOT_MATCH);
        }
        if (stackId != null) {
            final Stack stack = this.stackRepository.findByStackId(stackId);
            final List<StackAgencyInfo> agencyInfoList = this.stackAgencyInfoRepository.findByStack(stack);
            agencyInfoList.forEach(stackAgencyInfo -> {
                stackAgencyInfo.setStack(null);
                stackAgencyInfoList.add(stackAgencyInfo);
            });
            stacks.add(stack);
        } else {
            final Building building = this.buildingRepository.findByBuildingId(equipmentDetailsReqVO.getBuildingId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            if (equipmentDetailsReqVO.getFloor() == null) {
                stacks.addAll(this.stackRepository.findAllByBuilding(building));
            } else {
                stacks.addAll(this.stackRepository.findAllByBuildingAndFloor(building, equipmentDetailsReqVO.getFloor()));
            }
        }
        return stacks.stream().map(stack -> this.setEquipment(stack, stackAgencyInfoList)).toList();
    }

    public StackResDTO updateStack(final Long stackId, final StackReqDTO equipmentReqDTO) {
        return this.createStack(equipmentReqDTO, stackId);
    }

    public void deleteStack(final Long stackId, final boolean activeStatus, final boolean isPermanentDelete) {
        final User user = this.validatorService.validateUser();
        final String userRole = user.getRole().getRole();
        final Stack stack;
        if (isPermanentDelete && (userRole.equalsIgnoreCase(ConstantStore.ADMIN_SUPER_USER) || userRole.equalsIgnoreCase(ConstantStore.ADMIN_USER))) {
            stack = this.stackRepository.findByStackId(stackId);
            if (stack == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            this.stackRepository.delete(stack);
            this.commonUtilService.removeEquipment(ConstantStore.STACK, stack.getBuilding(), 1L);
        } else {
            stack = this.stackRepository.findByStackIdAndIsActive(stackId, true);
            if (stack == null) {
                throw new ValidationError(ApplicationMessageStore.BOILER_NOT_FOUND);
            }
            stack.setIsActive(activeStatus);
            this.stackRepository.save(stack);
        }
    }

    private JobFilingInformation saveJobFilingInformation(final JobFilingInformation jobFilingInformation) {
        return this.jobFilingInformationRepository.save(jobFilingInformation);
    }

    private StackResDTO setEquipment(final Stack stack, List<StackAgencyInfo> stackAgencyInfoList) {
        final StackResDTO equipmentResDTO = new StackResDTO();
        equipmentResDTO.setStackId(stack.getStackId());
        equipmentResDTO.setUniqueId(stack.getUniqueId());
        equipmentResDTO.setFloor(stack.getFloor());
        equipmentResDTO.setLocation(stack.getLocation());
        equipmentResDTO.setInstalledOn(stack.getInstalledOn());
        equipmentResDTO.setMake(stack.getMake());
        equipmentResDTO.setInstalledBy(stack.getInstalledBy());
        equipmentResDTO.setManagedBy(stack.getManagedBy());
        equipmentResDTO.setSerialNo(stack.getSerialNo());
        equipmentResDTO.setDeviceType(stack.getDeviceType());
        equipmentResDTO.setStatus(!ObjectUtils.isEmpty(stack.getStatus()) ? stack.getStatus().getStatusId() : null);
        equipmentResDTO.setManagementNote(stack.getManagementNote());
        equipmentResDTO.setLandmark(stack.getLandmark());
        equipmentResDTO.setDisconnectedOn(stack.getDisconnectedOn());
        equipmentResDTO.setManagement(!ObjectUtils.isEmpty(stack.getManagement()) ? stack.getManagement().getId() : null);
        equipmentResDTO.setApplicationId(stack.getApplicationId());
        equipmentResDTO.setHeightFeet(stack.getHeightFeet());
        equipmentResDTO.setDiameterInches(stack.getDiameterInches());
        equipmentResDTO.setNumberOfEquipmentsConnected(stack.getNumberOfEquipmentsConnected());
        equipmentResDTO.setTotalCapacityOfEquipments(stack.getTotalCapacityOfEquipments());
        equipmentResDTO.setOilFuel(stack.isOilFuel());
        equipmentResDTO.setGasFuel(stack.isGasFuel());
        equipmentResDTO.setFlowRate(stack.getFlowRate());
        equipmentResDTO.setExhaustTemperature(stack.getExhaustTemperature());
        equipmentResDTO.setVelocity(stack.getVelocity());
        equipmentResDTO.setNote(stack.getNote());
        equipmentResDTO.setJobFilingInformation(!ObjectUtils.isEmpty(stack.getJobFilingInformation()) ? stack.getJobFilingInformation() : null);
        equipmentResDTO.setBuildingId(stack.getBuilding().getBuildingId());
        equipmentResDTO.setAgencyInfoList(!ObjectUtils.isEmpty(stackAgencyInfoList) ? this.setStackAgencyInfo(stackAgencyInfoList) : null);
        return equipmentResDTO;
    }

    private List<StackAgencyInfoDTO> setStackAgencyInfo(List<StackAgencyInfo> stackAgencyInfoList) {
        final List<StackAgencyInfoDTO> stackAgencyInfoDTOS = new LinkedList<>();
        stackAgencyInfoList.forEach(stackAgencyInfo -> {
            final StackAgencyInfoDTO stackAgencyInfoDTO = new StackAgencyInfoDTO();
            stackAgencyInfoDTO.setId(stackAgencyInfo.getId());
            stackAgencyInfoDTO.setStackAgency(stackAgencyInfo.getId());
            stackAgencyInfoDTO.setLastTestDate(stackAgencyInfo.getLastTestDate());
            stackAgencyInfoDTO.setTestedBy(stackAgencyInfo.getTestedBy());
            stackAgencyInfoDTO.setNextTestDate(stackAgencyInfo.getNextTestDate());
            stackAgencyInfoDTO.setMethod9TestConducted(stackAgencyInfo.getMethod9TestConducted());
            stackAgencyInfoDTO.setOpacityPermissibleLimit(stackAgencyInfo.getOpacityPermissibleLimit());
            stackAgencyInfoDTO.setNote(stackAgencyInfo.getNote());
            stackAgencyInfoDTOS.add(stackAgencyInfoDTO);
        });
        return stackAgencyInfoDTOS;
    }


    private void saveAgency(final StackReqDTO equipmentReqDTO, final Stack stack) {
        if (equipmentReqDTO.getAgencyInfoList() != null) {
            final List<StackAgencyInfoDTO> agencyInfoList = equipmentReqDTO.getAgencyInfoList();
            final List<StackAgencyInfo> stackAgencyInfoList = new LinkedList<>();
            for (StackAgencyInfoDTO agencyInfoDTO : agencyInfoList) {
                StackAgencyInfo stackAgencyInfo;
                if (agencyInfoDTO.getId() != null) {
                    stackAgencyInfo = this.stackAgencyInfoRepository.findById(agencyInfoDTO.getId())
                            .orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
                } else {
                    stackAgencyInfo = new StackAgencyInfo();
                }
                for (final Field field : StackAgencyInfo.class.getDeclaredFields()) {
                    field.setAccessible(true);
                    if ((agencyInfoDTO.getId() == null && field.getName().equalsIgnoreCase("id")) || field.isAnnotationPresent(ManyToOne.class)) {
                        continue;
                    }
                    try {
                        final Object value = this.getFieldValue(agencyInfoDTO, field.getName());
                        if (value != null) {
                            field.set(stackAgencyInfo, value);
                        }
                    } catch (final NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                        throw new ValidationError("Error setting field: " + field.getName());
                    }
                }
                stackAgencyInfo.setStack(stack);
                this.stackAgency(agencyInfoDTO, stackAgencyInfo);
                stackAgencyInfoList.add(stackAgencyInfo);
            }
            this.stackAgencyInfoRepository.saveAll(stackAgencyInfoList);
        }
    }

    private void stackAgency(final StackAgencyInfoDTO agencyInfoDTO, final StackAgencyInfo stackAgencyInfo) {
        final Long stackAgencyId = agencyInfoDTO.getStackAgency();
        if (stackAgencyId != null) {
            final StackAgency stackAgency = this.stackAgencyRepository.findById(stackAgencyId)
                    .orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
            stackAgencyInfo.setStackAgency(stackAgency);
        }
    }

    private Object getFieldValue(final StackAgencyInfoDTO agencyInfoDTO, final String fieldName) throws NoSuchFieldException, IllegalAccessException {
        final Field field = StackAgencyInfoDTO.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(agencyInfoDTO);
    }

    private List<CoolingTowerAgencyInfoDTO> setCoolingTowerAgencyInfo(final List<CoolingTowerAgencyInfo> coolingTowerAgencyInfos) {
        final List<CoolingTowerAgencyInfoDTO> coolingTowerAgencyInfoDTOS = new LinkedList<>();
        coolingTowerAgencyInfos.forEach(coolingTowerAgencyInfo -> {
            CoolingTowerAgencyInfoDTO coolingTowerAgencyInfoDTO = new CoolingTowerAgencyInfoDTO();
            coolingTowerAgencyInfoDTO.setId(coolingTowerAgencyInfo.getId());
            coolingTowerAgencyInfoDTO.setLastCertificationDate(coolingTowerAgencyInfo.getLastCertificationDate());
            coolingTowerAgencyInfoDTO.setNextCertificationDate(coolingTowerAgencyInfo.getNextCertificationDate());
            coolingTowerAgencyInfoDTO.setCertifiedBy(coolingTowerAgencyInfo.getCertifiedBy());
            coolingTowerAgencyInfoDTO.setLastInspectionDate(coolingTowerAgencyInfo.getLastInspectionDate());
            coolingTowerAgencyInfoDTO.setNextInspectionDate(coolingTowerAgencyInfo.getNextInspectionDate());
            coolingTowerAgencyInfoDTO.setInspectedBy(coolingTowerAgencyInfo.getInspectedBy());
            coolingTowerAgencyInfoDTO.setLastTestDate(coolingTowerAgencyInfo.getLastTestDate());
            coolingTowerAgencyInfoDTO.setNextTestDate(coolingTowerAgencyInfo.getNextTestDate());
            coolingTowerAgencyInfoDTO.setTestedBy(coolingTowerAgencyInfo.getTestedBy());
            coolingTowerAgencyInfoDTO.setNote(coolingTowerAgencyInfo.getNote());
            coolingTowerAgencyInfoDTO.setCoolingTowerAgency(!ObjectUtils.isEmpty(coolingTowerAgencyInfo.getCoolingTowerAgency()) ? coolingTowerAgencyInfo.getId() : null);
            coolingTowerAgencyInfoDTOS.add(coolingTowerAgencyInfoDTO);
        });
        return coolingTowerAgencyInfoDTOS;
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

    private List<IncineratorCrematoriesAgencyInfoDTO> setIncineratorCrematoriesAgencyDTO(List<IncineratorCrematoriesAgencyInfo> agencyInfos) {
        final List<IncineratorCrematoriesAgencyInfoDTO> incineratorCrematoriesAgencyInfoDTOS = new LinkedList<>();
        agencyInfos.forEach(agencyInfo -> {
            final IncineratorCrematoriesAgencyInfoDTO incineratorCrematoriesAgencyInfo = new IncineratorCrematoriesAgencyInfoDTO();
            incineratorCrematoriesAgencyInfo.setId(agencyInfo.getId());
            incineratorCrematoriesAgencyInfo.setDepNumber(agencyInfo.getDepNumber());
            incineratorCrematoriesAgencyInfo.setIssueDate(agencyInfo.getIssueDate());
            incineratorCrematoriesAgencyInfo.setExpirationDate(agencyInfo.getExpirationDate());
            incineratorCrematoriesAgencyInfo.setStatus(agencyInfo.getStatus());
            incineratorCrematoriesAgencyInfo.setDecNumber(agencyInfo.getDecNumber());
            incineratorCrematoriesAgencyInfo.setDecIssueDate(agencyInfo.getDecIssueDate());
            incineratorCrematoriesAgencyInfo.setDecExpirationDate(agencyInfo.getDecExpirationDate());
            incineratorCrematoriesAgencyInfo.setIsSolidWastePermitRequired(agencyInfo.getIsSolidWastePermitRequired());
            incineratorCrematoriesAgencyInfo.setStackLastTestDate(agencyInfo.getStackLastTestDate());
            incineratorCrematoriesAgencyInfo.setStackNextTestDate(agencyInfo.getStackNextTestDate());
            incineratorCrematoriesAgencyInfo.setStackTestProtocolSubmitted(agencyInfo.isStackTestProtocolSubmitted());
            incineratorCrematoriesAgencyInfo.setParameterTypes(!ObjectUtils.isEmpty(agencyInfo.getParameterTypes()) ? agencyInfo.getParameterTypes().getId() : null);
            incineratorCrematoriesAgencyInfo.setStackTestPassed(agencyInfo.isStackTestPassed());
            incineratorCrematoriesAgencyInfo.setTestConductedBy(agencyInfo.getTestConductedBy());
            incineratorCrematoriesAgencyInfo.setNote(agencyInfo.getNote());
            incineratorCrematoriesAgencyInfo.setIncineratorCrematoriesAgency(!ObjectUtils.isEmpty(agencyInfo.getIncineratorCrematoriesAgency()) ? agencyInfo.getIncineratorCrematoriesAgency().getId() : null);
            incineratorCrematoriesAgencyInfoDTOS.add(incineratorCrematoriesAgencyInfo);
        });
        return incineratorCrematoriesAgencyInfoDTOS;
    }
}
