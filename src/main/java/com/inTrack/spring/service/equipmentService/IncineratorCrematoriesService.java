package com.inTrack.spring.service.equipmentService;

import com.inTrack.spring.dto.common.CoolingTowerAgencyInfoDTO;
import com.inTrack.spring.dto.common.IncineratorCrematoriesAgencyInfoDTO;
import com.inTrack.spring.dto.requestDTO.CoolingTowerReqDTO;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.IncineratorCrematoriesReqDTO;
import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.dto.responseDTO.IncineratorCrematoriesResDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.FuelConsumption;
import com.inTrack.spring.entity.equipmentEntity.*;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.User;
import com.inTrack.spring.entity.equipmentEntity.IncineratorCrematoriesDropDown.UnitType;
import com.inTrack.spring.entity.equipmentEntity.IncineratorCrematoriesDropDown.WasteTypeBurner;
import com.inTrack.spring.entity.equipmentEntity.Stack;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.CoolingTowerAgencyInfo;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.IncineratorCrematoriesAgencyInfo;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.CoolingTowerAgency;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.IncineratorCrematoriesAgency;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.ParameterType;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.AgencyRepo.IncineratorCrematoriesAgencyInfoRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.IncineratorCrematoriesAgencyRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.ParameterTypesRepository;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.equipmentRepository.IncineratorCrematoriesRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
import com.inTrack.spring.repository.equipmentRepository.IncineratorFuelConsumptionRepository;
import com.inTrack.spring.repository.equipmentRepository.StackRepository;
import com.inTrack.spring.repository.equipmentRepository.incineratorCrematoriesDropDown.UnitTypeRepository;
import com.inTrack.spring.repository.equipmentRepository.incineratorCrematoriesDropDown.WasteTypeBurnerRepository;
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
import java.util.*;
import java.util.stream.Stream;

/**
 * @author vijayan
 */

@Service
@RequiredArgsConstructor
public class IncineratorCrematoriesService {

    private final IncineratorCrematoriesRepository incineratorCrematoriesRepository;
    private final ValidatorService validatorService;
    private final BuildingRepository buildingRepository;
    private final CommonUtilService commonUtilService;
    private final JobFilingInformationRepository jobFilingInformationRepository;
    private final CommonEquipmentService commonEquipmentService;
    private final StackRepository stackRepository;
    private final UnitTypeRepository unitTypeRepository;
    private final WasteTypeBurnerRepository wasteTypeBurnerRepository;
    private final IncineratorCrematoriesAgencyInfoRepository incineratorCrematoriesAgencyInfoRepository;
    private final IncineratorFuelConsumptionRepository incineratorFuelConsumptionRepository;
    private final IncineratorCrematoriesAgencyRepository incineratorCrematoriesAgencyRepository;
    private final ParameterTypesRepository parameterTypesRepository;

