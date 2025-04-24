package com.inTrack.spring.service.equipmentService;

import com.inTrack.spring.UtilService.CommonUtilService;
import com.inTrack.spring.dto.requestDTO.BoilerReqDTO;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.dto.responseDTO.BoilerCogenAgencyInfoDTO;
import com.inTrack.spring.dto.responseDTO.BoilerResDTO;
import com.inTrack.spring.entity.*;
import com.inTrack.spring.entity.equipmentEntity.Boiler;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.BoilerCogenAgency;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.InspectionType;
import com.inTrack.spring.entity.equipmentEntity.boilerDropDown.BoilerPressureType;
import com.inTrack.spring.entity.equipmentEntity.boilerDropDown.PrimaryUse;
import com.inTrack.spring.entity.equipmentEntity.boilerDropDown.TestOnFuel;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.AgencyRepo.BoilerCogenAgencyInfoRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.BoilerCogenAgencyRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.InspectionDurationTypeRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.InspectionTypeRepository;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
import com.inTrack.spring.repository.equipmentRepository.BoilerPressureTypeRepository;
import com.inTrack.spring.repository.equipmentRepository.BoilerRepository;
import com.inTrack.spring.repository.equipmentRepository.PrimaryUseRepository;
import com.inTrack.spring.repository.equipmentRepository.TestOnFuelRepository;
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
public class BoilerService {

    private final BoilerRepository boilerRepository;
    private final ValidatorService validatorService;
    private final BuildingRepository buildingRepository;
    private final CommonUtilService commonUtilService;
    private final JobFilingInformationRepository jobFilingInformationRepository;
    private final CommonEquipmentService commonEquipmentService;
    private final BoilerPressureTypeRepository boilerPressureTypeRepository;
    private final PrimaryUseRepository primaryUseRepository;
    private final TestOnFuelRepository testOnFuelRepository;
    private final BoilerCogenAgencyInfoRepository boilerCogenAgencyInfoRepository;
    private final BoilerCogenAgencyRepository boilerCogenAgencyRepository;
    private final InspectionDurationTypeRepository inspectionDurationTypeRepository;
    private final InspectionTypeRepository inspectionTypeRepository;

