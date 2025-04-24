package com.inTrack.spring.service.equipmentService;

import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.FireAlarmDTO;
import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.dto.responseDTO.BoilerCogenAgencyInfoDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.User;
import com.inTrack.spring.entity.equipmentEntity.*;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.BoilerCogenAgency;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.InspectionType;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.AgencyRepo.BoilerCogenAgencyInfoRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.BoilerCogenAgencyRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.InspectionTypeRepository;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
import com.inTrack.spring.repository.equipmentRepository.*;
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

/**
 * @author vijayan
 */

@Service
@RequiredArgsConstructor
public class FireAlarmService {

    private final FireAlarmRepository fireAlarmRepository;
    private final ValidatorService validatorService;
    private final BuildingRepository buildingRepository;
    private final CommonUtilService commonUtilService;
    private final JobFilingInformationRepository jobFilingInformationRepository;
    private final CommonEquipmentService commonEquipmentService;
    private final ControlEquipmentRepository controlEquipmentRepository;
    private final BatteryTypeRepository batteryTypeRepository;
    private final BatteryLocationRepository batteryLocationRepository;
    private final FiberOpticCableConnectionRepository fiberOpticCableConnectionRepository;
    private final BoilerCogenAgencyInfoRepository boilerCogenAgencyInfoRepository;
    private final BoilerCogenAgencyRepository boilerCogenAgencyRepository;
    private final InspectionTypeRepository inspectionTypeRepository;

    @Transactional(rollbackOn = Exception.class)
    public FireAlarmDTO createFireAlarm(final FireAlarmDTO equipmentReqDTO, final Long fireAlarmId) {
        final FireAlarm fireAlarm;
        final Building building = this.buildingRepository.findByBuildingId(equipmentReqDTO.getBuildingId());
        if (fireAlarmId != null) {
            fireAlarm = this.fireAlarmRepository.findByFireAlarmId(fireAlarmId);
        } else {
            fireAlarm = new FireAlarm();
            fireAlarm.setUniqueId(equipmentReqDTO.getUniqueId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            fireAlarm.setBuilding(building);
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
                fireAlarm.setJobFilingInformation(this.saveJobFilingInformation(jobFilingInformation));
            }
            this.commonUtilService.saveEquipment(ConstantStore.FIRE_ALARM, building, 1L);
            this.commonEquipmentService.createEquipmentFloorRelation(equipmentReqDTO.getFloor(), ConstantStore.FIRE_ALARM, building);
        }
        fireAlarm.setNote(equipmentReqDTO.getNote());
        fireAlarm.setDeviceType(ConstantStore.FIRE_ALARM);
        fireAlarm.setManagement(!ObjectUtils.isEmpty(equipmentReqDTO.getManagement()) ? new ManagementType().setId(equipmentReqDTO.getManagement()) : null);
        fireAlarm.setManagementNote(equipmentReqDTO.getManagementNote());
        fireAlarm.setLandmark(equipmentReqDTO.getLandmark());
        fireAlarm.setDisconnectedOn(equipmentReqDTO.getDisconnectedOn());
        fireAlarm.setFloor(equipmentReqDTO.getFloor());
        fireAlarm.setLocation(equipmentReqDTO.getLocation());
        fireAlarm.setInstalledOn(equipmentReqDTO.getInstalledOn());
        fireAlarm.setMakeBy(equipmentReqDTO.getMakeBy());
        fireAlarm.setInstalledBy(equipmentReqDTO.getInstalledBy());
        fireAlarm.setManagedBy(equipmentReqDTO.getManagedBy());
        fireAlarm.setSerialNo(equipmentReqDTO.getSerialNo());
        fireAlarm.setFireAlarmType(equipmentReqDTO.getFireAlarmType());
        fireAlarm.setAlarmNotificationAppliances(equipmentReqDTO.getAlarmNotificationAppliances());
        fireAlarm.setTransientSuppressors(equipmentReqDTO.getTransientSuppressors());
        fireAlarm.setSupervisingStationFireAlarmSystem(equipmentReqDTO.getSupervisingStationFireAlarmSystem());
        fireAlarm.setSpecialProcedures(equipmentReqDTO.getSpecialProcedures());
        fireAlarm.setRemoteAnnunciations(equipmentReqDTO.getRemoteAnnunciations());
        fireAlarm.setNextInspectionDate(equipmentReqDTO.getNextInspectionDate());
        fireAlarm.setModel(equipmentReqDTO.getModel());
        fireAlarm.setLastInspectionDate(equipmentReqDTO.getLastInspectionDate());
        fireAlarm.setInterfaceEquipment(equipmentReqDTO.getInterfaceEquipment());
        fireAlarm.setInitiatingDevices(equipmentReqDTO.getInitiatingDevices());
        fireAlarm.setGuardsTourEquipment(equipmentReqDTO.getGuardsTourEquipment());
        fireAlarm.setFiberOpticConnection(!ObjectUtils.isEmpty(equipmentReqDTO.getFiberOpticConnection()) ? new FiberOpticConnection().setFiberOpticConnectionId(equipmentReqDTO.getFiberOpticConnection()) : null);
        fireAlarm.setEmergencyCommunicationsEquipment(equipmentReqDTO.getEmergencyCommunicationsEquipment());
        fireAlarm.setControlUnitTroubleSignals(equipmentReqDTO.getControlUnitTroubleSignals());
        fireAlarm.setControlEquipment(!ObjectUtils.isEmpty(equipmentReqDTO.getControlEquipment()) ? new ControlEquipment().setControlEquipmentId(equipmentReqDTO.getControlEquipment()) : null);
        fireAlarm.setCapacity(equipmentReqDTO.getCapacity());
        fireAlarm.setBatteryType(!ObjectUtils.isEmpty(equipmentReqDTO.getBatteryType()) ? new BatteryType().setBatteryTypeId(equipmentReqDTO.getBatteryType()) : null);
        fireAlarm.setBatteryLocation(!ObjectUtils.isEmpty(equipmentReqDTO.getBatteryLocation()) ? new BatteryLocation().setBatteryLocationId(equipmentReqDTO.getBatteryLocation()) : null);
        fireAlarm.setStatus(!ObjectUtils.isEmpty(equipmentReqDTO.getStatus()) ? new ElevatorStatus().setStatusId(equipmentReqDTO.getStatus()) : null);
        final FireAlarm saveFireAlarm = this.fireAlarmRepository.save(fireAlarm);
        this.saveAgency(equipmentReqDTO, saveFireAlarm);
        return this.setEquipment(saveFireAlarm, null);
    }

