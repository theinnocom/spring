package com.inTrack.spring.service.equipmentService;

import com.inTrack.spring.dto.requestDTO.CogenTurbineReqDTO;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.FireAlarmDTO;
import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.dto.responseDTO.BoilerCogenAgencyInfoDTO;
import com.inTrack.spring.dto.responseDTO.CogenTurbineResDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.FuelType;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.User;
import com.inTrack.spring.entity.equipmentEntity.CogenEngineTurbine;
import com.inTrack.spring.entity.equipmentEntity.CogenTurbineDropDown.CogenPrimaryUse;
import com.inTrack.spring.entity.equipmentEntity.CogenTurbineDropDown.TypeOfControlEfficiency;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.FireAlarm;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.BoilerCogenAgency;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.InspectionType;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.AgencyRepo.BoilerCogenAgencyInfoRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.BoilerCogenAgencyRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.InspectionTypeRepository;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.equipmentRepository.CogenEngineTurbineRepository;
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
import java.util.stream.Collectors;

/**
 * @author vijayan
 */

@Service
@RequiredArgsConstructor
public class CogenEngineTurbineService {

    private final CogenEngineTurbineRepository cogenEngineTurbineRepository;
    private final ValidatorService validatorService;
    private final BuildingRepository buildingRepository;
    private final CommonUtilService commonUtilService;
    private final JobFilingInformationRepository jobFilingInformationRepository;
    private final CommonEquipmentService commonEquipmentService;
    private final BoilerCogenAgencyInfoRepository boilerCogenAgencyInfoRepository;
    private final BoilerCogenAgencyRepository boilerCogenAgencyRepository;
    private final InspectionTypeRepository inspectionTypeRepository;

