package com.inTrack.spring.service.equipmentService;

import com.inTrack.spring.dto.common.GeneratorAgencyInfoDTO;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.FumeHoodReqDTO;
import com.inTrack.spring.dto.requestDTO.GeneratorReqDTO;
import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.dto.responseDTO.BoilerCogenAgencyInfoDTO;
import com.inTrack.spring.dto.responseDTO.GeneratorResDTO;
import com.inTrack.spring.entity.*;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.FumeHood;
import com.inTrack.spring.entity.equipmentEntity.Generator;
import com.inTrack.spring.entity.equipmentEntity.TierLevel;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.GeneratorAgencyInfo;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.BoilerCogenAgency;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.GeneratorAgency;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.AgencyRepo.GeneratorAgencyInfoRepository;
import com.inTrack.spring.repository.AgencyRepo.GeneratorAgencyRepository;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
import com.inTrack.spring.repository.equipmentRepository.GeneratorRepository;
import com.inTrack.spring.repository.equipmentRepository.PetroleumBulkStorageDropDown.TierLevelRepository;
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

/**
 * @author vijayan
 */

@Service
public class GeneratorService {

    @Autowired
    private GeneratorRepository generatorRepository;

    @Autowired
    private ValidatorService validatorService;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private CommonUtilService commonUtilService;

    @Autowired
    private JobFilingInformationRepository jobFilingInformationRepository;

    @Autowired
    private CommonEquipmentService commonEquipmentService;

    @Autowired
    private TierLevelRepository tierLevelRepository;

    @Autowired
    private GeneratorAgencyInfoRepository generatorAgencyInfoRepository;

    @Autowired
    private GeneratorAgencyRepository generatorAgencyRepository;

