package com.inTrack.spring.service.equipmentService;

import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.dto.requestDTO.KitchenHoodFireSuppressionDTO;
import com.inTrack.spring.dto.requestDTO.RPZReqDTO;
import com.inTrack.spring.dto.responseDTO.BoilerCogenAgencyInfoDTO;
import com.inTrack.spring.dto.responseDTO.RPZResDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.User;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.KitchenHoodFireSuppression;
import com.inTrack.spring.entity.equipmentEntity.RPZ;
import com.inTrack.spring.entity.equipmentEntity.TypeOfRpz;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.BoilerCogenAgency;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.InspectionType;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.AgencyRepo.BoilerCogenAgencyInfoRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.BoilerCogenAgencyRepository;
import com.inTrack.spring.repository.AgencyRepo.agencyDropDown.InspectionTypeRepository;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
import com.inTrack.spring.repository.equipmentRepository.RPZRepository;
import com.inTrack.spring.repository.equipmentRepository.TypeOfRpzRepository;
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
public class RPZService {

    private final RPZRepository rpzRepository;
    private final ValidatorService validatorService;
    private final BuildingRepository buildingRepository;
    private final CommonUtilService commonUtilService;
    private final JobFilingInformationRepository jobFilingInformationRepository;
    private final CommonEquipmentService commonEquipmentService;
    private final TypeOfRpzRepository typeOfRpzRepository;
    private final BoilerCogenAgencyInfoRepository boilerCogenAgencyInfoRepository;
    private final BoilerCogenAgencyRepository boilerCogenAgencyRepository;
    private final InspectionTypeRepository inspectionTypeRepository;