    @Transactional(rollbackOn = Exception.class)
    public CogenTurbineResDTO createCogenEngineTurbine(final CogenTurbineReqDTO equipmentReqDTO, final Long engineId) {
        final CogenEngineTurbine cogenEngineTurbine;
        final Building building = this.buildingRepository.findByBuildingId(equipmentReqDTO.getBuildingId());
        if (engineId != null) {
            cogenEngineTurbine = this.cogenEngineTurbineRepository.findByEngineId(engineId);
        } else {
            cogenEngineTurbine = new CogenEngineTurbine();
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            cogenEngineTurbine.setBuilding(building);
            cogenEngineTurbine.setUniqueId(equipmentReqDTO.getUniqueId());
            final Long jobFillingIdCount = this.jobFilingInformationRepository.countByJobNumber(equipmentReqDTO.getJobFilingInformationReqDTO().getJobNumber());
            if (jobFillingIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.JOB_FILLING_ID_EXISTS);
            }
            if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformationReqDTO())) {
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
                cogenEngineTurbine.setJobFilingInformation(this.saveJobFilingInformation(jobFilingInformation));
            }
            this.commonUtilService.saveEquipment(ConstantStore.COGEN_ENGINE_TURBINE, building, 1L);
            this.commonEquipmentService.createEquipmentFloorRelation(equipmentReqDTO.getFloor(), ConstantStore.COGEN_ENGINE_TURBINE, building);
        }
        cogenEngineTurbine.setFloor(equipmentReqDTO.getFloor());
        cogenEngineTurbine.setLocation(equipmentReqDTO.getLocation());
        cogenEngineTurbine.setInstalledOn(equipmentReqDTO.getInstalledOn());
        cogenEngineTurbine.setMake(equipmentReqDTO.getMakeBy());
        cogenEngineTurbine.setInstalledBy(equipmentReqDTO.getInstalledBy());
        cogenEngineTurbine.setManagedBy(equipmentReqDTO.getManagedBy());
        cogenEngineTurbine.setSerialNo(equipmentReqDTO.getSerialNo());
        cogenEngineTurbine.setApplicationId(equipmentReqDTO.getApplicationId());
        cogenEngineTurbine.setApplicationType(equipmentReqDTO.getApplicationType());
        cogenEngineTurbine.setNumberOfIdenticalUnits(equipmentReqDTO.getNumberOfIdenticalUnits());
        cogenEngineTurbine.setPrimaryUse(!ObjectUtils.isEmpty(equipmentReqDTO.getPrimaryUse()) ? new CogenPrimaryUse().setId(equipmentReqDTO.getPrimaryUse()) : null);
        cogenEngineTurbine.setEngineHasControlSystem(equipmentReqDTO.isEngineHasControlSystem());
        cogenEngineTurbine.setTypeOfControlEfficiency(!ObjectUtils.isEmpty(equipmentReqDTO.getTypeOfControlEfficiency()) ? new TypeOfControlEfficiency().setId(equipmentReqDTO.getTypeOfControlEfficiency()) : null);
        cogenEngineTurbine.setModel(equipmentReqDTO.getModel());
        cogenEngineTurbine.setSerialNumber(equipmentReqDTO.getSerialNumber());
        cogenEngineTurbine.setBurnerMake(equipmentReqDTO.getBurnerMake());
        cogenEngineTurbine.setBurnerModel(equipmentReqDTO.getBurnerModel());
        cogenEngineTurbine.setBurnerSerialNumber(equipmentReqDTO.getBurnerSerialNumber());
        cogenEngineTurbine.setCapacity(equipmentReqDTO.getCapacity());
        cogenEngineTurbine.setBurnerType(equipmentReqDTO.getBurnerType());
        cogenEngineTurbine.setPrimaryFuel(!ObjectUtils.isEmpty(equipmentReqDTO.getPrimaryFuel()) ? new FuelType().setFuelTypeId(equipmentReqDTO.getPrimaryFuel()) : null);
        cogenEngineTurbine.setSecondaryFuel(!ObjectUtils.isEmpty(equipmentReqDTO.getSecondaryFuel()) ? new FuelType().setFuelTypeId(equipmentReqDTO.getSecondaryFuel()) : null);
        cogenEngineTurbine.setFiringRateOil(equipmentReqDTO.getFiringRateOil());
        cogenEngineTurbine.setFiringRateNaturalGas(equipmentReqDTO.getFiringRateNaturalGas());
        cogenEngineTurbine.setPrimaryFuelFromTank(equipmentReqDTO.getPrimaryFuelFromTank());
        cogenEngineTurbine.setSecondaryFuelFromTank(equipmentReqDTO.getSecondaryFuelFromTank());
        cogenEngineTurbine.setStackExhausting(equipmentReqDTO.getStackExhausting());
        cogenEngineTurbine.setTurbineWithUnit(equipmentReqDTO.isTurbineWithUnit());
        cogenEngineTurbine.setWasteHrbWithUnit(equipmentReqDTO.isWasteHrbWithUnit());
        cogenEngineTurbine.setFuelCapping(equipmentReqDTO.isFuelCapping());
        cogenEngineTurbine.setGasFuelCapping(equipmentReqDTO.getGasFuelCapping());
        cogenEngineTurbine.setGasNoxEmissionFactor(equipmentReqDTO.getGasNoxEmissionFactor());
        cogenEngineTurbine.setOilFuelCapping(equipmentReqDTO.getOilFuelCapping());
        cogenEngineTurbine.setOilNoxEmissionFactor(equipmentReqDTO.getOilNoxEmissionFactor());
        cogenEngineTurbine.setSo2EmissionFactorOil(equipmentReqDTO.getSo2EmissionFactorOil());
        cogenEngineTurbine.setSo2EmissionFactorGas(equipmentReqDTO.getSo2EmissionFactorGas());
        cogenEngineTurbine.setSo2Allowable(equipmentReqDTO.getSo2Allowable());
        cogenEngineTurbine.setNoxAllowable(equipmentReqDTO.getNoxAllowable());
        cogenEngineTurbine.setComments(equipmentReqDTO.getComments());
        cogenEngineTurbine.setStatus(!ObjectUtils.isEmpty(equipmentReqDTO.getStatus()) ? new ElevatorStatus().setStatusId(equipmentReqDTO.getStatus()) : null);
        final CogenEngineTurbine engineTurbine = this.cogenEngineTurbineRepository.saveAndFlush(cogenEngineTurbine);
        this.saveAgency(equipmentReqDTO, cogenEngineTurbine);
        return this.setEquipment(engineTurbine, null);
    }

    public List<CogenTurbineResDTO> getCogenEngineTurbine(final Long engineId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        final List<CogenEngineTurbine> cogenEngineTurbines = new LinkedList<>();
        final List<BoilerCogenAgencyInfo> boilerCogenAgencyInfoList = new LinkedList<>();
        if (equipmentDetailsReqVO.getEquipmentType() != null && !equipmentDetailsReqVO.getEquipmentType().equalsIgnoreCase(ConstantStore.COGEN_ENGINE_TURBINE)) {
            throw new ValidationError(ApplicationMessageStore.EQUIPMENT_TYPE_NOT_MATCH);
        }
        if (engineId != null) {
            final CogenEngineTurbine cogenEngineTurbine = this.cogenEngineTurbineRepository.findByEngineId(engineId);
            cogenEngineTurbines.add(cogenEngineTurbine);
            final List<BoilerCogenAgencyInfo> boilerCogenAgencyInfos = this.boilerCogenAgencyInfoRepository.findByCogenEngineTurbine(cogenEngineTurbine);
            boilerCogenAgencyInfos.forEach(boilerCogenAgencyInfo -> {
                boilerCogenAgencyInfo.setCogenEngineTurbine(null);
                boilerCogenAgencyInfoList.add(boilerCogenAgencyInfo);
            });
        } else {
            final Building building = this.buildingRepository.findByBuildingId(equipmentDetailsReqVO.getBuildingId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            if (equipmentDetailsReqVO.getFloor() == null) {
                cogenEngineTurbines.addAll(this.cogenEngineTurbineRepository.findAllByBuilding(building));
            } else {
                cogenEngineTurbines.addAll(this.cogenEngineTurbineRepository.findAllByBuildingAndFloor(building, equipmentDetailsReqVO.getFloor()));
            }
        }
        return cogenEngineTurbines.stream()
                .map(turbine -> setEquipment(turbine, boilerCogenAgencyInfoList))
                .collect(Collectors.toList());
    }

    public CogenTurbineResDTO updateCogenEngineTurbine(final Long engineId, final CogenTurbineReqDTO equipmentReqDTO) {
        return this.createCogenEngineTurbine(equipmentReqDTO, engineId);
    }

    public void deleteCogenEngineTurbine(final Long engineId, final boolean activeStatus, final boolean isPermanentDelete) {
        final User user = this.validatorService.validateUser();
        final String userRole = user.getRole().getRole();
        final CogenEngineTurbine cogenEngineTurbine;
        if (isPermanentDelete && (userRole.equalsIgnoreCase(ConstantStore.ADMIN_SUPER_USER) || userRole.equalsIgnoreCase(ConstantStore.ADMIN_USER))) {
            cogenEngineTurbine = this.cogenEngineTurbineRepository.findByEngineId(engineId);
            if (cogenEngineTurbine == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            this.cogenEngineTurbineRepository.delete(cogenEngineTurbine);
            this.commonUtilService.removeEquipment(ConstantStore.COGEN_ENGINE_TURBINE, cogenEngineTurbine.getBuilding(), 1L);
        } else {
            cogenEngineTurbine = this.cogenEngineTurbineRepository.findByEngineIdAndIsActive(engineId, true);
            if (cogenEngineTurbine == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            cogenEngineTurbine.setIsActive(activeStatus);
            this.cogenEngineTurbineRepository.save(cogenEngineTurbine);
        }
    }

    private JobFilingInformation saveJobFilingInformation(final JobFilingInformation jobFilingInformation) {
        return this.jobFilingInformationRepository.save(jobFilingInformation);
    }

    private CogenTurbineResDTO setEquipment(final CogenEngineTurbine cogenEngineTurbine, final List<BoilerCogenAgencyInfo> boilerCogenAgencyInfoList) {
        final CogenTurbineResDTO equipmentResDTO = new CogenTurbineResDTO();
        equipmentResDTO.setId(cogenEngineTurbine.getEngineId());
        equipmentResDTO.setUniqueId(cogenEngineTurbine.getUniqueId());
        equipmentResDTO.setFloor(cogenEngineTurbine.getFloor());
        equipmentResDTO.setLocation(cogenEngineTurbine.getLocation());
        equipmentResDTO.setInstalledOn(cogenEngineTurbine.getInstalledOn());
        equipmentResDTO.setMakeBy(cogenEngineTurbine.getMake());
        equipmentResDTO.setInstalledBy(cogenEngineTurbine.getInstalledBy());
        equipmentResDTO.setManagedBy(cogenEngineTurbine.getManagedBy());
        equipmentResDTO.setSerialNo(cogenEngineTurbine.getSerialNo());
        equipmentResDTO.setDeviceType(ConstantStore.COGEN_ENGINE_TURBINE);
        equipmentResDTO.setJobFilingInformation(cogenEngineTurbine.getJobFilingInformation());
        equipmentResDTO.setSerialNo(cogenEngineTurbine.getSerialNo());
        equipmentResDTO.setApplicationId(cogenEngineTurbine.getApplicationId());
        equipmentResDTO.setApplicationType(cogenEngineTurbine.getApplicationType());
        equipmentResDTO.setNumberOfIdenticalUnits(cogenEngineTurbine.getNumberOfIdenticalUnits());
        equipmentResDTO.setPrimaryUse(!ObjectUtils.isEmpty(cogenEngineTurbine.getPrimaryUse()) ? cogenEngineTurbine.getPrimaryUse().getId() : null);
        equipmentResDTO.setEngineHasControlSystem(cogenEngineTurbine.isEngineHasControlSystem());
        equipmentResDTO.setTypeOfControlEfficiency(!ObjectUtils.isEmpty(cogenEngineTurbine.getTypeOfControlEfficiency()) ? cogenEngineTurbine.getTypeOfControlEfficiency().getId() : null);
        equipmentResDTO.setModel(cogenEngineTurbine.getModel());
        equipmentResDTO.setBurnerMake(cogenEngineTurbine.getBurnerMake());
        equipmentResDTO.setBurnerModel(cogenEngineTurbine.getBurnerModel());
        equipmentResDTO.setBurnerSerialNumber(cogenEngineTurbine.getBurnerSerialNumber());
        equipmentResDTO.setCapacity(cogenEngineTurbine.getCapacity());
        equipmentResDTO.setBurnerType(cogenEngineTurbine.getBurnerType());
        equipmentResDTO.setPrimaryFuel(!ObjectUtils.isEmpty(cogenEngineTurbine.getPrimaryFuel()) ? cogenEngineTurbine.getPrimaryFuel().getFuelTypeId() : null);
        equipmentResDTO.setSecondaryFuel(!ObjectUtils.isEmpty(cogenEngineTurbine.getSecondaryFuel()) ? cogenEngineTurbine.getSecondaryFuel().getFuelTypeId() : null);
        equipmentResDTO.setFiringRateOil(cogenEngineTurbine.getFiringRateOil());
        equipmentResDTO.setFiringRateNaturalGas(cogenEngineTurbine.getFiringRateNaturalGas());
        equipmentResDTO.setPrimaryFuelFromTank(cogenEngineTurbine.getPrimaryFuelFromTank());
        equipmentResDTO.setSecondaryFuelFromTank(cogenEngineTurbine.getSecondaryFuelFromTank());
        equipmentResDTO.setStackExhausting(cogenEngineTurbine.getStackExhausting());
        equipmentResDTO.setTurbineWithUnit(cogenEngineTurbine.isTurbineWithUnit());
        equipmentResDTO.setWasteHrbWithUnit(cogenEngineTurbine.isWasteHrbWithUnit());
        equipmentResDTO.setFuelCapping(cogenEngineTurbine.isFuelCapping());
        equipmentResDTO.setGasFuelCapping(cogenEngineTurbine.getGasFuelCapping());
        equipmentResDTO.setGasNoxEmissionFactor(cogenEngineTurbine.getGasNoxEmissionFactor());
        equipmentResDTO.setOilFuelCapping(cogenEngineTurbine.getOilFuelCapping());
        equipmentResDTO.setOilNoxEmissionFactor(cogenEngineTurbine.getOilNoxEmissionFactor());
        equipmentResDTO.setSo2EmissionFactorOil(cogenEngineTurbine.getSo2EmissionFactorOil());
        equipmentResDTO.setSo2EmissionFactorGas(cogenEngineTurbine.getSo2EmissionFactorGas());
        equipmentResDTO.setSo2Allowable(cogenEngineTurbine.getSo2Allowable());
        equipmentResDTO.setNoxAllowable(cogenEngineTurbine.getNoxAllowable());
        equipmentResDTO.setComments(cogenEngineTurbine.getComments());
        equipmentResDTO.setStatus(!ObjectUtils.isEmpty(cogenEngineTurbine.getStatus()) ? cogenEngineTurbine.getStatus().getStatusId() : null);
        equipmentResDTO.setBoilerCogenAgencyInfoList(!ObjectUtils.isEmpty(boilerCogenAgencyInfoList) ? this.setBoilerCogenAgencyInfoDTOS(boilerCogenAgencyInfoList) : null);
        return equipmentResDTO;
    }

    private void saveAgency(final CogenTurbineReqDTO equipmentReqDTO, final CogenEngineTurbine cogenEngineTurbine) {
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
                boilerCogenAgencyInfo.setCogenEngineTurbine(cogenEngineTurbine);
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
