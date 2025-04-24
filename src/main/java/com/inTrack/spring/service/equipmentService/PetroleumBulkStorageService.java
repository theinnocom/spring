package com.inTrack.spring.service.equipmentService;

import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.dto.requestDTO.PetroleumBulkStorageReqDTO;
import com.inTrack.spring.dto.responseDTO.PetroleumBulkStorageResDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.User;
import com.inTrack.spring.entity.equipmentEntity.PetroleumBulkStorage;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.PetroleumBulkStorageAgency;
import com.inTrack.spring.entity.equipmentEntity.petroleumDropDown.*;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.AgencyRepo.PetroleumBulkOxygenAgencyRepository;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
import com.inTrack.spring.repository.equipmentRepository.PetroleumBulkStorageRepository;
import com.inTrack.spring.UtilService.CommonUtilService;
import com.inTrack.spring.service.ValidatorService;
import com.inTrack.spring.store.ApplicationMessageStore;
import com.inTrack.spring.store.ConstantStore;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author vijayan
 */

@Service
@RequiredArgsConstructor
public class PetroleumBulkStorageService {

    private final PetroleumBulkStorageRepository petroleumBulkStorageRepository;
    private final ValidatorService validatorService;
    private final BuildingRepository buildingRepository;
    private final CommonUtilService commonUtilService;
    private final JobFilingInformationRepository jobFilingInformationRepository;
    private final CommonEquipmentService commonEquipmentService;
    private final PetroleumBulkOxygenAgencyRepository petroleumBulkOxygenAgencyRepository;