    @Transactional(rollbackOn = Exception.class)
    public RPZResDTO createRPZEquipment(final RPZReqDTO equipmentReqDTO, final Long RPZId) {
        final RPZ rpz;
        final Building building = this.buildingRepository.findByBuildingId(equipmentReqDTO.getBuildingId());
        if (RPZId != null) {
            rpz = this.rpzRepository.findByRPZId(RPZId);
            rpz.getJobFilingInformation().setStatus(equipmentReqDTO.getJobFilingInformationReqDTO().getStatus());
        } else {
            final Long uniqueIdCount = this.rpzRepository.countByUniqueId(equipmentReqDTO.getUniqueId());
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
            rpz = new RPZ();
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            rpz.setBuilding(building);
            this.commonUtilService.saveEquipment(ConstantStore.RPZ, building, 1L);
            this.commonEquipmentService.createEquipmentFloorRelation(equipmentReqDTO.getFloor(), ConstantStore.RPZ, building);
        }
        if (equipmentReqDTO.getJobFilingInformationReqDTO() != null) {
            if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformationReqDTO().getJobFilingId())) {
                this.commonEquipmentService.saveJobFiling(equipmentReqDTO.getJobFilingInformationReqDTO());
            } else {
                rpz.setJobFilingInformation(this.saveJobFilingInformation(this.setJobFilingInfo(equipmentReqDTO.getJobFilingInformationReqDTO())));
            }
        }
        rpz.setManagementNote(equipmentReqDTO.getManagementNote());
        rpz.setModel(equipmentReqDTO.getModel());
        rpz.setManagement(!ObjectUtils.isEmpty(equipmentReqDTO.getManagement()) ? new ManagementType().setId(equipmentReqDTO.getManagement()) : null);
        rpz.setSize(equipmentReqDTO.getSize());
        rpz.setCapacity(equipmentReqDTO.getCapacity());
        rpz.setDeviceType(ConstantStore.RPZ);
        rpz.setDisconnectedOn(equipmentReqDTO.getDisconnectedOn());
        rpz.setLandmark(equipmentReqDTO.getLandmark());
        rpz.setLinePressure(equipmentReqDTO.getLinePressure());
        rpz.setUniqueId(equipmentReqDTO.getUniqueId());
        rpz.setTypeOfRpz(!ObjectUtils.isEmpty(equipmentReqDTO.getTypeOfRpz()) ? new TypeOfRpz().setTypeId(equipmentReqDTO.getTypeOfRpz()) : null);
        rpz.setMeterReading(equipmentReqDTO.getMeterReading());
        rpz.setTypeOfService(equipmentReqDTO.getTypeOfService());
        rpz.setWaterReadingNumber(equipmentReqDTO.getWaterReadingNumber());
        rpz.setFloor(equipmentReqDTO.getFloor());
        rpz.setLocation(equipmentReqDTO.getLocation());
        rpz.setInstalledOn(equipmentReqDTO.getInstalledOn());
        rpz.setMake(equipmentReqDTO.getMake());
        rpz.setComments(equipmentReqDTO.getComments());
        rpz.setEquipmentStatus(!ObjectUtils.isEmpty(equipmentReqDTO.getStatus()) ? new ElevatorStatus().setStatusId(equipmentReqDTO.getStatus()) : null);
        rpz.setInstalledBy(equipmentReqDTO.getInstalledBy());
        rpz.setManagedBy(equipmentReqDTO.getManagedBy());
        rpz.setSerialNo(equipmentReqDTO.getSerialNo());
        final RPZ saveRpz = this.rpzRepository.save(rpz);
        this.saveAgency(equipmentReqDTO, saveRpz);
        return this.setEquipment(saveRpz, null);
    }

    public List<RPZResDTO> getRPZEquipment(final Long rpzId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        final List<RPZ> rpzList = new LinkedList<>();
        final List<BoilerCogenAgencyInfo> agencyInfoList = new LinkedList<>();
        if (equipmentDetailsReqVO.getEquipmentType() != null && !equipmentDetailsReqVO.getEquipmentType().equalsIgnoreCase(ConstantStore.RPZ)) {
            throw new ValidationError(ApplicationMessageStore.EQUIPMENT_TYPE_NOT_MATCH);
        }
        if (rpzId != null) {
            final RPZ rpz = this.rpzRepository.findByRPZId(rpzId);
            rpzList.add(rpz);
            agencyInfoList.addAll(this.boilerCogenAgencyInfoRepository.findByRpz(rpz));
        } else {
            final Building building = this.buildingRepository.findByBuildingId(equipmentDetailsReqVO.getBuildingId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            if (equipmentDetailsReqVO.getFloor() == null) {
                rpzList.addAll(this.rpzRepository.findAllByBuilding(building));
            } else {
                rpzList.addAll(this.rpzRepository.findAllByBuildingAndFloor(building, equipmentDetailsReqVO.getFloor()));
            }
        }
        return rpzList.stream().map(rpz -> this.setEquipment(rpz, agencyInfoList)).toList();
    }

    public RPZResDTO updateRPZEquipment(final Long rpzId, final RPZReqDTO equipmentReqDTO) {
        return this.createRPZEquipment(equipmentReqDTO, rpzId);
    }

    public void deleteRPZEquipment(final Long rpzId, final boolean activeStatus, final boolean isPermanentDelete) {
        final User user = this.validatorService.validateUser();
        final String userRole = user.getRole().getRole();
        final RPZ rpz;
        if (isPermanentDelete && (userRole.equalsIgnoreCase(ConstantStore.ADMIN_SUPER_USER) || userRole.equalsIgnoreCase(ConstantStore.ADMIN_USER))) {
            rpz = this.rpzRepository.findByRPZId(rpzId);
            if (rpzId == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            this.jobFilingInformationRepository.delete(rpz.getJobFilingInformation());
            this.rpzRepository.delete(rpz);
            this.commonUtilService.removeEquipment(ConstantStore.RPZ, rpz.getBuilding(), 1L);
        } else {
            rpz = this.rpzRepository.findByRPZIdAndIsActive(rpzId, true);
            if (rpz == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            rpz.setIsActive(activeStatus);
            this.rpzRepository.save(rpz);
        }
    }

    private JobFilingInformation saveJobFilingInformation(final JobFilingInformation jobFilingInformation) {
        return this.jobFilingInformationRepository.save(jobFilingInformation);
    }

    private RPZResDTO setEquipment(final RPZ rpz, final List<BoilerCogenAgencyInfo> agencies) {
        final RPZResDTO equipmentResDTO = new RPZResDTO(
                rpz.getRPZId(),
                rpz.getFloor(),
                rpz.getLocation(),
                rpz.getIsActive(),
                rpz.getMake(),
                rpz.getSerialNo(),
                rpz.getInstalledOn(),
                rpz.getInstalledBy(),
                rpz.getManagedBy(),
                rpz.getBuilding().getBuildingId(),
                rpz.getJobFilingInformation(),
                rpz.getUniqueId(),
                rpz.getDeviceType(),
                !ObjectUtils.isEmpty(rpz.getManagement()) ? rpz.getManagement().getId() : null,
                rpz.getLandmark(),
                rpz.getDisconnectedOn(),
                rpz.getManagementNote(),
                rpz.getModel(),
                rpz.getCapacity(),
                rpz.getSize(),
                rpz.getMeterReading(),
                rpz.getWaterReadingNumber(),
                rpz.getLinePressure(),
                rpz.getTypeOfService(),
                !ObjectUtils.isEmpty(rpz.getTypeOfRpz()) ? rpz.getTypeOfRpz().getTypeId() : null,
                !ObjectUtils.isEmpty(rpz.getEquipmentStatus()) ? rpz.getEquipmentStatus().getStatusId() : null,
                rpz.getComments(),
                !ObjectUtils.isEmpty(agencies) ? this.setBoilerCogenAgencyInfoDTOS(agencies) : null
        );
        return equipmentResDTO;
    }

    public List<TypeOfRpz> getTypeOfRpz(final String search) {
        return this.typeOfRpzRepository.getTypeOfRpz(search != null ? search : "");
    }


    private void saveAgency(final RPZReqDTO equipmentReqDTO, final RPZ rpz) {
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
                boilerCogenAgencyInfo.setRpz(rpz);
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
