package com.inTrack.spring.service.equipmentService;

import com.inTrack.spring.dto.requestDTO.ElevatorReqDTO;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.dto.responseDTO.BoilerCogenAgencyInfoDTO;
import com.inTrack.spring.dto.responseDTO.ElevatorResDTO;
import com.inTrack.spring.dto.responseDTO.ElevatorStatusResDTO;
import com.inTrack.spring.entity.*;
import com.inTrack.spring.entity.equipmentEntity.Elevator;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.BoilerCogenAgency;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.DeviceType;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.InspectionType;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.AgencyRepo.BoilerCogenAgencyInfoRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.BoilerCogenAgencyRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.DeviceTypeRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.InspectionDurationTypeRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.InspectionTypeRepository;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
import com.inTrack.spring.repository.equipmentRepository.ElevatorRepository;
import com.inTrack.spring.repository.equipmentRepository.ElevatorStatusRepository;
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
public class ElevatorService {

    private final ValidatorService validatorService;
    private final ElevatorRepository elevatorRepository;
    private final BuildingRepository buildingRepository;
    private final JobFilingInformationRepository jobFilingInformationRepository;
    private final CommonUtilService commonUtilService;
    private final CommonEquipmentService commonEquipmentService;
    private final ElevatorStatusRepository elevatorStatusRepository;
    private final BoilerCogenAgencyInfoRepository boilerCogenAgencyInfoRepository;
    private final DeviceTypeRepository deviceTypeRepository;
    private final BoilerCogenAgencyRepository boilerCogenAgencyRepository;
    private final InspectionTypeRepository inspectionTypeRepository;