    @Transactional(rollbackOn = Exception.class)
    public PetroleumBulkStorageResDTO createPetroleumBulkStorage(final PetroleumBulkStorageReqDTO equipmentReqDTO, final Long petroleumStorageId) {
        final PetroleumBulkStorage petroleumBulkStorage;
        final Building building = this.buildingRepository.findByBuildingId(equipmentReqDTO.getBuildingId());
        if (petroleumStorageId != null) {
            petroleumBulkStorage = this.petroleumBulkStorageRepository.findByPetroleumStorageId(petroleumStorageId);
        } else {
            final Long uniqueIdCount = this.petroleumBulkStorageRepository.countByUniqueId(equipmentReqDTO.getUniqueId());
            final Long jobFillingIdCount = this.jobFilingInformationRepository.countByJobNumber(equipmentReqDTO.getJobFilingInformationReqDTO().getJobNumber());
            if (uniqueIdCount > 0 && jobFillingIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.UNIQUE_ID_AND_JOB_FILLING_ID_EXISTS);
            } else if (uniqueIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.UNIQUE_ID_EXISTS);
            } else if (jobFillingIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.JOB_FILLING_ID_EXISTS);
            }
            petroleumBulkStorage = new PetroleumBulkStorage();
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            petroleumBulkStorage.setBuilding(building);
            this.commonUtilService.saveEquipment(ConstantStore.PETROLEUM_BULK_STORAGE, building, 1L);
            this.commonEquipmentService.createEquipmentFloorRelation(equipmentReqDTO.getFloor(), ConstantStore.PETROLEUM_BULK_STORAGE, building);
        }
        if (equipmentReqDTO.getJobFilingInformationReqDTO() != null) {
            if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformationReqDTO().getJobFilingId())) {
                this.commonEquipmentService.saveJobFiling(equipmentReqDTO.getJobFilingInformationReqDTO());
            } else {
                petroleumBulkStorage.setJobFilingInformation(this.saveJobFilingInformation(this.setJobFilingInfo(equipmentReqDTO.getJobFilingInformationReqDTO())));
            }
        }
        petroleumBulkStorage.setStatus(equipmentReqDTO.getStatus());
        petroleumBulkStorage.setCategory(equipmentReqDTO.getCategory());
        petroleumBulkStorage.setDispenser(!ObjectUtils.isEmpty(equipmentReqDTO.getDispenser()) ? new Dispenser().setId(equipmentReqDTO.getDispenser()) : null);
        petroleumBulkStorage.setCapacity(equipmentReqDTO.getCapacity());
        petroleumBulkStorage.setDisconnectedOn(equipmentReqDTO.getDisconnectedOn());
        petroleumBulkStorage.setDeviceType(ConstantStore.PETROLEUM_BULK_STORAGE);
        petroleumBulkStorage.setLandmark(equipmentReqDTO.getLandmark());
        petroleumBulkStorage.setManagement(!ObjectUtils.isEmpty(equipmentReqDTO.getManagement()) ? new ManagementType().setId(equipmentReqDTO.getManagement()) : null);
        petroleumBulkStorage.setModel(equipmentReqDTO.getModel());
        petroleumBulkStorage.setUniqueId(equipmentReqDTO.getUniqueId());
        petroleumBulkStorage.setTankType(!ObjectUtils.isEmpty(equipmentReqDTO.getTankType()) ? new TankType().setId(equipmentReqDTO.getTankType()) : null);
        petroleumBulkStorage.setUdc(equipmentReqDTO.getUdc());
        petroleumBulkStorage.setTankStatus(!ObjectUtils.isEmpty(equipmentReqDTO.getTankStatus()) ? new TankStatus().setId(equipmentReqDTO.getTankStatus()) : null);
        petroleumBulkStorage.setTankSecondaryContainment(!ObjectUtils.isEmpty(equipmentReqDTO.getTankSecondaryContainment()) ? new TankSecondaryContainment().setId(equipmentReqDTO.getTankSecondaryContainment()) : null);
        petroleumBulkStorage.setTankNumber(equipmentReqDTO.getTankNumber());
        petroleumBulkStorage.setTankLocation(!ObjectUtils.isEmpty(equipmentReqDTO.getTankLocation()) ? new TankLocation().setId(equipmentReqDTO.getTankLocation()) : null);
        petroleumBulkStorage.setTankLeakDetection(!ObjectUtils.isEmpty(equipmentReqDTO.getTankLeakDetection()) ? new TankLeakDetection().setId(equipmentReqDTO.getTankLeakDetection()) : null);
        petroleumBulkStorage.setTankInternalProtection(!ObjectUtils.isEmpty(equipmentReqDTO.getTankInternalProtection()) ? new InternalProtection().setId(equipmentReqDTO.getTankInternalProtection()) : null);
        petroleumBulkStorage.setTankExternalProtection(!ObjectUtils.isEmpty(equipmentReqDTO.getTankExternalProtection()) ? new ExternalProtection().setId(equipmentReqDTO.getTankExternalProtection()) : null);
        petroleumBulkStorage.setTankClosedDate(equipmentReqDTO.getTankClosedDate());
        petroleumBulkStorage.setTankCapacity(equipmentReqDTO.getTankCapacity());
        petroleumBulkStorage.setSubpart(!ObjectUtils.isEmpty(equipmentReqDTO.getSubpart()) ? new Subpart().setId(equipmentReqDTO.getSubpart()) : null);
        petroleumBulkStorage.setSpillPrevention(!ObjectUtils.isEmpty(equipmentReqDTO.getSpillPrevention()) ? new SpillPrevention().setId(equipmentReqDTO.getSpillPrevention()) : null);
        petroleumBulkStorage.setSecondaryOverfill(equipmentReqDTO.getSecondaryOverfill());
        petroleumBulkStorage.setSerialNo(equipmentReqDTO.getSerialNo());
        petroleumBulkStorage.setProductStored(!ObjectUtils.isEmpty(equipmentReqDTO.getProductStored()) ? new ProductStored().setId(equipmentReqDTO.getProductStored()) : null);
        petroleumBulkStorage.setPipingSecondaryContainment(!ObjectUtils.isEmpty(equipmentReqDTO.getPipingSecondaryContainment()) ? new PipingSecondaryContainment().setId(equipmentReqDTO.getPipingSecondaryContainment()) : null);
        petroleumBulkStorage.setPipingLeakDetection(!ObjectUtils.isEmpty(equipmentReqDTO.getPipingLeakDetection()) ? new PipeLeakDetection().setId(equipmentReqDTO.getPipingLeakDetection()) : null);
        petroleumBulkStorage.setPipeType(!ObjectUtils.isEmpty(equipmentReqDTO.getPipeType()) ? new PipingType().setId(equipmentReqDTO.getPipeType()) : null);
        petroleumBulkStorage.setPipeLocation(!ObjectUtils.isEmpty(equipmentReqDTO.getPipeLocation()) ? new PipingLocation().setId(equipmentReqDTO.getPipeLocation()) : null);
        petroleumBulkStorage.setPipeExternalProtection(!ObjectUtils.isEmpty(equipmentReqDTO.getPipeExternalProtection()) ? new ExternalProtection().setId(equipmentReqDTO.getPipeExternalProtection()) : null);
        petroleumBulkStorage.setPbsNumber(equipmentReqDTO.getPbsNumber());
        petroleumBulkStorage.setOverFillProtection(!ObjectUtils.isEmpty(equipmentReqDTO.getOverfill()) ? new OverFillProtection().setId(equipmentReqDTO.getOverfill()) : null);
        petroleumBulkStorage.setManagementNote(equipmentReqDTO.getManagementNote());
        petroleumBulkStorage.setManagedBy(equipmentReqDTO.getManagedBy());
        petroleumBulkStorage.setFloor(equipmentReqDTO.getFloor());
        petroleumBulkStorage.setLocation(equipmentReqDTO.getLocation());
        petroleumBulkStorage.setInstalledOn(equipmentReqDTO.getInstalledOn());
        petroleumBulkStorage.setMake(equipmentReqDTO.getMake());
        petroleumBulkStorage.setInstalledBy(equipmentReqDTO.getInstalledBy());
        petroleumBulkStorage.setManagedBy(equipmentReqDTO.getManagedBy());
        petroleumBulkStorage.setSerialNo(equipmentReqDTO.getSerialNo());
        petroleumBulkStorage.setTankSecondaryContainmentOtherList(equipmentReqDTO.getTankSecondaryContainmentOtherList());
        petroleumBulkStorage.setTankLeakDetectionOtherList(equipmentReqDTO.getTankLeakDetectionOtherList());
        petroleumBulkStorage.setTankInternalProductionOtherList(equipmentReqDTO.getTankInternalProductionOtherList());
        petroleumBulkStorage.setTankExternalProductionOtherList(equipmentReqDTO.getTankExternalProductionOtherList());
        petroleumBulkStorage.setSpillPreventionOtherList(equipmentReqDTO.getSpillPreventionOtherList());
        petroleumBulkStorage.setPipingSecondaryContainmentOtherList(equipmentReqDTO.getPipingSecondaryContainmentOtherList());
        petroleumBulkStorage.setPipingLeakDetectionOtherList(equipmentReqDTO.getPipingLeakDetectionOtherList());
        petroleumBulkStorage.setPipeTypeOtherList(equipmentReqDTO.getPipeTypeOtherList());
        petroleumBulkStorage.setOverFillProtectionOtherList(equipmentReqDTO.getOverFillProtectionOtherList());
        petroleumBulkStorage.setPipingInternalProductionOtherList(equipmentReqDTO.getPipingInternalProductionOtherList());
        petroleumBulkStorage.setTankTypeOtherList(equipmentReqDTO.getTankTypeOtherList());
        petroleumBulkStorage.setNote(equipmentReqDTO.getNote());
        final PetroleumBulkStorage storage = this.petroleumBulkStorageRepository.save(petroleumBulkStorage);
        final List<PetroleumBulkStorageAgency> petroleumBulkStorageAgencies = new LinkedList<>();
        final List<PetroleumBulkStorageAgency> petroleumBulkStorageAgency = equipmentReqDTO.getPetroleumBulkStorageAgency();
        petroleumBulkStorageAgency.forEach(bulkStorageAgency -> {
            bulkStorageAgency.setPetroleumBulkStorage(storage);
            petroleumBulkStorageAgencies.add(bulkStorageAgency);
        });
        this.petroleumBulkOxygenAgencyRepository.saveAll(petroleumBulkStorageAgencies);
        return this.setEquipment(storage, null);
    }

    public List<PetroleumBulkStorageResDTO> getPetroleumBulkStorage(final Long petroleumStorageId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        final List<PetroleumBulkStorage> petroleumBulkStorages = new LinkedList<>();
        List<PetroleumBulkStorageAgency> petroleumBulkStorageAgency = new LinkedList<>();
        if (equipmentDetailsReqVO.getEquipmentType() != null && !equipmentDetailsReqVO.getEquipmentType().equalsIgnoreCase(ConstantStore.PETROLEUM_BULK_STORAGE)) {
            throw new ValidationError(ApplicationMessageStore.EQUIPMENT_TYPE_NOT_MATCH);
        }
        if (petroleumStorageId != null) {
            final PetroleumBulkStorage petroleumBulkStorage = this.petroleumBulkStorageRepository.findByPetroleumStorageId(petroleumStorageId);
            petroleumBulkStorages.add(petroleumBulkStorage);
            petroleumBulkStorageAgency.addAll(this.petroleumBulkOxygenAgencyRepository.findByPetroleumBulkStorage(petroleumBulkStorage));
        } else {
            final Building building = this.buildingRepository.findByBuildingId(equipmentDetailsReqVO.getBuildingId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            if (equipmentDetailsReqVO.getFloor() == null) {
                petroleumBulkStorages.addAll(this.petroleumBulkStorageRepository.findAllByBuilding(building));
            } else {
                petroleumBulkStorages.addAll(this.petroleumBulkStorageRepository.findAllByBuildingAndFloor(building, equipmentDetailsReqVO.getFloor()));
            }
        }
        if (petroleumBulkStorages.isEmpty()) {
            throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
        }
        return petroleumBulkStorages.stream().map(petroleumBulkStorage -> this.setEquipment(petroleumBulkStorage, petroleumBulkStorageAgency)).toList();
    }

    public PetroleumBulkStorageResDTO updatePetroleumBulkStorage(final Long petroleumStorageId, final PetroleumBulkStorageReqDTO equipmentReqDTO) {
        return this.createPetroleumBulkStorage(equipmentReqDTO, petroleumStorageId);
    }

    public void deletePetroleumBulkStorage(final Long petroleumStorageId, final boolean activeStatus, final boolean isPermanentDelete) {
        final User user = this.validatorService.validateUser();
        final String userRole = user.getRole().getRole();
        final PetroleumBulkStorage petroleumBulkStorage;
        if (isPermanentDelete && (userRole.equalsIgnoreCase(ConstantStore.ADMIN_SUPER_USER) || userRole.equalsIgnoreCase(ConstantStore.ADMIN_USER))) {
            petroleumBulkStorage = this.petroleumBulkStorageRepository.findByPetroleumStorageId(petroleumStorageId);
            if (petroleumStorageId == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            this.petroleumBulkStorageRepository.delete(petroleumBulkStorage);
            this.commonUtilService.removeEquipment(ConstantStore.PETROLEUM_BULK_STORAGE, petroleumBulkStorage.getBuilding(), 1L);
        } else {
            petroleumBulkStorage = this.petroleumBulkStorageRepository.findByPetroleumStorageIdAndIsActive(petroleumStorageId, true);
            if (petroleumStorageId == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            petroleumBulkStorage.setIsActive(activeStatus);
            this.petroleumBulkStorageRepository.save(petroleumBulkStorage);
        }
    }

    private JobFilingInformation saveJobFilingInformation(final JobFilingInformation jobFilingInformation) {
        return this.jobFilingInformationRepository.save(jobFilingInformation);
    }

    private PetroleumBulkStorageResDTO setEquipment(final PetroleumBulkStorage petroleumBulkStorage, final List<PetroleumBulkStorageAgency> petroleumBulkStorageAgency) {
        final PetroleumBulkStorageResDTO equipmentResDTO = new PetroleumBulkStorageResDTO();
        equipmentResDTO.setPetroleumStorageId(petroleumBulkStorage.getPetroleumStorageId());
        equipmentResDTO.setIsActive(petroleumBulkStorage.getIsActive());
        equipmentResDTO.setCategory(petroleumBulkStorage.getCategory());
        equipmentResDTO.setDispenser(!ObjectUtils.isEmpty(petroleumBulkStorage.getDispenser()) ? petroleumBulkStorage.getDispenser().getId() : null);
        equipmentResDTO.setCapacity(petroleumBulkStorage.getCapacity());
        equipmentResDTO.setDisconnectedOn(petroleumBulkStorage.getDisconnectedOn());
        equipmentResDTO.setDeviceType(petroleumBulkStorage.getDeviceType());
        equipmentResDTO.setLandmark(petroleumBulkStorage.getLandmark());
        equipmentResDTO.setManagement(!ObjectUtils.isEmpty(petroleumBulkStorage.getManagement()) ? petroleumBulkStorage.getManagement().getId() : null);
        equipmentResDTO.setModel(petroleumBulkStorage.getModel());
        equipmentResDTO.setUniqueId(petroleumBulkStorage.getUniqueId());
        equipmentResDTO.setTankType(!ObjectUtils.isEmpty(petroleumBulkStorage.getTankType()) ? petroleumBulkStorage.getTankType().getId() : null);
        equipmentResDTO.setUdc(petroleumBulkStorage.getUdc());
        equipmentResDTO.setTankStatus(!ObjectUtils.isEmpty(petroleumBulkStorage.getTankStatus()) ? petroleumBulkStorage.getTankStatus().getId() : null);
        equipmentResDTO.setTankSecondaryContainment(!ObjectUtils.isEmpty(petroleumBulkStorage.getTankSecondaryContainment()) ? petroleumBulkStorage.getTankSecondaryContainment().getId() : null);
        equipmentResDTO.setTankNumber(petroleumBulkStorage.getTankNumber());
        equipmentResDTO.setTankLocation(!ObjectUtils.isEmpty(petroleumBulkStorage.getTankLocation()) ? petroleumBulkStorage.getTankLocation().getId() : null);
        equipmentResDTO.setTankLeakDetection(!ObjectUtils.isEmpty(petroleumBulkStorage.getTankLeakDetection()) ? petroleumBulkStorage.getTankLeakDetection().getId() : null);
        equipmentResDTO.setTankInternalProtection(!ObjectUtils.isEmpty(petroleumBulkStorage.getTankInternalProtection()) ? petroleumBulkStorage.getTankInternalProtection().getId() : null);
        equipmentResDTO.setTankExternalProtection(!ObjectUtils.isEmpty(petroleumBulkStorage.getTankExternalProtection()) ? petroleumBulkStorage.getTankExternalProtection().getId() : null);
        equipmentResDTO.setTankClosedDate(petroleumBulkStorage.getTankClosedDate());
        equipmentResDTO.setTankCapacity(petroleumBulkStorage.getTankCapacity());
        equipmentResDTO.setSubpart(!ObjectUtils.isEmpty(petroleumBulkStorage.getSubpart()) ? petroleumBulkStorage.getSubpart().getId() : null);
        equipmentResDTO.setSpillPrevention(!ObjectUtils.isEmpty(petroleumBulkStorage.getSpillPrevention()) ? petroleumBulkStorage.getSpillPrevention().getId() : null);
        equipmentResDTO.setSecondaryOverfill(petroleumBulkStorage.getSecondaryOverfill());
        equipmentResDTO.setSerialNo(petroleumBulkStorage.getSerialNo());
        equipmentResDTO.setProductStored(!ObjectUtils.isEmpty(petroleumBulkStorage.getProductStored()) ? petroleumBulkStorage.getProductStored().getId() : null);
        equipmentResDTO.setPipingSecondaryContainment(!ObjectUtils.isEmpty(petroleumBulkStorage.getPipingSecondaryContainment()) ? petroleumBulkStorage.getPipingSecondaryContainment().getId() : null);
        equipmentResDTO.setPipingLeakDetection(!ObjectUtils.isEmpty(petroleumBulkStorage.getPipingLeakDetection()) ? petroleumBulkStorage.getPipingSecondaryContainment().getId() : null);
        equipmentResDTO.setPipeType(!ObjectUtils.isEmpty(petroleumBulkStorage.getPipeType()) ? petroleumBulkStorage.getPipeType().getId() : null);
        equipmentResDTO.setPipeLocation(!ObjectUtils.isEmpty(petroleumBulkStorage.getPipeLocation()) ? petroleumBulkStorage.getPipeLocation().getId() : null);
        equipmentResDTO.setPipeExternalProtection(!ObjectUtils.isEmpty(petroleumBulkStorage.getPipeExternalProtection()) ? petroleumBulkStorage.getPipeExternalProtection().getId() : null);
        equipmentResDTO.setPbsNumber(petroleumBulkStorage.getPbsNumber());
        equipmentResDTO.setOverfill(!ObjectUtils.isEmpty(petroleumBulkStorage.getOverFillProtection()) ? petroleumBulkStorage.getOverFillProtection().getId() : null);
        equipmentResDTO.setManagementNote(petroleumBulkStorage.getManagementNote());
        equipmentResDTO.setManagedBy(petroleumBulkStorage.getManagedBy());
        equipmentResDTO.setFloor(petroleumBulkStorage.getFloor());
        equipmentResDTO.setLocation(petroleumBulkStorage.getLocation());
        equipmentResDTO.setInstalledOn(petroleumBulkStorage.getInstalledOn());
        equipmentResDTO.setMake(petroleumBulkStorage.getMake());
        equipmentResDTO.setInstalledBy(petroleumBulkStorage.getInstalledBy());
        equipmentResDTO.setManagedBy(petroleumBulkStorage.getManagedBy());
        equipmentResDTO.setSerialNo(petroleumBulkStorage.getSerialNo());
        equipmentResDTO.setBuildingId(petroleumBulkStorage.getBuilding().getBuildingId());
        equipmentResDTO.setJobFilingInformation(petroleumBulkStorage.getJobFilingInformation());
        equipmentResDTO.setTankSecondaryContainmentOtherList(petroleumBulkStorage.getTankSecondaryContainmentOtherList());
        equipmentResDTO.setTankLeakDetectionOtherList(petroleumBulkStorage.getTankLeakDetectionOtherList());
        equipmentResDTO.setTankInternalProductionOtherList(petroleumBulkStorage.getTankInternalProductionOtherList());
        equipmentResDTO.setTankExternalProductionOtherList(petroleumBulkStorage.getTankExternalProductionOtherList());
        equipmentResDTO.setSpillPreventionOtherList(petroleumBulkStorage.getSpillPreventionOtherList());
        equipmentResDTO.setPipingSecondaryContainmentOtherList(petroleumBulkStorage.getPipingSecondaryContainmentOtherList());
        equipmentResDTO.setPipingLeakDetectionOtherList(petroleumBulkStorage.getPipingLeakDetectionOtherList());
        equipmentResDTO.setPipeTypeOtherList(petroleumBulkStorage.getPipeTypeOtherList());
        equipmentResDTO.setOverFillProtectionOtherList(petroleumBulkStorage.getOverFillProtectionOtherList());
        equipmentResDTO.setPipingInternalProductionOtherList(petroleumBulkStorage.getPipingInternalProductionOtherList());
        equipmentResDTO.setTankTypeOtherLIst(petroleumBulkStorage.getTankTypeOtherList());
        equipmentResDTO.setStatus(petroleumBulkStorage.getStatus());
        equipmentResDTO.setNote(petroleumBulkStorage.getNote());
        if (petroleumBulkStorageAgency != null) {
            equipmentResDTO.setPetroleumBulkStorageAgency(petroleumBulkStorageAgency);
        }
        return equipmentResDTO;
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