    @Transactional(rollbackOn = Exception.class)
    public IncineratorCrematoriesResDTO createIncineratorCrematories(final IncineratorCrematoriesReqDTO equipmentReqDTO, final Long id) {
        final IncineratorCrematories incineratorCrematories;
        final Building building = this.buildingRepository.findByBuildingId(equipmentReqDTO.getBuildingId());
        if (id != null) {
            incineratorCrematories = this.incineratorCrematoriesRepository.getById(id);
            if (incineratorCrematories == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
        } else {
            final Long uniqueIdCount = this.incineratorCrematoriesRepository.countByUniqueId(equipmentReqDTO.getUniqueId());
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
            incineratorCrematories = new IncineratorCrematories();
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            incineratorCrematories.setBuilding(building);
            incineratorCrematories.setUniqueId(equipmentReqDTO.getUniqueId());
            this.commonUtilService.saveEquipment(ConstantStore.INCINERATOR_CREMATORIES, building, 1L);
            this.commonEquipmentService.createEquipmentFloorRelation(equipmentReqDTO.getFloor(), ConstantStore.INCINERATOR_CREMATORIES, building);
        }
        if (equipmentReqDTO.getJobFilingInformationReqDTO() != null) {
            if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformationReqDTO().getJobFilingId())) {
                this.commonEquipmentService.saveJobFiling(equipmentReqDTO.getJobFilingInformationReqDTO());
            } else {
                incineratorCrematories.setJobFilingInformation(this.saveJobFilingInformation(this.setJobFilingInfo(equipmentReqDTO.getJobFilingInformationReqDTO())));
            }
        }
        final Stack stack = this.stackRepository.findByStackId(equipmentReqDTO.getStackId());
        if (stack == null) {
            throw new ValidationError(ApplicationMessageStore.STACK_NOT_FOUND);
        }
        incineratorCrematories.setStack(stack);
        incineratorCrematories.setStatus(!ObjectUtils.isEmpty(equipmentReqDTO.getStatus()) ? new ElevatorStatus().setStatusId(equipmentReqDTO.getStatus()) : null);
        incineratorCrematories.setDeviceType(ConstantStore.INCINERATOR_CREMATORIES);
        incineratorCrematories.setApplicationId(equipmentReqDTO.getApplicationId());
        incineratorCrematories.setUnitType(!ObjectUtils.isEmpty(equipmentReqDTO.getUnitType()) ? new UnitType().setId(equipmentReqDTO.getUnitType()) : null);
        incineratorCrematories.setBurnerModel(equipmentReqDTO.getBurnerModel());
        incineratorCrematories.setBurnerSerialNumber(equipmentReqDTO.getBurnerSerialNumber());
        incineratorCrematories.setBurnerCapacity(equipmentReqDTO.getBurnerCapacity());
        incineratorCrematories.setWasteTypeBurner(!ObjectUtils.isEmpty(equipmentReqDTO.getWasteTypeBurner()) ? new WasteTypeBurner().setId(equipmentReqDTO.getWasteTypeBurner()) : null);
        incineratorCrematories.setScrubberInstalled(equipmentReqDTO.isScrubberInstalled());
        incineratorCrematories.setCoMonitorInstalled(equipmentReqDTO.isCoMonitorInstalled());
        incineratorCrematories.setOpacityMonitorInstalled(equipmentReqDTO.isOpacityMonitorInstalled());
        incineratorCrematories.setO2MonitorInstalled(equipmentReqDTO.isO2MonitorInstalled());
        incineratorCrematories.setOpacityChartRecorderAvailable(equipmentReqDTO.isOpacityChartRecorderAvailable());
        incineratorCrematories.setQuarterlyCgaRequired(equipmentReqDTO.isQuarterlyCgaRequired());
        incineratorCrematories.setTemperatureMeasurementRequired(equipmentReqDTO.isTemperatureMeasurementRequired());
        incineratorCrematories.setMinimumTemperaturePrimary(equipmentReqDTO.getMinimumTemperaturePrimary());
        incineratorCrematories.setMinimumTemperatureSecondary(equipmentReqDTO.getMinimumTemperatureSecondary());
        incineratorCrematories.setAverageFacilityTemperaturePrimary(equipmentReqDTO.getAverageFacilityTemperaturePrimary());
        incineratorCrematories.setAverageFacilityTemperatureSecondary(equipmentReqDTO.getAverageFacilityTemperatureSecondary());
        incineratorCrematories.setFloor(equipmentReqDTO.getFloor());
        incineratorCrematories.setLocation(equipmentReqDTO.getLocation());
        incineratorCrematories.setInstalledOn(equipmentReqDTO.getInstalledOn());
        incineratorCrematories.setMake(equipmentReqDTO.getMake());
        incineratorCrematories.setInstalledBy(equipmentReqDTO.getInstalledBy());
        incineratorCrematories.setManagedBy(equipmentReqDTO.getManagedBy());
        incineratorCrematories.setSerialNo(equipmentReqDTO.getSerialNo());
        final IncineratorCrematories crematories = this.incineratorCrematoriesRepository.save(incineratorCrematories);
        this.saveAgency(equipmentReqDTO, crematories);
        if (!equipmentReqDTO.getIncineratorFuelConsumptions().isEmpty() && ObjectUtils.isEmpty(id)) {
            final List<Long> years = new LinkedList<>();
            final Map<Long, IncineratorFuelConsumption> fuelConsumptionMap = new LinkedHashMap<>();
            equipmentReqDTO.getIncineratorFuelConsumptions().forEach(incineratorFuelConsumption -> {
                incineratorFuelConsumption.setIncineratorCrematories(crematories);
                incineratorFuelConsumption.setTotal(this.calculateAnnualFuelConsumption(incineratorFuelConsumption));
                incineratorFuelConsumption.setCreatedAt(System.currentTimeMillis());
                years.add(incineratorFuelConsumption.getYear());
                if (ObjectUtils.isEmpty(fuelConsumptionMap.get(incineratorFuelConsumption.getYear()))) {
                    fuelConsumptionMap.put(incineratorFuelConsumption.getYear(), incineratorFuelConsumption);
                }
            });
            final List<IncineratorFuelConsumption> fuelConsumptions = this.incineratorFuelConsumptionRepository.findByYearIn(years);
            fuelConsumptions.forEach(fuelConsumption -> fuelConsumptionMap.remove(fuelConsumption.getYear()));
            List<IncineratorFuelConsumption> allFuelConsumptions = new ArrayList<>(fuelConsumptionMap.values());
            this.incineratorFuelConsumptionRepository.saveAll(allFuelConsumptions);
        }
        return this.setEquipment(crematories, null, null);
    }

    public List<IncineratorCrematoriesResDTO> getIncineratorCrematories(final Long id, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        final List<IncineratorCrematories> incineratorCrematoriesList = new LinkedList<>();
        final List<IncineratorCrematoriesAgencyInfo> agencyInfoList = new LinkedList<>();
        final List<IncineratorFuelConsumption> incineratorFuelConsumptionList = new LinkedList<>();
        if (equipmentDetailsReqVO.getEquipmentType() != null && !equipmentDetailsReqVO.getEquipmentType().equalsIgnoreCase(ConstantStore.INCINERATOR_CREMATORIES)) {
            throw new ValidationError(ApplicationMessageStore.EQUIPMENT_TYPE_NOT_MATCH);
        }
        if (id != null) {
            final IncineratorCrematories incineratorCrematories = this.incineratorCrematoriesRepository.getById(id);
            if (incineratorCrematories == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            final List<IncineratorCrematoriesAgencyInfo> crematoriesAgencyInfoList = this.incineratorCrematoriesAgencyInfoRepository.findByIncineratorCrematories(incineratorCrematories);
            crematoriesAgencyInfoList.forEach(crematoriesAgencyInfo -> {
                crematoriesAgencyInfo.setIncineratorCrematories(null);
                agencyInfoList.add(crematoriesAgencyInfo);
            });
            incineratorCrematoriesList.add(incineratorCrematories);
            final List<IncineratorFuelConsumption> incineratorFuelConsumptions = this.incineratorFuelConsumptionRepository.findByIncineratorCrematories(incineratorCrematories);
            incineratorFuelConsumptions.forEach(incineratorFuelConsumption -> {
                incineratorFuelConsumption.setIncineratorCrematories(null);
                incineratorFuelConsumptionList.add(incineratorFuelConsumption);
            });
        } else {
            final Building building = this.buildingRepository.findByBuildingId(equipmentDetailsReqVO.getBuildingId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            if (equipmentDetailsReqVO.getFloor() == null) {
                incineratorCrematoriesList.addAll(this.incineratorCrematoriesRepository.findAllByBuilding(building));
            } else {
                incineratorCrematoriesList.addAll(this.incineratorCrematoriesRepository.findAllByBuildingAndFloor(building, equipmentDetailsReqVO.getFloor()));
            }
        }
        return incineratorCrematoriesList.stream().map(incineratorCrematories -> this.setEquipment(incineratorCrematories, agencyInfoList, incineratorFuelConsumptionList)).toList();
    }

    public IncineratorCrematoriesResDTO updateIncineratorCrematories(final Long id, final IncineratorCrematoriesReqDTO equipmentReqDTO) {
        return this.createIncineratorCrematories(equipmentReqDTO, id);
    }

    public void deleteIncineratorCrematories(final Long id, final boolean activeStatus, final boolean isPermanentDelete) {
        final User user = this.validatorService.validateUser();
        final String userRole = user.getRole().getRole();
        final IncineratorCrematories incineratorCrematories;
        if (isPermanentDelete && (userRole.equalsIgnoreCase(ConstantStore.ADMIN_SUPER_USER) || userRole.equalsIgnoreCase(ConstantStore.ADMIN_USER))) {
            incineratorCrematories = this.incineratorCrematoriesRepository.getById(id);
            if (incineratorCrematories == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            this.incineratorCrematoriesRepository.delete(incineratorCrematories);
            this.commonUtilService.removeEquipment(ConstantStore.INCINERATOR_CREMATORIES, incineratorCrematories.getBuilding(), 1L);
        } else {
            incineratorCrematories = this.incineratorCrematoriesRepository.findByIdAndIsActive(id, true);
            if (incineratorCrematories == null) {
                throw new ValidationError(ApplicationMessageStore.BOILER_NOT_FOUND);
            }
            incineratorCrematories.setIsActive(activeStatus);
            this.incineratorCrematoriesRepository.save(incineratorCrematories);
        }
    }

    private JobFilingInformation saveJobFilingInformation(final JobFilingInformation jobFilingInformation) {
        return this.jobFilingInformationRepository.save(jobFilingInformation);
    }

    private IncineratorCrematoriesResDTO setEquipment(final IncineratorCrematories incineratorCrematories, final List<IncineratorCrematoriesAgencyInfo> agencyInfoList, final List<IncineratorFuelConsumption> incineratorFuelConsumptionList) {
        final IncineratorCrematoriesResDTO equipmentResDTO = new IncineratorCrematoriesResDTO();
        equipmentResDTO.setId(incineratorCrematories.getId());
        equipmentResDTO.setFloor(incineratorCrematories.getFloor());
        equipmentResDTO.setLocation(incineratorCrematories.getLocation());
        equipmentResDTO.setInstalledOn(incineratorCrematories.getInstalledOn());
        equipmentResDTO.setMake(incineratorCrematories.getMake());
        equipmentResDTO.setInstalledBy(incineratorCrematories.getInstalledBy());
        equipmentResDTO.setManagedBy(incineratorCrematories.getManagedBy());
        equipmentResDTO.setSerialNo(incineratorCrematories.getSerialNo());
        equipmentResDTO.setDeviceType(ConstantStore.INCINERATOR_CREMATORIES);
        equipmentResDTO.setApplicationId(incineratorCrematories.getApplicationId());
        equipmentResDTO.setManagement(!ObjectUtils.isEmpty(incineratorCrematories.getManagement()) ? incineratorCrematories.getManagement().getId() : null);
        equipmentResDTO.setUnitType(!ObjectUtils.isEmpty(incineratorCrematories.getUnitType()) ? incineratorCrematories.getUnitType().getId() : null);
        equipmentResDTO.setBurnerModel(incineratorCrematories.getBurnerModel());
        equipmentResDTO.setBurnerSerialNumber(incineratorCrematories.getBurnerSerialNumber());
        equipmentResDTO.setBurnerCapacity(incineratorCrematories.getBurnerCapacity());
        equipmentResDTO.setWasteTypeBurner(!ObjectUtils.isEmpty(incineratorCrematories.getWasteTypeBurner()) ? incineratorCrematories.getWasteTypeBurner().getId() : null);
        equipmentResDTO.setScrubberInstalled(incineratorCrematories.isScrubberInstalled());
        equipmentResDTO.setCoMonitorInstalled(incineratorCrematories.isCoMonitorInstalled());
        equipmentResDTO.setOpacityMonitorInstalled(incineratorCrematories.isOpacityMonitorInstalled());
        equipmentResDTO.setO2MonitorInstalled(incineratorCrematories.isO2MonitorInstalled());
        equipmentResDTO.setOpacityChartRecorderAvailable(incineratorCrematories.isOpacityChartRecorderAvailable());
        equipmentResDTO.setQuarterlyCgaRequired(incineratorCrematories.isQuarterlyCgaRequired());
        equipmentResDTO.setTemperatureMeasurementRequired(incineratorCrematories.isTemperatureMeasurementRequired());
        equipmentResDTO.setMinimumTemperaturePrimary(incineratorCrematories.getMinimumTemperaturePrimary());
        equipmentResDTO.setMinimumTemperatureSecondary(incineratorCrematories.getMinimumTemperatureSecondary());
        equipmentResDTO.setAverageFacilityTemperaturePrimary(incineratorCrematories.getAverageFacilityTemperaturePrimary());
        equipmentResDTO.setAverageFacilityTemperatureSecondary(incineratorCrematories.getAverageFacilityTemperatureSecondary());
        equipmentResDTO.setStack(!ObjectUtils.isEmpty(incineratorCrematories.getStack()) ? incineratorCrematories.getStack().getStackId() : null);
        equipmentResDTO.setBuildingId(incineratorCrematories.getBuilding().getBuildingId());
        equipmentResDTO.setUniqueId(incineratorCrematories.getUniqueId());
        equipmentResDTO.setJobFilingInformation(!ObjectUtils.isEmpty(incineratorCrematories.getJobFilingInformation()) ? incineratorCrematories.getJobFilingInformation() : null);
        equipmentResDTO.setStatus(!ObjectUtils.isEmpty(incineratorCrematories.getStatus()) ? incineratorCrematories.getStatus().getStatusId() : null);
        equipmentResDTO.setAgencyInfoList(!ObjectUtils.isEmpty(agencyInfoList) ? this.setIncineratorCrematoriesAgencyDTO(agencyInfoList) : null);
        equipmentResDTO.setIncineratorFuelConsumptionList(incineratorFuelConsumptionList);
        return equipmentResDTO;
    }

    public List<UnitType> getUnitType() {
        return this.unitTypeRepository.findAll();
    }

    public List<WasteTypeBurner> getWasteTypeBurner() {
        return this.wasteTypeBurnerRepository.findAll();
    }


    public IncineratorFuelConsumption updateIncineratorFuelConsumption(final IncineratorFuelConsumption incineratorFuelConsumption) {
        final IncineratorFuelConsumption fuelConsumption = this.incineratorFuelConsumptionRepository.findById(incineratorFuelConsumption.getId())
                .orElseThrow(() -> new RuntimeException(ApplicationMessageStore.DATA_NOT_FOUND));
        fuelConsumption.setYear(incineratorFuelConsumption.getYear());
        fuelConsumption.setJanuary(incineratorFuelConsumption.getJanuary());
        fuelConsumption.setFebruary(incineratorFuelConsumption.getFebruary());
        fuelConsumption.setMarch(incineratorFuelConsumption.getMarch());
        fuelConsumption.setApril(incineratorFuelConsumption.getApril());
        fuelConsumption.setMay(incineratorFuelConsumption.getMay());
        fuelConsumption.setJune(incineratorFuelConsumption.getJune());
        fuelConsumption.setJuly(incineratorFuelConsumption.getJuly());
        fuelConsumption.setAugust(incineratorFuelConsumption.getAugust());
        fuelConsumption.setSeptember(incineratorFuelConsumption.getSeptember());
        fuelConsumption.setOctober(incineratorFuelConsumption.getOctober());
        fuelConsumption.setNovember(incineratorFuelConsumption.getNovember());
        fuelConsumption.setDecember(incineratorFuelConsumption.getDecember());
        fuelConsumption.setTotal(this.calculateAnnualFuelConsumption(incineratorFuelConsumption));
        fuelConsumption.setUpdatedAt(System.currentTimeMillis());
        return this.setFuelConsumption(this.incineratorFuelConsumptionRepository.save(fuelConsumption));
    }

    private IncineratorFuelConsumption setFuelConsumption(final IncineratorFuelConsumption incineratorFuelConsumption) {
        incineratorFuelConsumption.setIncineratorCrematories(null);
        return incineratorFuelConsumption;
    }

    private Double calculateAnnualFuelConsumption(final IncineratorFuelConsumption fuelConsumption) {
        return Stream.of(
                        fuelConsumption.getJanuary(),
                        fuelConsumption.getFebruary(),
                        fuelConsumption.getMarch(),
                        fuelConsumption.getApril(),
                        fuelConsumption.getMay(),
                        fuelConsumption.getJune(),
                        fuelConsumption.getJuly(),
                        fuelConsumption.getAugust(),
                        fuelConsumption.getSeptember(),
                        fuelConsumption.getOctober(),
                        fuelConsumption.getNovember(),
                        fuelConsumption.getDecember()
                )
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    private void saveAgency(final IncineratorCrematoriesReqDTO equipmentReqDTO, final IncineratorCrematories incineratorCrematories) {
        if (equipmentReqDTO.getAgencyInfoList() != null) {
            final List<IncineratorCrematoriesAgencyInfoDTO> agencyInfoList = equipmentReqDTO.getAgencyInfoList();
            final List<IncineratorCrematoriesAgencyInfo> incineratorCrematoriesAgencyInfos = new LinkedList<>();
            for (IncineratorCrematoriesAgencyInfoDTO agencyInfoDTO : agencyInfoList) {
                IncineratorCrematoriesAgencyInfo incineratorCrematoriesAgencyInfo;
                if (agencyInfoDTO.getId() != null) {
                    incineratorCrematoriesAgencyInfo = incineratorCrematoriesAgencyInfoRepository.findById(agencyInfoDTO.getId())
                            .orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
                } else {
                    incineratorCrematoriesAgencyInfo = new IncineratorCrematoriesAgencyInfo();
                }
                for (final Field field : IncineratorCrematoriesAgencyInfo.class.getDeclaredFields()) {
                    field.setAccessible(true);
                    if ((agencyInfoDTO.getId() == null && field.getName().equalsIgnoreCase("id")) || field.isAnnotationPresent(ManyToOne.class)) {
                        continue;
                    }
                    try {
                        final Object value = this.getFieldValue(agencyInfoDTO, field.getName());
                        if (value != null) {
                            field.set(incineratorCrematoriesAgencyInfo, value);
                        }
                    } catch (final NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                        throw new ValidationError("Error setting field: " + field.getName());
                    }
                }
                incineratorCrematoriesAgencyInfo.setIncineratorCrematories(incineratorCrematories);
                this.setIncineratorCrematoriesAgency(agencyInfoDTO, incineratorCrematoriesAgencyInfo);
                this.setParameter(agencyInfoDTO, incineratorCrematoriesAgencyInfo);
                incineratorCrematoriesAgencyInfos.add(incineratorCrematoriesAgencyInfo);
            }
            this.incineratorCrematoriesAgencyInfoRepository.saveAll(incineratorCrematoriesAgencyInfos);
        }
    }

    private void setIncineratorCrematoriesAgency(final IncineratorCrematoriesAgencyInfoDTO agencyInfoDTO, final IncineratorCrematoriesAgencyInfo incineratorCrematoriesAgencyInfo) {
        final Long incineratorCrematoriesAgencyId = agencyInfoDTO.getIncineratorCrematoriesAgency();
        if (incineratorCrematoriesAgencyId != null) {
            final IncineratorCrematoriesAgency incineratorCrematoriesAgency = this.incineratorCrematoriesAgencyRepository.findById(incineratorCrematoriesAgencyId)
                    .orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
            if (incineratorCrematoriesAgency == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            incineratorCrematoriesAgencyInfo.setIncineratorCrematoriesAgency(incineratorCrematoriesAgency);
        }
    }

    private void setParameter(final IncineratorCrematoriesAgencyInfoDTO agencyInfoDTO, final IncineratorCrematoriesAgencyInfo incineratorCrematoriesAgencyInfo) {
        final Long parameterTypesId = agencyInfoDTO.getParameterTypes();
        if (parameterTypesId != null) {
            final ParameterType parameterType = this.parameterTypesRepository.findById(parameterTypesId)
                    .orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
            if (parameterType == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            incineratorCrematoriesAgencyInfo.setParameterTypes(parameterType);
        }
    }


    private Object getFieldValue(final IncineratorCrematoriesAgencyInfoDTO agencyInfoDTO, final String fieldName) throws NoSuchFieldException, IllegalAccessException {
        final Field field = IncineratorCrematoriesAgencyInfoDTO.class.getDeclaredField(fieldName);
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