    @Transactional(rollbackOn = Exception.class)
    public GeneratorResDTO createGenerator(final GeneratorReqDTO equipmentReqDTO, final Long equipmentId) {
        final Generator generator;
        final Building building = this.buildingRepository.findByBuildingId(equipmentReqDTO.getBuildingId());
        if (equipmentId != null) {
            generator = this.generatorRepository.findByGeneratorId(equipmentId);
            generator.getJobFilingInformation().setStatus(equipmentReqDTO.getJobFilingInformationReqDTO().getStatus());
        } else {
            final Long uniqueIdCount = this.generatorRepository.countByUniqueId(equipmentReqDTO.getUniqueId());
            Long jobFillingIdCount = 0L;
            if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformationReqDTO())) {
                jobFillingIdCount = this.jobFilingInformationRepository.countByJobNumber(equipmentReqDTO.getJobFilingInformationReqDTO().getJobNumber());
            }
            if (uniqueIdCount > 0 && jobFillingIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.UNIQUE_ID_AND_JOB_FILLING_ID_EXISTS);
            } else if (uniqueIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.UNIQUE_ID_EXISTS);
            } else if (jobFillingIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.JOB_FILLING_ID_EXISTS);
            }
            generator = new Generator();
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            generator.setBuilding(building);
            this.commonUtilService.saveEquipment(ConstantStore.GENERATOR, building, 1L);
            this.commonEquipmentService.createEquipmentFloorRelation(equipmentReqDTO.getFloor(), ConstantStore.GENERATOR, building);
        }
        if (equipmentReqDTO.getJobFilingInformationReqDTO() != null) {
            if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformationReqDTO().getJobFilingId())) {
                this.commonEquipmentService.saveJobFiling(equipmentReqDTO.getJobFilingInformationReqDTO());
            } else {
                generator.setJobFilingInformation(this.saveJobFilingInformation(this.setJobFilingInfo(equipmentReqDTO.getJobFilingInformationReqDTO())));
            }
        }
        generator.setUniqueId(equipmentReqDTO.getUniqueId());
        generator.setCapacity(equipmentReqDTO.getCapacity());
        generator.setManagement(!ObjectUtils.isEmpty(equipmentReqDTO.getManagement()) ? new ManagementType().setId(equipmentReqDTO.getManagement()) : null);
        generator.setFloor(equipmentReqDTO.getFloor());
        generator.setLocation(equipmentReqDTO.getLocation());
        generator.setInstalledOn(equipmentReqDTO.getInstalledOn());
        generator.setMake(equipmentReqDTO.getMake());
        generator.setInstalledBy(equipmentReqDTO.getInstalledBy());
        generator.setManagedBy(equipmentReqDTO.getManagedBy());
        generator.setSerialNo(equipmentReqDTO.getSerialNo());
        generator.setDep(equipmentReqDTO.getDep());
        generator.setDeviceType(ConstantStore.GENERATOR);
        generator.setDisconnectedOn(equipmentReqDTO.getDisconnectedOn());
        generator.setTierLevel(!ObjectUtils.isEmpty(equipmentReqDTO.getTierLevel()) ? new TierLevel().setId(equipmentReqDTO.getTierLevel()) : null);
        generator.setStackExhausting(equipmentReqDTO.getStackExhausting());
        generator.setSo2EmissionFactorOil(equipmentReqDTO.getSo2EmissionFactorOil());
        generator.setSo2Allowable(equipmentReqDTO.getSo2Allowable());
        generator.setSecondaryFuelType(!ObjectUtils.isEmpty(equipmentReqDTO.getSecondaryFuelType()) ? new FuelType().setFuelTypeId(equipmentReqDTO.getSecondaryFuelType()) : null);
        generator.setRenewedWithDOE(equipmentReqDTO.getRenewedWithDOE());
        generator.setRegisteredWithDOE(equipmentReqDTO.getRegisteredWithDOE());
        generator.setQualifiesForDRProgram(equipmentReqDTO.getQualifiesForDRProgram());
        generator.setQualifiesForDOE(equipmentReqDTO.getQualifiesForDOE());
        generator.setPrimaryFuelType(!ObjectUtils.isEmpty(equipmentReqDTO.getPrimaryFuelType()) ? new FuelType().setFuelTypeId(equipmentReqDTO.getPrimaryFuelType()) : null);
        generator.setOilNoxEmissionFactor(equipmentReqDTO.getOilNoxEmissionFactor());
        generator.setOilFuelCapping(equipmentReqDTO.getOilFuelCapping());
        generator.setNoxAllowable(equipmentReqDTO.getNoxAllowable());
        generator.setNoOfIdenticalUnits(equipmentReqDTO.getNoOfIdenticalUnits());
        generator.setModel(equipmentReqDTO.getModel());
        generator.setMea(equipmentReqDTO.getMea());
        generator.setManagementNote(equipmentReqDTO.getManagementNote());
        generator.setLandmark(equipmentReqDTO.getLandmark());
        generator.setIsSchedule(equipmentReqDTO.getIsSchedule());
        generator.setIsRecordsInPermanentBoundBook(equipmentReqDTO.getIsRecordsInPermanentBoundBook());
        generator.setIsPlanApproval(equipmentReqDTO.getIsPlanApproval());
        generator.setIsDOBPermitExpire(equipmentReqDTO.getIsDOBPermitExpire());
        generator.setIsDEPPermitExpire(equipmentReqDTO.getIsDepPermitExpire());
        generator.setIsDayTank(equipmentReqDTO.getIsDayTank());
        generator.setHoursOfOperationLimit(equipmentReqDTO.getHoursOfOperationLimit());
        generator.setHasDayTank(equipmentReqDTO.getHasDayTank());
        generator.setGasNoxEmissionFactor(equipmentReqDTO.getGasNoxEmissionFactor());
        generator.setGasFuelCapping(equipmentReqDTO.getGasFuelCapping());
        generator.setGasEmissionFactor(equipmentReqDTO.getGasEmissionFactor());
        generator.setFuelCapping(equipmentReqDTO.getFuelCapping());
        generator.setFiringRate(equipmentReqDTO.getFiringRate());
        generator.setDOBStatus(equipmentReqDTO.getDobStatus());
        generator.setDOBIssueDate(equipmentReqDTO.getDobIssueDate());
        generator.setDOBExpireDate(equipmentReqDTO.getDobExpireDate());
        generator.setDEPStatus(equipmentReqDTO.getDepStatus());
        generator.setDEPIssueDate(equipmentReqDTO.getDepIssueDate());
        generator.setDEPExpireDate(equipmentReqDTO.getDepExpireDate());
        generator.setStatus(!ObjectUtils.isEmpty(equipmentReqDTO.getStatus()) ? new ElevatorStatus().setStatusId(equipmentReqDTO.getStatus()) : null);
        final Generator saveGenerator = this.generatorRepository.save(generator);
        this.saveAgency(equipmentReqDTO, saveGenerator);
        return this.setEquipment(saveGenerator, null);
    }

    public List<GeneratorResDTO> getGenerator(final Long generatorId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        final List<Generator> generators = new LinkedList<>();
        final List<GeneratorAgencyInfo> agencyInfoList = new LinkedList<>();
        if (equipmentDetailsReqVO.getEquipmentType() != null && !equipmentDetailsReqVO.getEquipmentType().equalsIgnoreCase(ConstantStore.GENERATOR)) {
            throw new ValidationError(ApplicationMessageStore.EQUIPMENT_TYPE_NOT_MATCH);
        }
        if (generatorId != null) {
            final Generator generator = this.generatorRepository.findByGeneratorId(generatorId);
            final List<GeneratorAgencyInfo> generatorAgencyInfoList = this.generatorAgencyInfoRepository.findByGenerator(generator);
            generatorAgencyInfoList.forEach(generatorAgencyInfo -> {
                generatorAgencyInfo.setGenerator(null);
                agencyInfoList.add(generatorAgencyInfo);
            });
            generators.add(generator);
        } else {
            final Building building = this.buildingRepository.findByBuildingId(equipmentDetailsReqVO.getBuildingId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            if (equipmentDetailsReqVO.getFloor() == null) {
                generators.addAll(this.generatorRepository.findAllByBuilding(building));
            } else {
                generators.addAll(this.generatorRepository.findAllByBuildingAndFloor(building, equipmentDetailsReqVO.getFloor()));
            }
        }
        return generators.stream().map(generator -> this.setEquipment(generator, agencyInfoList)).toList();
    }

    public GeneratorResDTO updateGenerator(final Long equipmentId, final GeneratorReqDTO equipmentReqDTO) {
        return this.createGenerator(equipmentReqDTO, equipmentId);
    }

    public void deleteGenerator(final Long generatorId, final boolean activeStatus, final boolean isPermanentDelete) {
        final User user = this.validatorService.validateUser();
        final String userRole = user.getRole().getRole();
        final Generator generator;
        if (isPermanentDelete && (userRole.equalsIgnoreCase(ConstantStore.ADMIN_SUPER_USER) || userRole.equalsIgnoreCase(ConstantStore.ADMIN_USER))) {
            generator = this.generatorRepository.findByGeneratorId(generatorId);
            if (generator == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            this.commonUtilService.removeEquipment(ConstantStore.GENERATOR, generator.getBuilding(), 1L);
            this.generatorRepository.delete(generator);
        } else {
            generator = this.generatorRepository.findByGeneratorIdAndIsActive(generatorId, true);
            if (generator == null) {
                throw new ValidationError(ApplicationMessageStore.BOILER_NOT_FOUND);
            }
            generator.setIsActive(activeStatus);
            this.generatorRepository.save(generator);
        }
    }

    public List<TierLevel> getTireLevel(final String search) {
        return this.tierLevelRepository.getTierLevel(search != null ? search : "");
    }

    private JobFilingInformation saveJobFilingInformation(final JobFilingInformation jobFilingInformation) {
        return this.jobFilingInformationRepository.save(jobFilingInformation);
    }

    private GeneratorResDTO setEquipment(final Generator generator, final List<GeneratorAgencyInfo> agencyInfoList) {
        return GeneratorResDTO.builder()
                .uniqueId(generator.getUniqueId())
                .capacity(generator.getCapacity())
                .isActive(generator.getIsActive())
                .management(!ObjectUtils.isEmpty(generator.getManagement()) ? generator.getManagement().getId() : null)
                .generatorId(generator.getGeneratorId())
                .floor(generator.getFloor())
                .location(generator.getLocation())
                .installedOn(generator.getInstalledOn())
                .make(generator.getMake())
                .installedBy(generator.getInstalledBy())
                .managedBy(generator.getManagedBy())
                .serialNo(generator.getSerialNo())
                .dep(generator.getDep())
                .deviceType(generator.getDeviceType())
                .disconnectedOn(generator.getDisconnectedOn())
                .tierLevel(!ObjectUtils.isEmpty(generator.getTierLevel()) ? generator.getTierLevel().getId() : null)
                .stackExhausting(generator.getStackExhausting())
                .so2EmissionFactorOil(generator.getSo2EmissionFactorOil())
                .so2Allowable(generator.getSo2Allowable())
                .secondaryFuelType(!ObjectUtils.isEmpty(generator.getSecondaryFuelType()) ? generator.getSecondaryFuelType().getFuelTypeId() : null)
                .renewedWithDOE(generator.getRenewedWithDOE())
                .registeredWithDOE(generator.getRegisteredWithDOE())
                .qualifiesForDRProgram(generator.getQualifiesForDRProgram())
                .qualifiesForDOE(generator.getQualifiesForDOE())
                .primaryFuelType(!ObjectUtils.isEmpty(generator.getPrimaryFuelType()) ? generator.getPrimaryFuelType().getFuelTypeId() : null)
                .oilNoxEmissionFactor(generator.getOilNoxEmissionFactor())
                .oilFuelCapping(generator.getOilFuelCapping())
                .noxAllowable(generator.getNoxAllowable())
                .noOfIdenticalUnits(generator.getNoOfIdenticalUnits())
                .model(generator.getModel())
                .mea(generator.getMea())
                .managementNote(generator.getManagementNote())
                .landmark(generator.getLandmark())
                .isSchedule(generator.getIsSchedule())
                .isRecordsInPermanentBoundBook(generator.getIsRecordsInPermanentBoundBook())
                .isPlanApproval(generator.getIsPlanApproval())
                .isDOBPermitExpire(generator.getIsDOBPermitExpire())
                .isDepPermitExpire(generator.getIsDEPPermitExpire())
                .isDayTank(generator.getIsDayTank())
                .hoursOfOperationLimit(generator.getHoursOfOperationLimit())
                .hasDayTank(generator.getHasDayTank())
                .gasNoxEmissionFactor(generator.getGasNoxEmissionFactor())
                .gasFuelCapping(generator.getGasFuelCapping())
                .gasEmissionFactor(generator.getGasEmissionFactor())
                .fuelCapping(generator.getFuelCapping())
                .firingRate(generator.getFiringRate())
                .dobStatus(generator.getDOBStatus())
                .dobIssueDate(generator.getDOBIssueDate())
                .dobExpireDate(generator.getDOBExpireDate())
                .depStatus(generator.getDEPStatus())
                .depIssueDate(generator.getDEPIssueDate())
                .depExpireDate(generator.getDEPExpireDate())
                .jobFilingInformation(!ObjectUtils.isEmpty(generator.getJobFilingInformation()) ? generator.getJobFilingInformation() : null)
                .buildingId(generator.getBuilding().getBuildingId())
                .generatorAgencyInfoList(!ObjectUtils.isEmpty(agencyInfoList) ? this.setGeneratorAgencyInfo(agencyInfoList) : null)
                .status(!ObjectUtils.isEmpty(generator.getStatus()) ? generator.getStatus().getStatusId() : null)
                .build();
    }

    private void saveAgency(final GeneratorReqDTO equipmentReqDTO, final Generator generator) {
        if (equipmentReqDTO.getGeneratorAgencyInfoList() != null) {
            final List<GeneratorAgencyInfoDTO> agencyInfoList = equipmentReqDTO.getGeneratorAgencyInfoList();
            final List<GeneratorAgencyInfo> generatorAgencyInfos = new LinkedList<>();
            for (GeneratorAgencyInfoDTO agencyInfoDTO : agencyInfoList) {
                GeneratorAgencyInfo generatorAgencyInfo;
                if (agencyInfoDTO.getId() != null) {
                    generatorAgencyInfo = generatorAgencyInfoRepository.findById(agencyInfoDTO.getId())
                            .orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
                } else {
                    generatorAgencyInfo = new GeneratorAgencyInfo();
                }
                for (final Field field : GeneratorAgencyInfo.class.getDeclaredFields()) {
                    field.setAccessible(true);
                    if ((agencyInfoDTO.getId() == null && field.getName().equalsIgnoreCase("id")) || field.isAnnotationPresent(ManyToOne.class)) {
                        continue;
                    }
                    try {
                        final Object value = this.getFieldValue(agencyInfoDTO, field.getName());
                        if (value != null) {
                            field.set(generatorAgencyInfo, value);
                        }
                    } catch (final NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                        throw new ValidationError("Error setting field: " + field.getName());
                    }
                }
                generatorAgencyInfo.setGenerator(generator);
                this.setGeneratorAgency(agencyInfoDTO, generatorAgencyInfo);
                generatorAgencyInfos.add(generatorAgencyInfo);
            }
            this.generatorAgencyInfoRepository.saveAll(generatorAgencyInfos);
        }
    }

    private GeneratorAgencyInfo setGeneratorAgency(final GeneratorAgencyInfoDTO generatorAgencyInfoDTO, final GeneratorAgencyInfo generatorAgencyInfo) {
        final Long generatorAgencyId = generatorAgencyInfoDTO.getGeneratorAgency();
        if (generatorAgencyId != null) {
            final GeneratorAgency generatorAgency = this.generatorAgencyRepository.findById(generatorAgencyId)
                    .orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
            generatorAgencyInfo.setGeneratorAgency(generatorAgency);
        }
        return generatorAgencyInfo;
    }

    private Object getFieldValue(final GeneratorAgencyInfoDTO agencyInfoDTO, final String fieldName) throws NoSuchFieldException, IllegalAccessException {
        final Field field = GeneratorAgencyInfoDTO.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(agencyInfoDTO);
    }

    private List<GeneratorAgencyInfoDTO> setGeneratorAgencyInfo(final List<GeneratorAgencyInfo> agencyInfoList) {
        final List<GeneratorAgencyInfoDTO> generatorAgencyInfoDTOS = new LinkedList<>();
        agencyInfoList.forEach(agencyInfo -> {
            final GeneratorAgencyInfoDTO agencyInfoDTO = new GeneratorAgencyInfoDTO();
            agencyInfoDTO.setId(agencyInfo.getId());
            agencyInfoDTO.setAgencyNumber(agencyInfo.getAgencyNumber());
            agencyInfoDTO.setIssueDate(agencyInfo.getIssueDate());
            agencyInfoDTO.setExpirationDate(agencyInfo.getExpirationDate());
            agencyInfoDTO.setStatus(agencyInfo.getStatus());
            agencyInfoDTO.setSubmittedDate(agencyInfo.getSubmittedDate());
            agencyInfoDTO.setNote(agencyInfo.getNote());
            agencyInfoDTO.setQualifiesForDRProgram(agencyInfo.getQualifiesForDRProgram());
            agencyInfoDTO.setStackLastTestDate(agencyInfo.getStackLastTestDate());
            agencyInfoDTO.setStackNextTestDate(agencyInfo.getStackNextTestDate());
            agencyInfoDTO.setNoxLevel(agencyInfo.getNoxLevel());
            agencyInfoDTO.setUnitType(agencyInfo.getUnitType());
            agencyInfoDTO.setTestConductedBy(agencyInfo.getTestConductedBy());
            agencyInfoDTO.setOtherGeneratorCombined(agencyInfo.getOtherGeneratorCombined());
            agencyInfoDTO.setCombinedGenerator(agencyInfo.getCombinedGenerator());
            agencyInfoDTO.setProtocolSubmittedToDEC(agencyInfo.getProtocolSubmittedToDEC());
            agencyInfoDTO.setQualifiesForDOE(agencyInfo.getQualifiesForDOE());
            agencyInfoDTO.setRegisteredWithDOE(agencyInfo.getRegisteredWithDOE());
            agencyInfoDTO.setRenewedWithDOE(agencyInfo.getRenewedWithDOE());
            agencyInfoDTO.setGeneratorAgency(agencyInfo.getGeneratorAgency().getId());
            generatorAgencyInfoDTOS.add(agencyInfoDTO);
        });

        return generatorAgencyInfoDTOS;
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