    public List<FireAlarmDTO> getFireAlarm(final Long fireAlarmId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        final List<FireAlarm> fireAlarms = new LinkedList<>();
        final List<FireAlarmDTO> fireAlarmDTOS = new LinkedList<>();
        List<BoilerCogenAgencyInfo> boilerCogenAgencyInfos;
        final List<BoilerCogenAgencyInfo> boilerCogenAgencyInfoList = new LinkedList<>();
        if (equipmentDetailsReqVO.getEquipmentType() != null && !equipmentDetailsReqVO.getEquipmentType().equalsIgnoreCase(ConstantStore.FIRE_ALARM)) {
            throw new ValidationError(ApplicationMessageStore.EQUIPMENT_TYPE_NOT_MATCH);
        }
        if (fireAlarmId != null) {
            final FireAlarm fireAlarm = this.fireAlarmRepository.findByFireAlarmId(fireAlarmId);
            fireAlarms.add(fireAlarm);
            boilerCogenAgencyInfos = this.boilerCogenAgencyInfoRepository.findByFireAlarm(fireAlarm);
            boilerCogenAgencyInfos.forEach(boilerCogenAgencyInfo -> {
                boilerCogenAgencyInfo.setFireAlarm(null);
                boilerCogenAgencyInfoList.add(boilerCogenAgencyInfo);
            });
        } else {
            boilerCogenAgencyInfos = null;
            final Building building = this.buildingRepository.findByBuildingId(equipmentDetailsReqVO.getBuildingId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            if (equipmentDetailsReqVO.getFloor() == null) {
                fireAlarms.addAll(this.fireAlarmRepository.findAllByBuilding(building));
            } else {
                fireAlarms.addAll(this.fireAlarmRepository.findAllByBuildingAndFloor(building, equipmentDetailsReqVO.getFloor()));
            }
        }
        if (fireAlarms.isEmpty()) {
            throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
        }
        fireAlarms.forEach(fireAlarm -> fireAlarmDTOS.add(this.setEquipment(fireAlarm, boilerCogenAgencyInfos)));
        return fireAlarmDTOS;
    }

    public FireAlarmDTO updateFireAlarm(final Long fireAlarmId, final FireAlarmDTO equipmentReqDTO) {
        return this.createFireAlarm(equipmentReqDTO, fireAlarmId);
    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteFireAlarm(final Long fireAlarmId, final boolean activeStatus, final boolean isPermanentDelete) {
        final User user = this.validatorService.validateUser();
        final String userRole = user.getRole().getRole();
        final FireAlarm fireAlarm;
        if (isPermanentDelete && (userRole.equalsIgnoreCase(ConstantStore.ADMIN_SUPER_USER) || userRole.equalsIgnoreCase(ConstantStore.ADMIN_USER))) {
            fireAlarm = this.fireAlarmRepository.findByFireAlarmId(fireAlarmId);
            if (fireAlarmId == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            this.jobFilingInformationRepository.delete(fireAlarm.getJobFilingInformation());
            this.fireAlarmRepository.delete(fireAlarm);
            this.commonUtilService.removeEquipment(ConstantStore.FIRE_ALARM, fireAlarm.getBuilding(), 1L);
        } else {
            fireAlarm = this.fireAlarmRepository.findByFireAlarmIdAndIsActive(fireAlarmId, true);
            if (fireAlarm == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            fireAlarm.setIsActive(activeStatus);
            this.fireAlarmRepository.save(fireAlarm);
        }
    }

    private JobFilingInformation saveJobFilingInformation(final JobFilingInformation jobFilingInformation) {
        return this.jobFilingInformationRepository.save(jobFilingInformation);
    }

    public List<BatteryType> getBatteryType(final String search) {
        return this.batteryTypeRepository.getBatteryType(search != null ? search : "");
    }

    public List<BatteryLocation> getBatteryLocation(final String search) {
        return this.batteryLocationRepository.getBatteryLocation(search != null ? search : "");
    }

    public List<FiberOpticConnection> getFiberOpticCableConnection(final String search) {
        return this.fiberOpticCableConnectionRepository.getFiberOpticCableConnection(search != null ? search : "");
    }

    public List<ControlEquipment> getControlEquipment(final String search) {
        return this.controlEquipmentRepository.getControlEquipment(search != null ? search : "");
    }

    private void saveAgency(final FireAlarmDTO equipmentReqDTO, final FireAlarm fireAlarm) {
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
                boilerCogenAgencyInfo.setFireAlarm(fireAlarm);
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

    private FireAlarmDTO setEquipment(final FireAlarm fireAlarm, final List<BoilerCogenAgencyInfo> boilerCogenAgencyInfos) {
        final FireAlarmDTO equipmentResDTO = new FireAlarmDTO();
        equipmentResDTO.setId(fireAlarm.getFireAlarmId());
        equipmentResDTO.setUniqueId(fireAlarm.getUniqueId());
        equipmentResDTO.setDeviceType(fireAlarm.getDeviceType());
        equipmentResDTO.setManagementNote(fireAlarm.getManagementNote());
        equipmentResDTO.setLandmark(fireAlarm.getLandmark());
        equipmentResDTO.setDisconnectedOn(fireAlarm.getDisconnectedOn());
        equipmentResDTO.setFloor(fireAlarm.getFloor());
        equipmentResDTO.setLocation(fireAlarm.getLocation());
        equipmentResDTO.setInstalledOn(fireAlarm.getInstalledOn());
        equipmentResDTO.setMakeBy(fireAlarm.getMakeBy());
        equipmentResDTO.setInstalledBy(fireAlarm.getInstalledBy());
        equipmentResDTO.setManagedBy(fireAlarm.getManagedBy());
        equipmentResDTO.setSerialNo(fireAlarm.getSerialNo());
        equipmentResDTO.setFireAlarmType(fireAlarm.getFireAlarmType());
        equipmentResDTO.setAlarmNotificationAppliances(fireAlarm.getAlarmNotificationAppliances());
        equipmentResDTO.setTransientSuppressors(fireAlarm.getTransientSuppressors());
        equipmentResDTO.setSupervisingStationFireAlarmSystem(fireAlarm.getSupervisingStationFireAlarmSystem());
        equipmentResDTO.setSpecialProcedures(fireAlarm.getSpecialProcedures());
        equipmentResDTO.setRemoteAnnunciations(fireAlarm.getRemoteAnnunciations());
        equipmentResDTO.setNextInspectionDate(fireAlarm.getNextInspectionDate());
        equipmentResDTO.setModel(fireAlarm.getModel());
        equipmentResDTO.setLastInspectionDate(fireAlarm.getLastInspectionDate());
        equipmentResDTO.setInterfaceEquipment(fireAlarm.getInterfaceEquipment());
        equipmentResDTO.setInitiatingDevices(fireAlarm.getInitiatingDevices());
        equipmentResDTO.setGuardsTourEquipment(fireAlarm.getGuardsTourEquipment());
        equipmentResDTO.setCapacity(fireAlarm.getCapacity());
        equipmentResDTO.setEmergencyCommunicationsEquipment(fireAlarm.getEmergencyCommunicationsEquipment());
        equipmentResDTO.setControlUnitTroubleSignals(fireAlarm.getControlUnitTroubleSignals());
        equipmentResDTO.setManagement(!ObjectUtils.isEmpty(fireAlarm.getManagement()) ? fireAlarm.getManagement().getId() : null);
        equipmentResDTO.setFiberOpticConnection(!ObjectUtils.isEmpty(fireAlarm.getFiberOpticConnection()) ? fireAlarm.getFiberOpticConnection().getFiberOpticConnectionId() : null);
        equipmentResDTO.setControlEquipment(!ObjectUtils.isEmpty(fireAlarm.getControlEquipment()) ? fireAlarm.getControlEquipment().getControlEquipmentId() : null);
        equipmentResDTO.setBatteryType(!ObjectUtils.isEmpty(fireAlarm.getBatteryType()) ? fireAlarm.getBatteryType().getBatteryTypeId() : null);
        equipmentResDTO.setBatteryLocation(!ObjectUtils.isEmpty(fireAlarm.getBatteryLocation()) ? fireAlarm.getBatteryLocation().getBatteryLocationId() : null);
        equipmentResDTO.setAgencyInfoList(!ObjectUtils.isEmpty(boilerCogenAgencyInfos) ? this.setBoilerCogenAgencyInfoDTOS(boilerCogenAgencyInfos) : null);
        equipmentResDTO.setNote(fireAlarm.getNote());
        equipmentResDTO.setBuildingId(fireAlarm.getBuilding().getBuildingId());
        return equipmentResDTO;
    }
}