    @Transactional(rollbackOn = Exception.class)
    public BoilerResDTO createBoiler(final BoilerReqDTO equipmentReqDTO, final Long boilerId) {
        final Boiler boiler;
        final Building building = this.buildingRepository.findByBuildingId(equipmentReqDTO.getBuildingId());
        if (boilerId != null) {
            boiler = this.boilerRepository.findByBoilerId(boilerId);
        } else {
            final Long uniqueIdCount = this.boilerRepository.countByUniqueId(equipmentReqDTO.getUniqueId());
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
            boiler = new Boiler();
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            boiler.setBuilding(building);
            boiler.setUniqueId(equipmentReqDTO.getUniqueId());
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
                boiler.setJobFilingInformation(this.saveJobFilingInformation(jobFilingInformation));
            }
            this.commonUtilService.saveEquipment(ConstantStore.BOILER, building, 1L);
            this.commonEquipmentService.createEquipmentFloorRelation(equipmentReqDTO.getFloor(), ConstantStore.BOILER, building);
        }
        boiler.setDisconnectedOn(equipmentReqDTO.getDisconnectedOn());
        boiler.setApplicationId(equipmentReqDTO.getApplicationId());
        boiler.setApplicationType(equipmentReqDTO.getApplicationType());
        boiler.setFloor(equipmentReqDTO.getFloor());
        boiler.setLandmark(equipmentReqDTO.getLandMark());
        boiler.setLocation(equipmentReqDTO.getLocation());
        boiler.setInstalledOn(equipmentReqDTO.getInstalledOn());
        boiler.setMakeBy(equipmentReqDTO.getMakeBy());
        boiler.setInstalledBy(equipmentReqDTO.getInstalledBy());
        boiler.setManagedBy(equipmentReqDTO.getManagedBy());
        boiler.setSerialNo(equipmentReqDTO.getSerialNo());
        boiler.setDeviceType(ConstantStore.BOILER);
        boiler.setManagement(equipmentReqDTO.getManagement() != null ? new ManagementType().setId(equipmentReqDTO.getManagement()) : null);
        boiler.setManagementNote(equipmentReqDTO.getManagementNotes());
        boiler.setBoilerMake(equipmentReqDTO.getBoilerMake());
        boiler.setBoilerModel(equipmentReqDTO.getBoilerModel());
        boiler.setBurnerMake(equipmentReqDTO.getBurnerMake());
        boiler.setBurnerModel(equipmentReqDTO.getBurnerModel());
        boiler.setBurnerSerialNumber(equipmentReqDTO.getBurnerSerialNumber());
        boiler.setNumberOfIdenticalUnits(equipmentReqDTO.getNumberOfIdenticalUnits());
        boiler.setCapacity(equipmentReqDTO.getCapacity());
        boiler.setMeaNumber(equipmentReqDTO.getMeaNumber());
        boiler.setDepNumber(equipmentReqDTO.getDepNumber());
        boiler.setDepStatus(equipmentReqDTO.getDepStatus());
        boiler.setDecSourceId(equipmentReqDTO.getDecSourceId());
        boiler.setDobDeviceNumber(equipmentReqDTO.getDobDeviceNumber());
        boiler.setBoilerPressureType(equipmentReqDTO.getBoilerPressureType() != null ? new BoilerPressureType().setId(equipmentReqDTO.getBoilerPressureType()) : null);
        boiler.setPrimaryUse(equipmentReqDTO.getPrimaryUse() != null ? new PrimaryUse().setId(equipmentReqDTO.getPrimaryUse()) : null);
        boiler.setPrimaryFuel(equipmentReqDTO.getPrimaryFuel() != null ? new FuelType().setFuelTypeId(equipmentReqDTO.getPrimaryFuel()) : null);
        boiler.setSecondaryFuel(equipmentReqDTO.getSecondaryFuel() != null ? new FuelType().setFuelTypeId(equipmentReqDTO.getSecondaryFuel()) : null);
        boiler.setFiringRateOil(equipmentReqDTO.getFiringRateOil());
        boiler.setFiringRateNaturalGas(equipmentReqDTO.getFiringRateNaturalGas());
        boiler.setPrimaryFuelTank(equipmentReqDTO.getPrimaryFuelTank());
        boiler.setSecondaryFuelTank(equipmentReqDTO.getSecondaryFuelTank());
        boiler.setStackExhausting(equipmentReqDTO.getStackExhausting());
        boiler.setFireDepartmentApproval(equipmentReqDTO.getFireDepartmentApproval());
        boiler.setScheduleC(equipmentReqDTO.getScheduleC());
        boiler.setPlanApproval(equipmentReqDTO.getPlanApproval());
        boiler.setParameter(equipmentReqDTO.getParameter());
        boiler.setTestOnFuel(equipmentReqDTO.getTestOnFuel() != null ? new TestOnFuel().setId(equipmentReqDTO.getTestOnFuel()) : null);
        boiler.setOctrNumber(equipmentReqDTO.getOctrNumber());
        boiler.setOpacityMonitorInstalled(equipmentReqDTO.getOpacityMonitorInstalled());
        boiler.setPerformanceTestProtocolSubmitted(equipmentReqDTO.getPerformanceTestProtocolSubmitted());
        boiler.setPerformanceTestProtocolDate(equipmentReqDTO.getPerformanceTestProtocolDate());
        boiler.setPerformanceTestReportSubmitted(equipmentReqDTO.getPerformanceTestReportSubmitted());
        boiler.setPerformanceTestReportDate(equipmentReqDTO.getPerformanceTestReportDate());
        boiler.setSulphurContentSampled(equipmentReqDTO.getSulphurContentSampled());
        boiler.setSulphurContentPercentage(equipmentReqDTO.getSulphurContentPercentage());
        boiler.setNitrogenContentRequired(equipmentReqDTO.getNitrogenContentRequired());
        boiler.setNitrogenContentPercentage(equipmentReqDTO.getNitrogenContentPercentage());
        boiler.setNspsSubject(equipmentReqDTO.getNspsSubject());
        boiler.setModifiedInPast(equipmentReqDTO.getModifiedInPast());
        boiler.setEmissionCreditRequired(equipmentReqDTO.getEmissionCreditRequired());
        boiler.setFederalPSDSubject(equipmentReqDTO.getFederalPSDSubject());
        boiler.setOtherBoilerCombined(equipmentReqDTO.getOtherBoilerCombined());
        boiler.setFuelCapping(equipmentReqDTO.getFuelCapping());
        boiler.setGasFuelCapping(equipmentReqDTO.getGasFuelCapping());
        boiler.setGasNOxEmissionFactor(equipmentReqDTO.getGasNOxEmissionFactor());
        boiler.setOilFuelCapping(equipmentReqDTO.getOilFuelCapping());
        boiler.setOilNOxEmissionFactor(equipmentReqDTO.getOilNOxEmissionFactor());
        boiler.setSo2EmissionFactorOil(equipmentReqDTO.getSo2EmissionFactorOil());
        boiler.setSo2EmissionFactorGas(equipmentReqDTO.getSo2EmissionFactorGas());
        boiler.setSo2AllowableTonsPerYear(equipmentReqDTO.getSo2AllowableTonsPerYear());
        boiler.setNoxAllowableTonsPerYear(equipmentReqDTO.getNoxAllowableTonsPerYear());
        boiler.setComments(equipmentReqDTO.getComments());
        boiler.setStatus(new ElevatorStatus().setStatusId(equipmentReqDTO.getStatus()));
        final Boiler saveBoiler = this.boilerRepository.saveAndFlush(boiler);
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
                    boilerCogenAgencyInfo.setBoiler(saveBoiler);
                    this.setBoilerCogenAgency(agencyInfoDTO, boilerCogenAgencyInfo);
                    this.setInspectionDurationType(agencyInfoDTO, boilerCogenAgencyInfo);
                    this.setInspectionType(agencyInfoDTO, boilerCogenAgencyInfo);
                    boilerCogenAgencyInfos.add(boilerCogenAgencyInfo);
                }
                boilerCogenAgencyInfoRepository.saveAll(boilerCogenAgencyInfos);
            }

        return this.setEquipment(saveBoiler, null);
    }

    public List<BoilerResDTO> getBoilers(final Long boilerId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        final List<Boiler> equipmentList = new LinkedList<>();
        final List<BoilerCogenAgencyInfo> boilerCogenAgencyInfoList = new LinkedList<>();
        if (equipmentDetailsReqVO.getEquipmentType() != null && !equipmentDetailsReqVO.getEquipmentType().equalsIgnoreCase(ConstantStore.BOILER)) {
            throw new ValidationError(ApplicationMessageStore.EQUIPMENT_TYPE_NOT_MATCH);
        }
        if (boilerId != null) {
            final Boiler boiler = this.boilerRepository.findByBoilerId(boilerId);
            equipmentList.add(boiler);
            final List<BoilerCogenAgencyInfo> boilerCogenAgencyInfos = this.boilerCogenAgencyInfoRepository.findByBoiler(boiler);
            boilerCogenAgencyInfos.forEach(boilerCogenAgencyInfo -> {
                boilerCogenAgencyInfo.setBoiler(null);
                boilerCogenAgencyInfoList.add(boilerCogenAgencyInfo);
            });
        } else {
            final Building building = this.buildingRepository.findByBuildingId(equipmentDetailsReqVO.getBuildingId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            if (equipmentDetailsReqVO.getFloor() == null) {
                equipmentList.addAll(this.boilerRepository.findAllByBuilding(building));
            } else {
                equipmentList.addAll(this.boilerRepository.findAllByBuildingAndFloor(building, equipmentDetailsReqVO.getFloor()));
            }
        }
        return equipmentList.stream().map(boiler -> this.setEquipment(boiler, boilerCogenAgencyInfoList)).toList();
    }

    public BoilerResDTO updateBoiler(final Long boilerId, final BoilerReqDTO equipmentReqDTO) {
        return this.createBoiler(equipmentReqDTO, boilerId);
    }

    public void deleteBoiler(final Long boilerId, final boolean activeStatus, final boolean isPermanentDelete) {
        final User user = this.validatorService.validateUser();
        final String userRole = user.getRole().getRole();
        final Boiler boiler;
        if (isPermanentDelete && (userRole.equalsIgnoreCase(ConstantStore.ADMIN_SUPER_USER) || userRole.equalsIgnoreCase(ConstantStore.ADMIN_USER))) {
            boiler = this.boilerRepository.findByBoilerId(boilerId);
            if (boiler == null) {
                throw new ValidationError(ApplicationMessageStore.BOILER_NOT_FOUND);
            }
            this.commonUtilService.removeEquipment(ConstantStore.BOILER, boiler.getBuilding(), 1L);
            this.boilerRepository.delete(boiler);
        } else {
            boiler = this.boilerRepository.findByBoilerIdAndIsActive(boilerId, true);
            if (boiler == null) {
                throw new ValidationError(ApplicationMessageStore.BOILER_NOT_FOUND);
            }
            boiler.setIsActive(activeStatus);
            this.boilerRepository.save(boiler);
        }
    }

    private JobFilingInformation saveJobFilingInformation(final JobFilingInformation jobFilingInformation) {
        return this.jobFilingInformationRepository.save(jobFilingInformation);
    }

    private BoilerResDTO setEquipment(final Boiler boiler, List<BoilerCogenAgencyInfo> boilerCogenAgencyInfoList) {
        return BoilerResDTO.builder()
                .boilerId(boiler.getBoilerId())
                .landMark(boiler.getLandmark())
                .isActive(boiler.getIsActive())
                .floor(boiler.getFloor())
                .location(boiler.getLocation())
                .installedOn(boiler.getInstalledOn())
                .makeBy(boiler.getMakeBy())
                .installedBy(boiler.getInstalledBy())
                .managedBy(boiler.getManagedBy())
                .managementNote(boiler.getManagementNote())
                .management(boiler.getManagement() != null ? boiler.getManagement().getId() : null)
                .serialNo(boiler.getSerialNo())
                .deviceType(boiler.getDeviceType())
                .uniqueId(boiler.getUniqueId())
                .boilerMake(boiler.getBoilerMake())
                .boilerModel(boiler.getBoilerModel())
                .burnerMake(boiler.getBurnerMake())
                .burnerModel(boiler.getBurnerModel())
                .burnerSerialNumber(boiler.getBurnerSerialNumber())
                .numberOfIdenticalUnits(boiler.getNumberOfIdenticalUnits())
                .capacity(boiler.getCapacity())
                .meaNumber(boiler.getMeaNumber())
                .depNumber(boiler.getDepNumber())
                .depStatus(boiler.getDepStatus())
                .decSourceId(boiler.getDecSourceId())
                .dobDeviceNumber(boiler.getDobDeviceNumber())
                .boilerPressureType(!ObjectUtils.isEmpty(boiler.getBoilerPressureType()) ? boiler.getBoilerPressureType().getId() : null)
                .primaryUse(!ObjectUtils.isEmpty(boiler.getPrimaryUse()) ? boiler.getPrimaryUse().getId() : null)
                .primaryFuel(!ObjectUtils.isEmpty(boiler.getPrimaryFuel()) ? boiler.getPrimaryFuel().getFuelTypeId() : null)
                .secondaryFuel(!ObjectUtils.isEmpty(boiler.getSecondaryFuel()) ? boiler.getSecondaryFuel().getFuelTypeId() : null)
                .firingRateOil(boiler.getFiringRateOil())
                .firingRateNaturalGas(boiler.getFiringRateNaturalGas())
                .primaryFuelTank(boiler.getPrimaryFuelTank())
                .secondaryFuelTank(boiler.getSecondaryFuelTank())
                .stackExhausting(boiler.getStackExhausting())
                .fireDepartmentApproval(boiler.getFireDepartmentApproval())
                .scheduleC(boiler.getScheduleC())
                .planApproval(boiler.getPlanApproval())
                .parameter(boiler.getParameter())
                .testOnFuel(!ObjectUtils.isEmpty(boiler.getTestOnFuel()) ? boiler.getTestOnFuel().getId() : null)
                .octrNumber(boiler.getOctrNumber())
                .opacityMonitorInstalled(boiler.getOpacityMonitorInstalled())
                .performanceTestProtocolSubmitted(boiler.getPerformanceTestProtocolSubmitted())
                .performanceTestProtocolDate(boiler.getPerformanceTestProtocolDate())
                .performanceTestReportSubmitted(boiler.getPerformanceTestReportSubmitted())
                .performanceTestReportDate(boiler.getPerformanceTestReportDate())
                .sulphurContentSampled(boiler.getSulphurContentSampled())
                .sulphurContentPercentage(boiler.getSulphurContentPercentage())
                .nitrogenContentRequired(boiler.getNitrogenContentRequired())
                .nitrogenContentPercentage(boiler.getNitrogenContentPercentage())
                .nspsSubject(boiler.getNspsSubject())
                .modifiedInPast(boiler.getModifiedInPast())
                .emissionCreditRequired(boiler.getEmissionCreditRequired())
                .federalPSDSubject(boiler.getFederalPSDSubject())
                .otherBoilerCombined(boiler.getOtherBoilerCombined())
                .fuelCapping(boiler.getFuelCapping())
                .gasFuelCapping(boiler.getGasFuelCapping())
                .gasNOxEmissionFactor(boiler.getGasNOxEmissionFactor())
                .oilFuelCapping(boiler.getOilFuelCapping())
                .oilNOxEmissionFactor(boiler.getOilNOxEmissionFactor())
                .so2EmissionFactorOil(boiler.getSo2EmissionFactorOil())
                .so2EmissionFactorGas(boiler.getSo2EmissionFactorGas())
                .so2AllowableTonsPerYear(boiler.getSo2AllowableTonsPerYear())
                .noxAllowableTonsPerYear(boiler.getNoxAllowableTonsPerYear())
                .comments(boiler.getComments())
                .buildingId(boiler.getBuilding().getBuildingId())
                .jobFilingInformation(boiler.getJobFilingInformation())
                .applicationId(boiler.getApplicationId())
                .applicationType(boiler.getApplicationType())
                .disconnectedOn(boiler.getDisconnectedOn())
                .status(!ObjectUtils.isEmpty(boiler.getStatus()) ? boiler.getStatus().getStatusId() : null)
                .agencyInfoList(!ObjectUtils.isEmpty(boilerCogenAgencyInfoList) ? this.setBoilerCogenAgencyInfoDTOS(boilerCogenAgencyInfoList) : null)
                .build();
    }

    public List<PrimaryUse> getPrimaryUse(final String type) {
        return this.primaryUseRepository.getAllPrimaryUse(type);
    }

    public List<BoilerPressureType> getBoilerPressureType(final String type) {
        return this.boilerPressureTypeRepository.getAllBoilerPressureType(type);
    }

    public List<TestOnFuel> getTestOnFuel(final String type) {
        return this.testOnFuelRepository.getAllTestOnFuel(type);
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

    private void setInspectionDurationType(final BoilerCogenAgencyInfoDTO agencyInfoDTO, final BoilerCogenAgencyInfo boilerCogenAgencyInfo) {
        final Long inspectionDurationTypeId = agencyInfoDTO.getInspectionDurationType();
        if (inspectionDurationTypeId != null) {
            final InspectionDurationType inspectionDurationType = this.inspectionDurationTypeRepository.findById(inspectionDurationTypeId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid InspectionDurationType ID: " + inspectionDurationTypeId));
            boilerCogenAgencyInfo.setInspectionDurationType(inspectionDurationType);
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
}
