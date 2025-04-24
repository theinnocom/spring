package com.inTrack.spring.service.equipmentService;

import com.inTrack.spring.UtilService.CommonUtilService;
import com.inTrack.spring.dto.common.CoolingTowerAgencyInfoDTO;
import com.inTrack.spring.dto.requestDTO.CoolingTowerReqDTO;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.dto.responseDTO.CoolingTowerResDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.User;
import com.inTrack.spring.entity.equipmentEntity.CoolingTower;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.TypeOfCollingTower;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.CoolingTowerAgencyInfo;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.CoolingTowerAgency;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.AgencyRepo.CoolingTowerAgencyInfoRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.CoolingTowerAgencyRepository;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
import com.inTrack.spring.repository.equipmentRepository.CoolingTowerRepository;
import com.inTrack.spring.repository.equipmentRepository.TypeOfCoolingTowerRepository;
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
public class CoolingTowerService {

    @Autowired
    private CoolingTowerRepository coolingTowerRepository;

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
    private TypeOfCoolingTowerRepository typeOfCoolingTowerRepository;

    @Autowired
    private CoolingTowerAgencyInfoRepository coolingTowerAgencyInfoRepository;

    @Autowired
    private CoolingTowerAgencyRepository coolingTowerAgencyRepository;

    @Transactional(rollbackOn = Exception.class)
    public CoolingTowerResDTO createCoolingTower(final CoolingTowerReqDTO equipmentReqDTO, final Long collingTowerId) {
        final CoolingTower coolingTower;
        final Building building = this.buildingRepository.findByBuildingId(equipmentReqDTO.getBuildingId());
        if (collingTowerId != null) {
            coolingTower = this.coolingTowerRepository.findByCoolingTowerId(collingTowerId);
        } else {
            final Long uniqueIdCount = this.coolingTowerRepository.countByUniqueId(equipmentReqDTO.getUniqueId());
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
            coolingTower = new CoolingTower();
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            coolingTower.setBuilding(building);
            coolingTower.setUniqueId(equipmentReqDTO.getUniqueId());
            this.commonUtilService.saveEquipment(ConstantStore.COOLING_TOWER, building, 1L);
            this.commonEquipmentService.createEquipmentFloorRelation(equipmentReqDTO.getFloor(), ConstantStore.COOLING_TOWER, building);
        }
        if (equipmentReqDTO.getJobFilingInformationReqDTO() != null) {
            if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformationReqDTO().getJobFilingId())) {
                this.commonEquipmentService.saveJobFiling(equipmentReqDTO.getJobFilingInformationReqDTO());
            } else {
                coolingTower.setJobFilingInformation(this.saveJobFilingInformation(this.setJobFilingInfo(equipmentReqDTO.getJobFilingInformationReqDTO())));
            }
        }
        coolingTower.setStatus(!ObjectUtils.isEmpty(equipmentReqDTO.getStatus()) ? new ElevatorStatus().setStatusId(equipmentReqDTO.getStatus()) : null);
        coolingTower.setFloor(equipmentReqDTO.getFloor());
        coolingTower.setLandmark(equipmentReqDTO.getLandmark());
        coolingTower.setLocation(equipmentReqDTO.getLocation());
        coolingTower.setInstalledOn(equipmentReqDTO.getInstalledOn());
        coolingTower.setMake(equipmentReqDTO.getMake());
        coolingTower.setInstalledBy(equipmentReqDTO.getInstalledBy());
        coolingTower.setManagedBy(equipmentReqDTO.getManagedBy());
        coolingTower.setSerialNo(equipmentReqDTO.getSerialNo());
        coolingTower.setManagementNote(equipmentReqDTO.getManagementNote());
        coolingTower.setManagement(!ObjectUtils.isEmpty(equipmentReqDTO.getManagement()) ? new ManagementType().setId(equipmentReqDTO.getManagement()) : null);
        coolingTower.setDeviceType(ConstantStore.COOLING_TOWER);
        coolingTower.setModel(equipmentReqDTO.getModel());
        coolingTower.setSerialNumber(equipmentReqDTO.getSerialNumber());
        coolingTower.setCoolingCapacity(equipmentReqDTO.getCoolingCapacity());
        coolingTower.setCoolingCapacityUnit(equipmentReqDTO.getCoolingCapacityUnit());
        coolingTower.setBasinCapacity(equipmentReqDTO.getBasinCapacity());
        coolingTower.setBasinCapacityUnit(equipmentReqDTO.getBasinCapacityUnit());
        coolingTower.setIntendedUse(equipmentReqDTO.getIntendedUse());
        coolingTower.setEquipmentName(equipmentReqDTO.getEquipmentName());
        coolingTower.setNumberOfCells(equipmentReqDTO.getNumberOfCells());
        coolingTower.setStateRegistrationCompleted(equipmentReqDTO.getStateRegistrationCompleted());
        coolingTower.setStateEquipmentId(equipmentReqDTO.getStateEquipmentId());
        coolingTower.setNycDobRegistrationNo(equipmentReqDTO.getNycDobRegistrationNo());
        coolingTower.setCommissioningDate(equipmentReqDTO.getCommissioningDate());
        coolingTower.setSecurelyAffixNycdobRegistration(equipmentReqDTO.getSecurelyAffixNycdobRegistration());
        coolingTower.setTypeOfCollingTower(!ObjectUtils.isEmpty(equipmentReqDTO.getTypeOfCollingTower()) ? new TypeOfCollingTower().setId(equipmentReqDTO.getTypeOfCollingTower()) : null);
        final CoolingTower tower = this.coolingTowerRepository.save(coolingTower);
        this.saveAgency(equipmentReqDTO, tower);
        return this.setEquipment(tower, null);
    }

    public List<CoolingTowerResDTO> getCoolingTower(final Long collingTowerId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        final List<CoolingTower> collingTowers = new LinkedList<>();
        final List<CoolingTowerAgencyInfo> coolingTowerAgencyInfoList = new LinkedList<>();
        if (equipmentDetailsReqVO.getEquipmentType() != null && !equipmentDetailsReqVO.getEquipmentType().equalsIgnoreCase(ConstantStore.COOLING_TOWER)) {
            throw new ValidationError(ApplicationMessageStore.EQUIPMENT_TYPE_NOT_MATCH);
        }
        if (collingTowerId != null) {
            final CoolingTower coolingTower = this.coolingTowerRepository.findByCoolingTowerId(collingTowerId);
            final List<CoolingTowerAgencyInfo> agencyInfoList = this.coolingTowerAgencyInfoRepository.findByCoolingTower(coolingTower);
            agencyInfoList.forEach(coolingTowerAgencyInfo -> {
                coolingTowerAgencyInfo.setCoolingTower(null);
                coolingTowerAgencyInfoList.add(coolingTowerAgencyInfo);
            });
            collingTowers.add(coolingTower);
        } else {
            final Building building = this.buildingRepository.findByBuildingId(equipmentDetailsReqVO.getBuildingId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            if (equipmentDetailsReqVO.getFloor() == null) {
                collingTowers.addAll(this.coolingTowerRepository.findAllByBuilding(building));
            } else {
                collingTowers.addAll(this.coolingTowerRepository.findAllByBuildingAndFloor(building, equipmentDetailsReqVO.getFloor()));
            }
        }
        if (collingTowers.isEmpty()) {
            throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
        }
        return collingTowers.stream().map(collingTower -> this.setEquipment(collingTower, coolingTowerAgencyInfoList)).toList();
    }

    public CoolingTowerResDTO updateCoolingTower(final Long collingTowerId, final CoolingTowerReqDTO equipmentReqDTO) {
        return this.createCoolingTower(equipmentReqDTO, collingTowerId);
    }

    public void deleteCoolingTower(final Long coolingTowerId, final boolean activeStatus, final boolean isPermanentDelete) {
        final User user = this.validatorService.validateUser();
        final String userRole = user.getRole().getRole();
        final CoolingTower collingTower;
        if (isPermanentDelete && (userRole.equalsIgnoreCase(ConstantStore.ADMIN_SUPER_USER) || userRole.equalsIgnoreCase(ConstantStore.ADMIN_USER))) {
            collingTower = this.coolingTowerRepository.findByCoolingTowerId(coolingTowerId);
            if (collingTower == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            this.commonUtilService.removeEquipment(ConstantStore.COOLING_TOWER, collingTower.getBuilding(), 1L);
            this.coolingTowerRepository.delete(collingTower);
        } else {
            collingTower = this.coolingTowerRepository.findByCoolingTowerIdAndIsActive(coolingTowerId, true);
            if (collingTower == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            collingTower.setIsActive(activeStatus);
            this.coolingTowerRepository.save(collingTower);
        }
    }

    public List<TypeOfCollingTower> getTypeOfCoolingTower(final String search) {
        return this.typeOfCoolingTowerRepository.getTypeOfCoolingTower(search != null ? search : "");
    }

    private JobFilingInformation saveJobFilingInformation(final JobFilingInformation jobFilingInformation) {
        return this.jobFilingInformationRepository.save(jobFilingInformation);
    }

    private CoolingTowerResDTO setEquipment(final CoolingTower coolingTower, List<CoolingTowerAgencyInfo> coolingTowerAgencyInfoList) {
        return CoolingTowerResDTO.builder()
                .coolingTowerId(coolingTower.getCoolingTowerId())
                .uniqueId(coolingTower.getUniqueId())
                .landmark(coolingTower.getLandmark())
                .isActive(coolingTower.getIsActive())
                .floor(coolingTower.getFloor())
                .location(coolingTower.getLocation())
                .installedOn(coolingTower.getInstalledOn())
                .make(coolingTower.getMake())
                .installedBy(coolingTower.getInstalledBy())
                .managedBy(coolingTower.getManagedBy())
                .serialNo(coolingTower.getSerialNo())
                .floor(coolingTower.getFloor())
                .location(coolingTower.getLocation())
                .installedOn(coolingTower.getInstalledOn())
                .installedBy(coolingTower.getInstalledBy())
                .managedBy(coolingTower.getManagedBy())
                .serialNo(coolingTower.getSerialNo())
                .managementNote(coolingTower.getManagementNote())
                .management(!ObjectUtils.isEmpty(coolingTower.getManagement()) ? coolingTower.getManagement().getId() : null)
                .deviceType(coolingTower.getDeviceType())
                .make(coolingTower.getMake())
                .model(coolingTower.getModel())
                .serialNumber(coolingTower.getSerialNumber())
                .coolingCapacity(coolingTower.getCoolingCapacity())
                .coolingCapacityUnit(coolingTower.getCoolingCapacityUnit())
                .basinCapacity(coolingTower.getBasinCapacity())
                .basinCapacityUnit(coolingTower.getBasinCapacityUnit())
                .intendedUse(coolingTower.getIntendedUse())
                .equipmentName(coolingTower.getEquipmentName())
                .numberOfCells(coolingTower.getNumberOfCells())
                .stateRegistrationCompleted(coolingTower.getStateRegistrationCompleted())
                .stateEquipmentId(coolingTower.getStateEquipmentId())
                .nycDobRegistrationNo(coolingTower.getNycDobRegistrationNo())
                .commissioningDate(coolingTower.getCommissioningDate())
                .securelyAffixNycdobRegistration(coolingTower.getSecurelyAffixNycdobRegistration())
                .status(!ObjectUtils.isEmpty(coolingTower.getStatus()) ? coolingTower.getStatus().getStatusId() : null)
                .typeOfCollingTower(!ObjectUtils.isEmpty(coolingTower.getTypeOfCollingTower()) ? coolingTower.getTypeOfCollingTower().getId() : null)
                .buildingId(coolingTower.getBuilding().getBuildingId())
                .jobFilingInformation(coolingTower.getJobFilingInformation())
                .agencyInfoList(!ObjectUtils.isEmpty(coolingTowerAgencyInfoList) ? this.setCoolingTowerAgencyInfo(coolingTowerAgencyInfoList) : null)
                .build();
    }


    private void saveAgency(final CoolingTowerReqDTO equipmentReqDTO, final CoolingTower coolingTower) {
        if (equipmentReqDTO.getCoolingTowerAgencyInfoList() != null) {
            final List<CoolingTowerAgencyInfoDTO> agencyInfoList = equipmentReqDTO.getCoolingTowerAgencyInfoList();
            final List<CoolingTowerAgencyInfo> coolingTowerAgencyInfoList = new LinkedList<>();
            for (CoolingTowerAgencyInfoDTO agencyInfoDTO : agencyInfoList) {
                CoolingTowerAgencyInfo coolingTowerAgencyInfo;
                if (agencyInfoDTO.getId() != null) {
                    coolingTowerAgencyInfo = coolingTowerAgencyInfoRepository.findById(agencyInfoDTO.getId())
                            .orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
                } else {
                    coolingTowerAgencyInfo = new CoolingTowerAgencyInfo();
                }
                for (final Field field : CoolingTowerAgencyInfo.class.getDeclaredFields()) {
                    field.setAccessible(true);
                    if ((agencyInfoDTO.getId() == null && field.getName().equalsIgnoreCase("id")) || field.isAnnotationPresent(ManyToOne.class)) {
                        continue;
                    }
                    try {
                        final Object value = this.getFieldValue(agencyInfoDTO, field.getName());
                        if (value != null) {
                            field.set(coolingTowerAgencyInfo, value);
                        }
                    } catch (final NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                        throw new ValidationError("Error setting field: " + field.getName());
                    }
                }
                coolingTowerAgencyInfo.setCoolingTower(coolingTower);
                this.setCoolingTowerAgency(agencyInfoDTO, coolingTowerAgencyInfo);
                coolingTowerAgencyInfoList.add(coolingTowerAgencyInfo);
            }
            this.coolingTowerAgencyInfoRepository.saveAll(coolingTowerAgencyInfoList);
        }
    }

    private void setCoolingTowerAgency(final CoolingTowerAgencyInfoDTO agencyInfoDTO, final CoolingTowerAgencyInfo coolingTowerAgencyInfo) {
        final Long coolingTowerAgencyId = agencyInfoDTO.getCoolingTowerAgency();
        if (coolingTowerAgencyId != null) {
            final CoolingTowerAgency coolingTowerAgency = this.coolingTowerAgencyRepository.findById(coolingTowerAgencyId)
                    .orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
            if (coolingTowerAgency == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            coolingTowerAgencyInfo.setCoolingTowerAgency(coolingTowerAgency);
        }
    }


    private Object getFieldValue(final CoolingTowerAgencyInfoDTO agencyInfoDTO, final String fieldName) throws NoSuchFieldException, IllegalAccessException {
        final Field field = CoolingTowerAgencyInfoDTO.class.getDeclaredField(fieldName);
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
}
