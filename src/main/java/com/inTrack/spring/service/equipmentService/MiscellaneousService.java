package com.inTrack.spring.service.equipmentService;

import com.inTrack.spring.UtilService.CommonUtilService;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.dto.requestDTO.MiscellaneousDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.User;
import com.inTrack.spring.entity.equipmentEntity.AgencyType;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.Miscellaneous;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
import com.inTrack.spring.repository.equipmentRepository.MiscellaneousRepository;
import com.inTrack.spring.service.ValidatorService;
import com.inTrack.spring.store.ApplicationMessageStore;
import com.inTrack.spring.store.ConstantStore;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MiscellaneousService {

    private final ValidatorService validatorService;
    private final BuildingRepository buildingRepository;
    private final JobFilingInformationRepository jobFilingInformationRepository;
    private final CommonEquipmentService commonEquipmentService;
    private final CommonUtilService commonUtilService;
    private final MiscellaneousRepository miscellaneousRepository;

    @Transactional(rollbackOn = Exception.class)
    public MiscellaneousDTO createMiscellaneous(final MiscellaneousDTO miscellaneousReqDTO, final Long id) {
        final Building building = this.buildingRepository.findByBuildingId(miscellaneousReqDTO.getBuildingId());
        Miscellaneous miscellaneous;
        if (id != null) {
            miscellaneous = this.miscellaneousRepository.findByIdAndIsActive(id, true);
            miscellaneous.setUpdatedAt(System.currentTimeMillis());
        } else {
            final Long uniqueIdCount = this.miscellaneousRepository.countByUniqueId(miscellaneousReqDTO.getUniqueId());
            Long jobFillingIdCount = 0L;
            if (!ObjectUtils.isEmpty(miscellaneousReqDTO.getJobFilingInformationReqDTO())) {
                jobFillingIdCount = this.jobFilingInformationRepository.countByJobNumber(miscellaneousReqDTO.getJobFilingInformationReqDTO().getJobNumber());
            }
            if (uniqueIdCount > 0 && jobFillingIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.UNIQUE_ID_AND_JOB_FILLING_ID_EXISTS);
            } else if (uniqueIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.UNIQUE_ID_EXISTS);
            } else if (jobFillingIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.JOB_FILLING_ID_EXISTS);
            }
            miscellaneous = new Miscellaneous();
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            miscellaneous.setBuilding(building);
            this.commonUtilService.saveEquipment(ConstantStore.MISCELLANEOUS, building, 1L);
            this.commonEquipmentService.createEquipmentFloorRelation(miscellaneousReqDTO.getFloor(), ConstantStore.MISCELLANEOUS, building);
            miscellaneous.setUniqueId(miscellaneousReqDTO.getUniqueId());
            miscellaneous.setCreatedAt(System.currentTimeMillis());
        }
        if (miscellaneousReqDTO.getJobFilingInformationReqDTO() != null) {
            if (!ObjectUtils.isEmpty(miscellaneousReqDTO.getJobFilingInformationReqDTO().getJobFilingId())) {
                this.commonEquipmentService.saveJobFiling(miscellaneousReqDTO.getJobFilingInformationReqDTO());
            } else {
                miscellaneous.setJobFilingInformation(this.saveJobFilingInformation(this.setJobFilingInfo(miscellaneousReqDTO.getJobFilingInformationReqDTO())));
            }
        }
        miscellaneous.setActive(miscellaneousReqDTO.getIsActive());
        miscellaneous.setUpdatedAt(System.currentTimeMillis());
        miscellaneous.setFloor(miscellaneousReqDTO.getFloor());
        miscellaneous.setLocation(miscellaneousReqDTO.getLocation());
        miscellaneous.setLandmark(miscellaneousReqDTO.getLandmark());
        miscellaneous.setInstalledOn(miscellaneousReqDTO.getInstalledOn());
        miscellaneous.setInstalledBy(miscellaneousReqDTO.getInstalledBy());
        miscellaneous.setManagedBy(miscellaneousReqDTO.getManagedBy());
        miscellaneous.setManagementNote(miscellaneousReqDTO.getManagementNote());
        miscellaneous.setDisconnectedOn(miscellaneousReqDTO.getDisconnectedOn());
        miscellaneous.setApplicationId(miscellaneousReqDTO.getApplicationId());
        miscellaneous.setEquipmentType(miscellaneousReqDTO.getEquipmentType());
        miscellaneous.setMake(miscellaneousReqDTO.getMake());
        miscellaneous.setModel(miscellaneousReqDTO.getModel());
        miscellaneous.setSerialNumber(miscellaneousReqDTO.getSerialNumber());
        miscellaneous.setCapacity(miscellaneousReqDTO.getCapacity());
        miscellaneous.setNumberOfIdenticalUnits(miscellaneousReqDTO.getNumberOfIdenticalUnits());
        miscellaneous.setConnectedWithOtherEquipment(miscellaneousReqDTO.getConnectedWithOtherEquipment());
        miscellaneous.setNote(miscellaneousReqDTO.getNote());
        miscellaneous.setManagement(!ObjectUtils.isEmpty(miscellaneousReqDTO.getManagement()) ? new ManagementType().setId(miscellaneousReqDTO.getManagement()) : null);
        miscellaneous.setStatus(!ObjectUtils.isEmpty(miscellaneousReqDTO.getStatus()) ? new ElevatorStatus().setStatusId(miscellaneousReqDTO.getStatus()) : null);
        if (miscellaneousReqDTO.getAgencyName() != null) {
            miscellaneous.setAgencyId(miscellaneousReqDTO.getAgencyId());
            miscellaneous.setAgencyName(!ObjectUtils.isEmpty(miscellaneousReqDTO.getAgencyName()) ? new AgencyType().setId(miscellaneousReqDTO.getAgencyName()) : null);
            miscellaneous.setIssueDate(miscellaneousReqDTO.getIssueDate());
            miscellaneous.setExpirationDate(miscellaneousReqDTO.getExpirationDate());
            miscellaneous.setAgencyStatus(miscellaneousReqDTO.getAgencyStatus());
            miscellaneous.setSubmittedDate(miscellaneousReqDTO.getSubmittedDate());
            miscellaneous.setAgencyNote(miscellaneousReqDTO.getAgencyNote());
        }
        return this.setMiscellaneousDTO(this.miscellaneousRepository.save(miscellaneous));
    }

    public List<Miscellaneous> getMiscellaneous(final Long id, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        List<Miscellaneous> miscellaneous = new LinkedList<>();
//            if (equipmentDetailsReqVO.getEquipmentType() != null && !equipmentDetailsReqVO.getEquipmentType().equalsIgnoreCase(ConstantStore.ELEVATOR)) {
//                throw new ValidationError(ApplicationMessageStore.EQUIPMENT_TYPE_NOT_MATCH);
//            }
        if (id == null) {
            final Building building = this.buildingRepository.findByBuildingId(equipmentDetailsReqVO.getBuildingId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            if (equipmentDetailsReqVO.getFloor() == null) {
                miscellaneous.addAll(this.miscellaneousRepository.findAllByBuilding(building));
            } else {
                miscellaneous.addAll(this.miscellaneousRepository.findAllByBuildingAndFloor(building, equipmentDetailsReqVO.getFloor()));
            }
        } else {
            miscellaneous.add(this.miscellaneousRepository.findById(id).orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND)));
        }
        return miscellaneous;
    }

    public MiscellaneousDTO updateMiscellaneous(final MiscellaneousDTO miscellaneousReqDTO, final Long id) {
        if (id == null) {
            throw new ValidationError(ApplicationMessageStore.ID_MANDATORY);
        }
        return this.createMiscellaneous(miscellaneousReqDTO, id);
    }

    public void deleteMiscellaneous(final Long id, final boolean activeStatus, final boolean isPermanentDelete) {
        final User user = this.validatorService.validateUser();
        final String userRole = user.getRole().getRole();
        final Miscellaneous miscellaneous;
        if (isPermanentDelete && (userRole.equalsIgnoreCase(ConstantStore.ADMIN_SUPER_USER) || userRole.equalsIgnoreCase(ConstantStore.ADMIN_USER))) {
            miscellaneous = this.miscellaneousRepository.findById(id).orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
            this.miscellaneousRepository.delete(miscellaneous);
            this.commonUtilService.removeEquipment(ConstantStore.ELEVATOR, miscellaneous.getBuilding(), 1L);
        } else {
            miscellaneous = this.miscellaneousRepository.findByIdAndIsActive(id, activeStatus);
            this.miscellaneousRepository.delete(miscellaneous);
        }
    }


    private JobFilingInformation saveJobFilingInformation(final JobFilingInformation jobFilingInformation) {
        return this.jobFilingInformationRepository.save(jobFilingInformation);
    }

    private MiscellaneousDTO setMiscellaneousDTO(final Miscellaneous miscellaneous) {
        final MiscellaneousDTO miscellaneousDTO = new MiscellaneousDTO();
        miscellaneousDTO.setId(miscellaneous.getId());
        miscellaneousDTO.setUniqueId(miscellaneous.getUniqueId());
        miscellaneousDTO.setIsActive(miscellaneous.isActive());
        miscellaneousDTO.setFloor(miscellaneous.getFloor());
        miscellaneousDTO.setLocation(miscellaneous.getLocation());
        miscellaneousDTO.setLandmark(miscellaneous.getLandmark());
        miscellaneousDTO.setInstalledOn(miscellaneous.getInstalledOn());
        miscellaneousDTO.setInstalledBy(miscellaneous.getInstalledBy());
        miscellaneousDTO.setManagedBy(miscellaneous.getManagedBy());
        miscellaneousDTO.setManagementNote(miscellaneous.getManagementNote());
        miscellaneousDTO.setDisconnectedOn(miscellaneous.getDisconnectedOn());
        miscellaneousDTO.setApplicationId(miscellaneous.getApplicationId());
        miscellaneousDTO.setEquipmentType(miscellaneous.getEquipmentType());
        miscellaneousDTO.setMake(miscellaneous.getMake());
        miscellaneousDTO.setModel(miscellaneous.getModel());
        miscellaneousDTO.setSerialNumber(miscellaneous.getSerialNumber());
        miscellaneousDTO.setCapacity(miscellaneous.getCapacity());
        miscellaneousDTO.setNumberOfIdenticalUnits(miscellaneous.getNumberOfIdenticalUnits());
        miscellaneousDTO.setConnectedWithOtherEquipment(miscellaneous.getConnectedWithOtherEquipment());
        miscellaneousDTO.setNote(miscellaneous.getNote());
        miscellaneousDTO.setManagement(!ObjectUtils.isEmpty(miscellaneous.getManagement()) ? miscellaneous.getManagement().getId() : null);
        miscellaneousDTO.setStatus(!ObjectUtils.isEmpty(miscellaneous.getStatus()) ? miscellaneous.getStatus().getStatusId() : null);
        if (miscellaneous.getAgencyName() != null) {
            miscellaneousDTO.setAgencyId(miscellaneous.getAgencyId());
            miscellaneousDTO.setAgencyName(!ObjectUtils.isEmpty(miscellaneous.getAgencyName()) ? miscellaneous.getAgencyName().getId() : null);
            miscellaneousDTO.setIssueDate(miscellaneous.getIssueDate());
            miscellaneousDTO.setExpirationDate(miscellaneous.getExpirationDate());
            miscellaneousDTO.setAgencyStatus(miscellaneous.getAgencyStatus());
            miscellaneousDTO.setSubmittedDate(miscellaneous.getSubmittedDate());
            miscellaneousDTO.setAgencyNote(miscellaneous.getAgencyNote());
        }
        miscellaneousDTO.setJobFilingInformationReqDTO(!ObjectUtils.isEmpty(miscellaneous.getJobFilingInformation()) ? this.setJobFilingInfo(miscellaneous.getJobFilingInformation()) : null);
        return miscellaneousDTO;
    }

    private JobFilingInformationReqDTO setJobFilingInfo(final JobFilingInformation jobFilingInformation) {
        final JobFilingInformationReqDTO jobFilingInformationReqDTO = new JobFilingInformationReqDTO();
        jobFilingInformationReqDTO.setJobNumber(jobFilingInformation.getJobNumber());
        jobFilingInformationReqDTO.setStatus(jobFilingInformation.getStatus());
        jobFilingInformationReqDTO.setType(jobFilingInformation.getType());
        jobFilingInformationReqDTO.setDescription(jobFilingInformation.getDescription());
        jobFilingInformationReqDTO.setCommands(jobFilingInformation.getCommands());
        jobFilingInformationReqDTO.setApprovalDate(jobFilingInformation.getApprovalDate());
        jobFilingInformationReqDTO.setApprovalDate(jobFilingInformation.getApprovalDate());
        jobFilingInformationReqDTO.setSignOffDate(jobFilingInformation.getSignOffDate());
        jobFilingInformationReqDTO.setType(jobFilingInformation.getType());
        jobFilingInformationReqDTO.setEquipmentName(jobFilingInformation.getEquipmentName());
        return jobFilingInformationReqDTO;
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
