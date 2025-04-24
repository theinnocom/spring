package com.inTrack.spring.service.equipmentService;

import com.inTrack.spring.dto.common.BulkOxygenStorageAgencyInfoDTO;
import com.inTrack.spring.dto.requestDTO.BulkOxygenStorageReqDTO;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.dto.requestDTO.LandFillReqDTO;
import com.inTrack.spring.dto.responseDTO.BoilerCogenAgencyInfoDTO;
import com.inTrack.spring.dto.responseDTO.BulkOxygenStorageResDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.User;
import com.inTrack.spring.entity.equipmentEntity.BulkOxygenStorage;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.LandFill;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BulkOxygenStorageAgencyInfo;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.BoilerCogenAgency;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.BulkOxygenStorageAgency;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.AgencyRepo.BulkOxygenStorageAgencyInfoRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.BulkOxygenStorageAgencyRepository;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
import com.inTrack.spring.repository.equipmentRepository.BulkOxygenStorageRepository;
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
public class BulkOxygenStorageService {

    @Autowired
    private BulkOxygenStorageRepository bulkOxygenStorageRepository;

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
    private BulkOxygenStorageAgencyInfoRepository agencyInfoRepository;

    @Autowired
    private BulkOxygenStorageAgencyRepository bulkOxygenStorageAgencyRepository;