    @Transactional(rollbackOn = Exception.class)
    public ElevatorResDTO createElevator(final ElevatorReqDTO elevatorReqDTO, final Long elevatorId) {
        final User requestingUser = this.validatorService.validateUser();
        final Building building = this.buildingRepository.findByBuildingId(elevatorReqDTO.getBuildingId());
        Elevator elevator;
        if (elevatorId != null) {
            elevator = this.elevatorRepository.findByElevatorIdAndIsActive(elevatorId, true);
            elevator.setUpdatedAt(System.currentTimeMillis());
        } else {
            final Long uniqueIdCount = this.elevatorRepository.countByUniqueId(elevatorReqDTO.getUniqueId());
            Long jobFillingIdCount = 0L;
            if (elevatorReqDTO.getJobFilingInformationReqDTO() != null) {
                jobFillingIdCount = this.jobFilingInformationRepository.countByJobNumber(elevatorReqDTO.getJobFilingInformationReqDTO().getJobNumber());
            }
            if (uniqueIdCount > 0 && jobFillingIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.UNIQUE_ID_AND_JOB_FILLING_ID_EXISTS);
            } else if (uniqueIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.UNIQUE_ID_EXISTS);
            } else if (jobFillingIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.JOB_FILLING_ID_EXISTS);
            }
            elevator = new Elevator();
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            elevator.setUpdatedBy(requestingUser);
            elevator.setBuilding(building);
            if (elevatorReqDTO.getJobFilingInformationReqDTO() != null) {
                final JobFilingInformation jobFilingInformation = new JobFilingInformation();
                final JobFilingInformationReqDTO jobFilingInformationReqDTO = elevatorReqDTO.getJobFilingInformationReqDTO();
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
                elevator.setJobFilingInformation(this.saveJobFilingInformation(jobFilingInformation));
            }
            this.commonUtilService.saveEquipment(ConstantStore.ELEVATOR, building, 1L);
            this.commonEquipmentService.createEquipmentFloorRelation(elevatorReqDTO.getFloor(), ConstantStore.ELEVATOR, building);
            elevator.setUniqueId(elevatorReqDTO.getUniqueId());
            elevator.setElevatorJobNo(elevatorReqDTO.getElevatorJobNo());
            elevator.setCreatedBy(requestingUser);
        }
        elevator.setDeviceType(elevatorReqDTO.getDeviceType() != null ? new DeviceType().setId(elevatorReqDTO.getDeviceType()) : null);
        elevator.setTypeOfUse(elevatorReqDTO.getTypeOfUse());
        elevator.setFloor(elevatorReqDTO.getFloor());
        elevator.setFloorFrom(elevatorReqDTO.getFloorFrom());
        elevator.setFloorTo(elevatorReqDTO.getFloorTo());
        elevator.setLocation(elevatorReqDTO.getLocation());
        elevator.setLandmark(elevatorReqDTO.getLandmark());
        elevator.setSafetyType(elevatorReqDTO.getSafetyType());
        elevator.setManagementType(elevatorReqDTO.getManagementType() != null ? new ManagementType().setId(elevatorReqDTO.getManagementType()) : null);
        elevator.setManagedBy(elevatorReqDTO.getManagedBy());
        elevator.setMake(elevatorReqDTO.getMake());
        elevator.setModel(elevatorReqDTO.getModel());
        elevator.setCapacity(elevatorReqDTO.getCapacity());
        elevator.setSerial(elevatorReqDTO.getSerial());
        elevator.setGovernorType(elevatorReqDTO.getGovernorType());
        elevator.setMachineType(elevatorReqDTO.getMachineType());
        elevator.setModeOfOperation(elevatorReqDTO.getModeOfOperation());
        elevator.setPhoneInspection(elevatorReqDTO.getPhoneInspection());
        elevator.setInstalledBy(this.validatorService.validateUser(elevatorReqDTO.getInstalledBy()));
        elevator.setInstalledOn(elevatorReqDTO.getInstalledOn());
        elevator.setCreatedAt(System.currentTimeMillis());
        elevator.setElevatorStatus(elevatorReqDTO.getElevatorStatus() != null ? new ElevatorStatus().setStatusId(elevatorReqDTO.getElevatorStatus()) : null);
        elevator.setComments(elevatorReqDTO.getComments());
        elevator.setDisconnectedOn(elevatorReqDTO.getDisconnectedOn());
        elevator.setIsActive(true);
        final Elevator saveElevator = this.elevatorRepository.save(elevator);
        this.saveAgency(elevatorReqDTO, saveElevator);
        return this.setElevator(saveElevator, null);
    }

    public List<ElevatorResDTO> getElevators() {
        final User requestingUser = this.validatorService.validateUser();
        final List<ElevatorResDTO> elevatorResDTOS = new LinkedList<>();
        final List<Elevator> elevators = this.elevatorRepository.findAll();
        elevators.forEach(elevator -> {
            elevatorResDTOS.add(this.setElevator(elevator, null));
        });
        return elevatorResDTOS;
    }

    public List<ElevatorResDTO> getElevator(final Long elevatorId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        List<ElevatorResDTO> elevatorResDTOS = new LinkedList<>();
        final List<BoilerCogenAgencyInfo> agencyInfoList = new LinkedList<>();
        if (equipmentDetailsReqVO.getEquipmentType() != null && !equipmentDetailsReqVO.getEquipmentType().equalsIgnoreCase(ConstantStore.ELEVATOR)) {
            throw new ValidationError(ApplicationMessageStore.EQUIPMENT_TYPE_NOT_MATCH);
        }
        if (elevatorId == null) {
            final Building building = this.buildingRepository.findByBuildingId(equipmentDetailsReqVO.getBuildingId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            if (equipmentDetailsReqVO.getFloor() == null) {
                elevatorResDTOS = this.elevatorRepository.findAllByBuilding(building)
                        .stream()
                        .map(elevator -> this.setElevator(elevator, agencyInfoList))
                        .toList();
            } else {
                elevatorResDTOS = this.elevatorRepository.findAllByBuildingAndFloor(building, equipmentDetailsReqVO.getFloor())
                        .stream()
                        .map(elevator -> this.setElevator(elevator, agencyInfoList))
                        .toList();
            }
        } else {
            final Elevator elevator = this.elevatorRepository.findByElevatorId(elevatorId);
            final List<BoilerCogenAgencyInfo> boilerCogenAgencyInfoList = this.boilerCogenAgencyInfoRepository.findByElevator(elevator);
            boilerCogenAgencyInfoList.forEach(boilerCogenAgencyInfo -> {
                boilerCogenAgencyInfo.setElevator(null);
                agencyInfoList.add(boilerCogenAgencyInfo);
            });
            elevatorResDTOS.add(this.setElevator(elevator, agencyInfoList));
        }
        return elevatorResDTOS;
    }

    public ElevatorResDTO updateElevator(final ElevatorReqDTO elevatorReqDTO, final Long elevatorId) {
        if (elevatorId == null) {
            throw new ValidationError(ApplicationMessageStore.ELEVATOR_NOT_FOUND);
        }
        return this.createElevator(elevatorReqDTO, elevatorId);
    }

    private JobFilingInformation saveJobFilingInformation(final JobFilingInformation jobFilingInformation) {
        return this.jobFilingInformationRepository.save(jobFilingInformation);
    }

    public void deleteElevator(final Long elevatorId, final boolean activeStatus, final boolean isPermanentDelete) {
        final User user = this.validatorService.validateUser();
        final String userRole = user.getRole().getRole();
        final Elevator elevator;
        if (isPermanentDelete && (userRole.equalsIgnoreCase(ConstantStore.ADMIN_SUPER_USER) || userRole.equalsIgnoreCase(ConstantStore.ADMIN_USER))) {
            elevator = this.elevatorRepository.findByElevatorId(elevatorId);
            this.elevatorRepository.delete(elevator);
            this.commonUtilService.removeEquipment(ConstantStore.ELEVATOR, elevator.getBuilding(), 1L);
        } else {
            elevator = this.elevatorRepository.findByElevatorIdAndIsActive(elevatorId, activeStatus);
            this.elevatorRepository.delete(elevator);
        }
    }

    private ElevatorResDTO setElevator(final Elevator elevator, final List<BoilerCogenAgencyInfo> agencyInfoList) {
        final ElevatorResDTO elevatorResDTO = new ElevatorResDTO();
        elevatorResDTO.setElevatorId(elevator.getElevatorId());
        elevatorResDTO.setDeviceType(elevator.getDeviceType() != null ? elevator.getDeviceType().getId() : null);
        elevatorResDTO.setTypeOfUse(elevator.getTypeOfUse());
        elevatorResDTO.setFloor(elevator.getFloor());
        elevatorResDTO.setFloorFrom(elevator.getFloorFrom());
        elevatorResDTO.setFloorTo(elevator.getFloorTo());
        elevatorResDTO.setLocation(elevator.getLocation());
        elevatorResDTO.setLandmark(elevator.getLandmark());
        elevatorResDTO.setSafetyType(elevator.getSafetyType());
        elevatorResDTO.setManagementType(elevator.getManagementType() != null ? elevator.getManagementType().getId() : null);
        elevatorResDTO.setManagedBy(elevator.getManagedBy());
        elevatorResDTO.setMake(elevator.getMake());
        elevatorResDTO.setModel(elevator.getModel());
        elevatorResDTO.setCapacity(elevator.getCapacity());
        elevatorResDTO.setSerial(elevator.getSerial());
        elevatorResDTO.setGovernorType(elevator.getGovernorType());
        elevatorResDTO.setMachineType(elevator.getMachineType());
        elevatorResDTO.setModeOfOperation(elevator.getModeOfOperation());
        elevatorResDTO.setPhoneInspection(elevator.getPhoneInspection());
        elevatorResDTO.setJobFilingInformation(elevator.getJobFilingInformation());
        elevatorResDTO.setInstalledBy(elevator.getInstalledBy().getUserId());
        elevatorResDTO.setInstalledOn(elevator.getInstalledOn());
        elevatorResDTO.setUniqueId(elevator.getUniqueId());
        elevatorResDTO.setElevatorJobNo(elevator.getElevatorJobNo());
        elevatorResDTO.setCreatedBy(elevator.getCreatedBy().getUserId());
        elevatorResDTO.setCreatedAt(System.currentTimeMillis());
        elevatorResDTO.setElevatorStatus(elevator.getElevatorStatus() != null ? elevator.getElevatorStatus().getStatusId() : null);
        elevatorResDTO.setComments(elevator.getComments());
        elevatorResDTO.setDisconnectedOn(elevator.getDisconnectedOn());
        elevatorResDTO.setActiveStatus(elevator.getIsActive());
        elevatorResDTO.setBuildingId(elevator.getBuilding().getBuildingId());
        elevatorResDTO.setAgencyInfoList(agencyInfoList != null ? this.setBoilerCogenAgencyInfoDTOS(agencyInfoList) : null);
        return elevatorResDTO;
    }

    public List<ElevatorStatusResDTO> getElevatorStatus() {
        return this.elevatorStatusRepository.findAll()
                .stream()
                .map(elevatorStatus -> new ElevatorStatusResDTO(
                        elevatorStatus.getStatusId(),
                        elevatorStatus.getStatusName()
                )).collect(Collectors.toList());
    }

    public List<DeviceType> getDeviceType() {
        return this.deviceTypeRepository.findAll();
    }

    private void saveAgency(final ElevatorReqDTO equipmentReqDTO, final Elevator elevator) {
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
                boilerCogenAgencyInfo.setElevator(elevator);
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
