package com.inTrack.spring.service.equipmentService;

import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.dto.requestDTO.LandFillReqDTO;
import com.inTrack.spring.dto.requestDTO.PortableFireExtinguisherReqDTO;
import com.inTrack.spring.dto.responseDTO.BoilerCogenAgencyInfoDTO;
import com.inTrack.spring.dto.responseDTO.PortableFireExtinguisherResDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.User;
import com.inTrack.spring.entity.equipmentEntity.ClassType;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.LandFill;
import com.inTrack.spring.entity.equipmentEntity.PortableFireExtinguisher;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.BoilerCogenAgency;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.InspectionType;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.AgencyRepo.BoilerCogenAgencyInfoRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.BoilerCogenAgencyRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.InspectionTypeRepository;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
import com.inTrack.spring.repository.equipmentRepository.ClassTypeRepository;
import com.inTrack.spring.repository.equipmentRepository.PortableFireExtinguisherRepository;
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
public class PortableFireExtinguisherService {

    private final PortableFireExtinguisherRepository portableFireExtinguisherRepository;
    private final ValidatorService validatorService;
    private final BuildingRepository buildingRepository;
    private final CommonUtilService commonUtilService;
    private final BoilerCogenAgencyInfoRepository boilerCogenAgencyInfoRepository;
    private final JobFilingInformationRepository jobFilingInformationRepository;
    private final CommonEquipmentService commonEquipmentService;
    private final ClassTypeRepository classTypeRepository;
    private final BoilerCogenAgencyRepository boilerCogenAgencyRepository;
    private final InspectionTypeRepository inspectionTypeRepository;