    @Transactional(rollbackOn = Exception.class)
    public BulkOxygenStorageResDTO createOxygenStorageTank(final BulkOxygenStorageReqDTO equipmentReqDTO, final Long oxygenStorageId) {
        final BulkOxygenStorage bulkOxygenStorage;
        final Building building = this.buildingRepository.findByBuildingId(equipmentReqDTO.getBuildingId());
        if (oxygenStorageId != null) {
            bulkOxygenStorage = this.bulkOxygenStorageRepository.findByOxygenStorageId(oxygenStorageId);
        } else {
            final Long uniqueIdCount = this.bulkOxygenStorageRepository.countByUniqueId(equipmentReqDTO.getUniqueId());
            final Long jobFillingIdCount = this.jobFilingInformationRepository.countByJobNumber(equipmentReqDTO.getJobFilingInformationReqDTO().getJobNumber());
            if (uniqueIdCount > 0 && jobFillingIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.UNIQUE_ID_AND_JOB_FILLING_ID_EXISTS);
            } else if (uniqueIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.UNIQUE_ID_EXISTS);
            } else if (jobFillingIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.JOB_FILLING_ID_EXISTS);
            }
            bulkOxygenStorage = new BulkOxygenStorage();
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            bulkOxygenStorage.setBuilding(building);
            bulkOxygenStorage.setUniqueId(equipmentReqDTO.getUniqueId());
            this.commonUtilService.saveEquipment(ConstantStore.BULK_OXYGEN_STORAGE, building, 1L);
            this.commonEquipmentService.createEquipmentFloorRelation(equipmentReqDTO.getFloor(), ConstantStore.BULK_OXYGEN_STORAGE, building);
        }
        if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformationReqDTO())) {
            if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformationReqDTO().getJobFilingId())) {
                this.commonEquipmentService.saveJobFiling(equipmentReqDTO.getJobFilingInformationReqDTO());
            } else {
                bulkOxygenStorage.setJobFilingInformation(this.jobFilingInformationRepository.save(this.setJobFilingInfo(equipmentReqDTO.getJobFilingInformationReqDTO())));
            }
        }
        bulkOxygenStorage.setFloor(equipmentReqDTO.getFloor());
        bulkOxygenStorage.setDisconnectedOn(equipmentReqDTO.getDisconnectedOn());
        bulkOxygenStorage.setManagement(!ObjectUtils.isEmpty(equipmentReqDTO.getManagement()) ? new ManagementType().setId(equipmentReqDTO.getManagement()) : null);
        bulkOxygenStorage.setCapacity(equipmentReqDTO.getCapacity());
        bulkOxygenStorage.setLocation(equipmentReqDTO.getLocation());
        bulkOxygenStorage.setInstalledOn(equipmentReqDTO.getInstalledOn());
        bulkOxygenStorage.setMakeBy(equipmentReqDTO.getMakeBy());
        bulkOxygenStorage.setInstalledBy(equipmentReqDTO.getInstalledBy());
        bulkOxygenStorage.setManagedBy(equipmentReqDTO.getManagedBy());
        bulkOxygenStorage.setSerialNo(equipmentReqDTO.getSerialNo());
        bulkOxygenStorage.setDeviceType(ConstantStore.BULK_OXYGEN_STORAGE);
        bulkOxygenStorage.setManagementNote(equipmentReqDTO.getManagementNote());
        bulkOxygenStorage.setFireDepartmentApprovalObtained(equipmentReqDTO.getFireDepartmentApprovalObtained());
        bulkOxygenStorage.setFireDepartmentApprovalNumber(equipmentReqDTO.getFireDepartmentApprovalNumber());
        bulkOxygenStorage.setPressureTestPerformed(equipmentReqDTO.isPressureTestPerformed());
        bulkOxygenStorage.setLastTestDate(equipmentReqDTO.getLastTestDate());
        bulkOxygenStorage.setNextTestDate(equipmentReqDTO.getNextTestDate());
        bulkOxygenStorage.setNote(equipmentReqDTO.getNote());
        bulkOxygenStorage.setEesJobNumber(equipmentReqDTO.getEesJobNumber());
        bulkOxygenStorage.setComments(equipmentReqDTO.getComments());
        bulkOxygenStorage.setApplicationId(equipmentReqDTO.getApplicationId());
        bulkOxygenStorage.setStatus(!ObjectUtils.isEmpty(equipmentReqDTO.getStatus()) ? new ElevatorStatus().setStatusId(equipmentReqDTO.getStatus()) : null);
        bulkOxygenStorage.setModel(equipmentReqDTO.getModel());
        final BulkOxygenStorage oxygenStorage = this.bulkOxygenStorageRepository.save(bulkOxygenStorage);
        this.saveAgency(equipmentReqDTO, oxygenStorage);
        return this.setEquipment(oxygenStorage, null);
    }

    public List<BulkOxygenStorageResDTO> getOxygenStorageTank(final Long oxygenStorageId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        final List<BulkOxygenStorage> bulkOxygenStorages = new LinkedList<>();
        final List<BulkOxygenStorageAgencyInfo> bulkOxygenStorageAgencyInfos = new LinkedList<>();
        if (equipmentDetailsReqVO.getEquipmentType() != null && !equipmentDetailsReqVO.getEquipmentType().equalsIgnoreCase(ConstantStore.BULK_OXYGEN_STORAGE)) {
            throw new ValidationError(ApplicationMessageStore.EQUIPMENT_TYPE_NOT_MATCH);
        }
        if (oxygenStorageId != null) {
            final BulkOxygenStorage bulkOxygenStorage = this.bulkOxygenStorageRepository.findByOxygenStorageId(oxygenStorageId);
            final List<BulkOxygenStorageAgencyInfo> agencyInfoList = this.agencyInfoRepository.findByBulkOxygenStorage(bulkOxygenStorage);
            agencyInfoList.forEach(agencyInfo -> {
                agencyInfo.setBulkOxygenStorage(null);
                bulkOxygenStorageAgencyInfos.add(agencyInfo);
            });
            bulkOxygenStorages.add(bulkOxygenStorage);
        } else {
            final Building building = this.buildingRepository.findByBuildingId(equipmentDetailsReqVO.getBuildingId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            if (equipmentDetailsReqVO.getFloor() == null) {
                bulkOxygenStorages.addAll(this.bulkOxygenStorageRepository.findAllByBuilding(building));
            } else {
                bulkOxygenStorages.addAll(this.bulkOxygenStorageRepository.findAllByBuildingAndFloor(building, equipmentDetailsReqVO.getFloor()));
            }
        }
        if (bulkOxygenStorages.isEmpty()) {
            throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
        }
        return bulkOxygenStorages.stream().map(bulkOxygenStorage -> this.setEquipment(bulkOxygenStorage, bulkOxygenStorageAgencyInfos)).toList();
    }

    public BulkOxygenStorageResDTO updateOxygenStorageTank(final Long oxygenStorageId, final BulkOxygenStorageReqDTO equipmentReqDTO) {
        return this.createOxygenStorageTank(equipmentReqDTO, oxygenStorageId);
    }

    public void deleteOxygenStorageTank(final Long oxygenStorageId, final boolean activeStatus, final boolean isPermanentDelete) {
        final User user = this.validatorService.validateUser();
        final String userRole = user.getRole().getRole();
        final BulkOxygenStorage bulkOxygenStorage;
        if (isPermanentDelete && (userRole.equalsIgnoreCase(ConstantStore.ADMIN_SUPER_USER) || userRole.equalsIgnoreCase(ConstantStore.ADMIN_USER))) {
            bulkOxygenStorage = this.bulkOxygenStorageRepository.findByOxygenStorageId(oxygenStorageId);
            if (oxygenStorageId == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            this.bulkOxygenStorageRepository.delete(bulkOxygenStorage);
            this.commonUtilService.removeEquipment(ConstantStore.BULK_OXYGEN_STORAGE, bulkOxygenStorage.getBuilding(), 1L);
        } else {
            bulkOxygenStorage = this.bulkOxygenStorageRepository.findByOxygenStorageIdAndIsActive(oxygenStorageId, true);
            if (bulkOxygenStorage == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            bulkOxygenStorage.setIsActive(activeStatus);
            this.bulkOxygenStorageRepository.save(bulkOxygenStorage);
        }
    }

    private JobFilingInformation saveJobFilingInformation(final JobFilingInformation jobFilingInformation) {
        return this.jobFilingInformationRepository.save(jobFilingInformation);
    }

    private BulkOxygenStorageResDTO setEquipment(final BulkOxygenStorage bulkOxygenStorages, List<BulkOxygenStorageAgencyInfo> bulkOxygenStorageAgencyInfos) {
        return BulkOxygenStorageResDTO.builder()
                .id(bulkOxygenStorages.getOxygenStorageId())
                .managementNote(bulkOxygenStorages.getManagementNote())
                .landmark(bulkOxygenStorages.getLandmark())
                .uniqueId(bulkOxygenStorages.getUniqueId())
                .floor(bulkOxygenStorages.getFloor())
                .location(bulkOxygenStorages.getLocation())
                .installedOn(bulkOxygenStorages.getInstalledOn())
                .makeBy(bulkOxygenStorages.getMakeBy())
                .installedBy(bulkOxygenStorages.getInstalledBy())
                .managedBy(bulkOxygenStorages.getManagedBy())
                .serialNo(bulkOxygenStorages.getSerialNo())
                .deviceType(ConstantStore.BULK_OXYGEN_STORAGE)
                .jobFilingInformation(!ObjectUtils.isEmpty(bulkOxygenStorages.getJobFilingInformation()) ? bulkOxygenStorages.getJobFilingInformation() : null)
                .lastTestDate(bulkOxygenStorages.getLastTestDate())
                .capacity(bulkOxygenStorages.getCapacity())
                .comments(bulkOxygenStorages.getComments())
                .note(bulkOxygenStorages.getNote())
                .eesJobNumber(bulkOxygenStorages.getEesJobNumber())
                .disconnectedOn(bulkOxygenStorages.getDisconnectedOn())
                .fireDepartmentApprovalNumber(bulkOxygenStorages.getFireDepartmentApprovalNumber())
                .fireDepartmentApprovalObtained(bulkOxygenStorages.getFireDepartmentApprovalObtained())
                .nextTestDate(bulkOxygenStorages.getNextTestDate())
                .pressureTestPerformed(bulkOxygenStorages.isPressureTestPerformed())
                .management(!ObjectUtils.isEmpty(bulkOxygenStorages.getManagement()) ? bulkOxygenStorages.getManagement().getId() : null)
                .isActive(bulkOxygenStorages.getIsActive())
                .applicationId(bulkOxygenStorages.getApplicationId())
                .status(!ObjectUtils.isEmpty(bulkOxygenStorages.getStatus()) ? bulkOxygenStorages.getStatus().getStatusId() : null)
                .agencyInfoList(!ObjectUtils.isEmpty(bulkOxygenStorageAgencyInfos) ? this.setBulkOxygenStorageAgencyInfoDTO(bulkOxygenStorageAgencyInfos) : null)
                .model(bulkOxygenStorages.getModel())
                .build();
    }


    private void saveAgency(final BulkOxygenStorageReqDTO equipmentReqDTO, final BulkOxygenStorage bulkOxygenStorage) {
        if (equipmentReqDTO.getAgencyInfoList() != null) {
            final List<BulkOxygenStorageAgencyInfoDTO> agencyInfoList = equipmentReqDTO.getAgencyInfoList();
            final List<BulkOxygenStorageAgencyInfo> bulkOxygenStorageAgencyInfos = new LinkedList<>();
            for (BulkOxygenStorageAgencyInfoDTO agencyInfoDTO : agencyInfoList) {
                BulkOxygenStorageAgencyInfo agencyInfo;
                if (agencyInfoDTO.getId() != null) {
                    agencyInfo = agencyInfoRepository.findById(agencyInfoDTO.getId())
                            .orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
                } else {
                    agencyInfo = new BulkOxygenStorageAgencyInfo();
                }
                for (final Field field : BulkOxygenStorageAgencyInfo.class.getDeclaredFields()) {
                    field.setAccessible(true);
                    if ((agencyInfoDTO.getId() == null && field.getName().equalsIgnoreCase("id")) || field.isAnnotationPresent(ManyToOne.class)) {
                        continue;
                    }
                    try {
                        final Object value = this.getFieldValue(agencyInfoDTO, field.getName());
                        if (value != null) {
                            field.set(agencyInfo, value);
                        }
                    } catch (final NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                        throw new ValidationError("Error setting field: " + field.getName());
                    }
                }
                agencyInfo.setBulkOxygenStorage(bulkOxygenStorage);
                this.setBulkOxygenStorageAgency(agencyInfoDTO, agencyInfo);
                bulkOxygenStorageAgencyInfos.add(agencyInfo);
            }
            this.agencyInfoRepository.saveAll(bulkOxygenStorageAgencyInfos);
        }
    }

    private void setBulkOxygenStorageAgency(final BulkOxygenStorageAgencyInfoDTO agencyInfoDTO, final BulkOxygenStorageAgencyInfo bulkOxygenStorageAgencyInfo) {
        final Long bulkOxygenStorageAgencyId = agencyInfoDTO.getBulkOxygenStorageAgency();
        if (bulkOxygenStorageAgencyId != null) {
            final BulkOxygenStorageAgency bulkOxygenStorageAgency = this.bulkOxygenStorageAgencyRepository.findById(bulkOxygenStorageAgencyId)
                    .orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
            bulkOxygenStorageAgencyInfo.setBulkOxygenStorageAgency(bulkOxygenStorageAgency);
        }
    }

    private Object getFieldValue(final BulkOxygenStorageAgencyInfoDTO agencyInfoDTO, final String fieldName) throws NoSuchFieldException, IllegalAccessException {
        final Field field = BulkOxygenStorageAgencyInfoDTO.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(agencyInfoDTO);
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

    private List<BulkOxygenStorageAgencyInfoDTO> setBulkOxygenStorageAgencyInfoDTO(final List<BulkOxygenStorageAgencyInfo> bulkOxygenStorageAgencyInfos) {
        final List<BulkOxygenStorageAgencyInfoDTO> bulkOxygenStorageAgencyInfoDTOS = new LinkedList<>();
        bulkOxygenStorageAgencyInfos.forEach(bulkOxygenStorageAgencyInfo -> {
            final BulkOxygenStorageAgencyInfoDTO bulkOxygenStorageAgencyInfoDTO = new BulkOxygenStorageAgencyInfoDTO();
            bulkOxygenStorageAgencyInfoDTO.setId(bulkOxygenStorageAgencyInfo.getId());
            bulkOxygenStorageAgencyInfoDTO.setPermitId(bulkOxygenStorageAgencyInfo.getPermitId());
            bulkOxygenStorageAgencyInfoDTO.setExpirationDate(bulkOxygenStorageAgencyInfo.getExpirationDate());
            bulkOxygenStorageAgencyInfoDTO.setIssueDate(bulkOxygenStorageAgencyInfo.getIssueDate());
            bulkOxygenStorageAgencyInfoDTO.setFireDepartmentPermitObtained(bulkOxygenStorageAgencyInfo.getFireDepartmentPermitObtained());
            bulkOxygenStorageAgencyInfoDTO.setLastTestDate(bulkOxygenStorageAgencyInfo.getLastTestDate());
            bulkOxygenStorageAgencyInfoDTO.setNextTestDate(bulkOxygenStorageAgencyInfo.getNextTestDate());
            bulkOxygenStorageAgencyInfoDTO.setTestPerformed(bulkOxygenStorageAgencyInfo.getTestPerformed());
            bulkOxygenStorageAgencyInfoDTO.setNote(bulkOxygenStorageAgencyInfo.getNote());
            bulkOxygenStorageAgencyInfoDTOS.add(bulkOxygenStorageAgencyInfoDTO);
        });
        return bulkOxygenStorageAgencyInfoDTOS;
    }

}