    @Transactional(rollbackOn = Exception.class)
    public PortableFireExtinguisherResDTO createPortableFireExtinguisher(final PortableFireExtinguisherReqDTO equipmentReqDTO, final Long FireExtinguisherId) {
        final PortableFireExtinguisher portableFireExtinguisher;
        final Building building = this.buildingRepository.findByBuildingId(equipmentReqDTO.getBuildingId());
        if (FireExtinguisherId != null) {
            portableFireExtinguisher = this.portableFireExtinguisherRepository.findByFireExtinguisherId(FireExtinguisherId);
            portableFireExtinguisher.getJobFilingInformation().setStatus(equipmentReqDTO.getJobFilingInformationReqDTO().getStatus());
        } else {
            portableFireExtinguisher = new PortableFireExtinguisher();
            portableFireExtinguisher.setUniqueId(equipmentReqDTO.getUniqueId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            portableFireExtinguisher.setBuilding(building);
            Long jobFillingIdCount = 0L;
            if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformationReqDTO())) {
                jobFillingIdCount = this.jobFilingInformationRepository.countByJobNumber(equipmentReqDTO.getJobFilingInformationReqDTO().getJobNumber());
            }
            if (jobFillingIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.JOB_FILLING_ID_EXISTS);
            }
            this.commonUtilService.saveEquipment(ConstantStore.PORTABLE_FIRE_EXTINGUISHER, building, 1L);
            this.commonEquipmentService.createEquipmentFloorRelation(equipmentReqDTO.getFloor(), ConstantStore.PORTABLE_FIRE_EXTINGUISHER, building);
        }
        if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformationReqDTO())) {
            if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformationReqDTO().getJobFilingId())) {
                this.commonEquipmentService.saveJobFiling(equipmentReqDTO.getJobFilingInformationReqDTO());
            } else {
                portableFireExtinguisher.setJobFilingInformation(this.saveJobFilingInformation(this.setJobFilingInfo(equipmentReqDTO.getJobFilingInformationReqDTO())));
            }
        }
        portableFireExtinguisher.setDeviceType(ConstantStore.PORTABLE_FIRE_EXTINGUISHER);
        portableFireExtinguisher.setLandmark(equipmentReqDTO.getLandmark());
        portableFireExtinguisher.setManagementNote(equipmentReqDTO.getManagementNote());
        portableFireExtinguisher.setDisconnectedOn(equipmentReqDTO.getDisconnectedOn());
        portableFireExtinguisher.setFloor(equipmentReqDTO.getFloor());
        portableFireExtinguisher.setHydrostaticPressureTest(equipmentReqDTO.getHydrostaticPressureTest());
        portableFireExtinguisher.setInspectionTagAttached(equipmentReqDTO.getInspectionTagAttached());
        portableFireExtinguisher.setModel(equipmentReqDTO.getModel());
        portableFireExtinguisher.setCapacity(equipmentReqDTO.getCapacity());
        portableFireExtinguisher.setClassType(!ObjectUtils.isEmpty(equipmentReqDTO.getClassType()) ? new ClassType().setId(equipmentReqDTO.getClassType()) : null);
        portableFireExtinguisher.setManagement(!ObjectUtils.isEmpty(equipmentReqDTO.getManagement()) ? new ManagementType().setId(equipmentReqDTO.getManagement()) : null);
        portableFireExtinguisher.setInspectionLastTestDate(equipmentReqDTO.getInspectionLastTestDate());
        portableFireExtinguisher.setInspectionHydrostaticPressureTest(equipmentReqDTO.getInspectionHydrostaticPressureTest());
        portableFireExtinguisher.setInspectionNextTestDate(equipmentReqDTO.getInspectionNextTestDate());
        portableFireExtinguisher.setLocation(equipmentReqDTO.getLocation());
        portableFireExtinguisher.setInstalledOn(equipmentReqDTO.getInstalledOn());
        portableFireExtinguisher.setMake(equipmentReqDTO.getMakeBy());
        portableFireExtinguisher.setInstalledBy(equipmentReqDTO.getInstalledBy());
        portableFireExtinguisher.setManagedBy(equipmentReqDTO.getManagedBy());
        portableFireExtinguisher.setSerialNo(equipmentReqDTO.getSerialNo());
        portableFireExtinguisher.setStatus(!ObjectUtils.isEmpty(equipmentReqDTO.getStatus()) ? new ElevatorStatus().setStatusId(equipmentReqDTO.getStatus()) : null);
        final PortableFireExtinguisher extinguisher = this.portableFireExtinguisherRepository.save(portableFireExtinguisher);
        this.saveAgency(equipmentReqDTO, extinguisher);
        return this.setEquipment(extinguisher, null);
    }

    public List<PortableFireExtinguisherResDTO> getPortableFireExtinguisher(final Long FireExtinguisherId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        final List<PortableFireExtinguisher> portableFireExtinguishers = new LinkedList<>();
        final List<BoilerCogenAgencyInfo> boilerCogenAgencyInfoList = new LinkedList<>();
        if (equipmentDetailsReqVO.getEquipmentType() != null && !equipmentDetailsReqVO.getEquipmentType().equalsIgnoreCase(ConstantStore.LAND_FILL)) {
            throw new ValidationError(ApplicationMessageStore.EQUIPMENT_TYPE_NOT_MATCH);
        }
        if (FireExtinguisherId != null) {
            final PortableFireExtinguisher fireExtinguisher = this.portableFireExtinguisherRepository.findByFireExtinguisherId(FireExtinguisherId);
            portableFireExtinguishers.add(fireExtinguisher);
            final List<BoilerCogenAgencyInfo> boilerCogenAgencyInfos = this.boilerCogenAgencyInfoRepository.findByPortableFireExtinguisher(fireExtinguisher);
            boilerCogenAgencyInfos.forEach(agencyInfo -> {
                agencyInfo.setPortableFireExtinguisher(null);
                boilerCogenAgencyInfoList.add(agencyInfo);
            });
        } else {
            final Building building = this.buildingRepository.findByBuildingId(equipmentDetailsReqVO.getBuildingId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            if (equipmentDetailsReqVO.getFloor() == null) {
                portableFireExtinguishers.addAll(this.portableFireExtinguisherRepository.findAllByBuilding(building));
            } else {
                portableFireExtinguishers.addAll(this.portableFireExtinguisherRepository.findAllByBuildingAndFloor(building, equipmentDetailsReqVO.getFloor()));
            }
        }
        if (portableFireExtinguishers.isEmpty()) {
            throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
        }
        return portableFireExtinguishers.stream().map(portableFireExtinguisher -> this.setEquipment(portableFireExtinguisher, boilerCogenAgencyInfoList)).collect(Collectors.toList());
    }

    public PortableFireExtinguisherResDTO updatePortableFireExtinguisher(final Long FireExtinguisherId, final PortableFireExtinguisherReqDTO equipmentReqDTO) {
        return this.createPortableFireExtinguisher(equipmentReqDTO, FireExtinguisherId);
    }

    public void deletePortableFireExtinguisher(final Long FireExtinguisherId, final boolean activeStatus, final boolean isPermanentDelete) {
        final User user = this.validatorService.validateUser();
        final String userRole = user.getRole().getRole();
        final PortableFireExtinguisher portableFireExtinguisher;
        if (isPermanentDelete && (userRole.equalsIgnoreCase(ConstantStore.ADMIN_SUPER_USER) || userRole.equalsIgnoreCase(ConstantStore.ADMIN_USER))) {
            portableFireExtinguisher = this.portableFireExtinguisherRepository.findByFireExtinguisherId(FireExtinguisherId);
            if (FireExtinguisherId == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            this.portableFireExtinguisherRepository.delete(portableFireExtinguisher);
            this.commonUtilService.removeEquipment(ConstantStore.LAND_FILL, portableFireExtinguisher.getBuilding(), 1L);
        } else {
            portableFireExtinguisher = this.portableFireExtinguisherRepository.findByFireExtinguisherIdAndIsActive(FireExtinguisherId, true);
            if (portableFireExtinguisher == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            portableFireExtinguisher.setIsActive(activeStatus);
            this.portableFireExtinguisherRepository.save(portableFireExtinguisher);
        }
    }

    private JobFilingInformation saveJobFilingInformation(final JobFilingInformation jobFilingInformation) {
        return this.jobFilingInformationRepository.save(jobFilingInformation);
    }

    public List<ClassType> getClassType(final String search) {
        return this.classTypeRepository.getClassType(search != null ? search : "");
    }

    private PortableFireExtinguisherResDTO setEquipment(final PortableFireExtinguisher portableFireExtinguisher, final List<BoilerCogenAgencyInfo> agencyInfoList) {
        final PortableFireExtinguisherResDTO equipmentResDTO = new PortableFireExtinguisherResDTO();
        equipmentResDTO.setId(portableFireExtinguisher.getFireExtinguisherId());
        equipmentResDTO.setUniqueId(portableFireExtinguisher.getUniqueId());
        equipmentResDTO.setDeviceType(ConstantStore.PORTABLE_FIRE_EXTINGUISHER);
        equipmentResDTO.setLandmark(portableFireExtinguisher.getLandmark());
        equipmentResDTO.setManagementNote(portableFireExtinguisher.getManagementNote());
        equipmentResDTO.setDisconnectedOn(portableFireExtinguisher.getDisconnectedOn());
        equipmentResDTO.setFloor(portableFireExtinguisher.getFloor());
        equipmentResDTO.setHydrostaticPressureTest(portableFireExtinguisher.getHydrostaticPressureTest());
        equipmentResDTO.setInspectionTagAttached(portableFireExtinguisher.getInspectionTagAttached());
        equipmentResDTO.setModel(portableFireExtinguisher.getModel());
        equipmentResDTO.setCapacity(portableFireExtinguisher.getCapacity());
        equipmentResDTO.setClassType(!ObjectUtils.isEmpty(portableFireExtinguisher.getClassType()) ? portableFireExtinguisher.getClassType().getId() : null);
        equipmentResDTO.setManagement(!ObjectUtils.isEmpty(portableFireExtinguisher.getManagement()) ? portableFireExtinguisher.getManagement().getId() : null);
        equipmentResDTO.setInspectionLastTestDate(portableFireExtinguisher.getInspectionLastTestDate());
        equipmentResDTO.setInspectionHydrostaticPressureTest(portableFireExtinguisher.getInspectionHydrostaticPressureTest());
        equipmentResDTO.setInspectionNextTestDate(portableFireExtinguisher.getInspectionNextTestDate());
        equipmentResDTO.setLocation(portableFireExtinguisher.getLocation());
        equipmentResDTO.setInstalledOn(portableFireExtinguisher.getInstalledOn());
        equipmentResDTO.setMake(portableFireExtinguisher.getMake());
        equipmentResDTO.setInstalledBy(portableFireExtinguisher.getInstalledBy());
        equipmentResDTO.setManagedBy(portableFireExtinguisher.getManagedBy());
        equipmentResDTO.setSerialNo(portableFireExtinguisher.getSerialNo());
        equipmentResDTO.setStatus(!ObjectUtils.isEmpty(portableFireExtinguisher.getStatus()) ? portableFireExtinguisher.getStatus().getStatusId() : null);
        equipmentResDTO.setJobFilingInformation(!ObjectUtils.isEmpty(portableFireExtinguisher.getJobFilingInformation()) ? portableFireExtinguisher.getJobFilingInformation() : null);
        equipmentResDTO.setAgencyInfoList(!ObjectUtils.isEmpty(agencyInfoList) ? this.setBoilerCogenAgencyInfoDTOS(agencyInfoList) : null);
        return equipmentResDTO;
    }



    private void saveAgency(final PortableFireExtinguisherReqDTO equipmentReqDTO, final PortableFireExtinguisher portableFireExtinguisher) {
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
                boilerCogenAgencyInfo.setPortableFireExtinguisher(portableFireExtinguisher);
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
