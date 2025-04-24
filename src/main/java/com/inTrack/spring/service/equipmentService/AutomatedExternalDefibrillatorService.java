package com.inTrack.spring.service.equipmentService;

import com.inTrack.spring.UtilService.CommonUtilService;
import com.inTrack.spring.dto.requestDTO.AutomatedExternalDefibrillatorDTO;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.User;
import com.inTrack.spring.entity.equipmentEntity.AutomatedExternalDefibrillator;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.AutomatedExternalDefibrillatorAgencyInfo;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.AgencyRepo.AutomatedExternalDefibrillatorAgencyInfoRepository;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
import com.inTrack.spring.repository.equipmentRepository.AutomatedExternalDefibrillatorRepository;
import com.inTrack.spring.service.ValidatorService;
import com.inTrack.spring.store.ApplicationMessageStore;
import com.inTrack.spring.store.ConstantStore;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author vijayan
 */

@Service
public class AutomatedExternalDefibrillatorService {

    @Autowired
    private AutomatedExternalDefibrillatorRepository automatedExternalDefibrillatorRepository;

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
    private AutomatedExternalDefibrillatorAgencyInfoRepository agencyInfoRepository;

    @Transactional(rollbackOn = Exception.class)
    public AutomatedExternalDefibrillatorDTO createAutomatedExternalDefibrillator(final AutomatedExternalDefibrillatorDTO equipmentReqDTO, final Long externalDefibrillatorId) {
        final AutomatedExternalDefibrillator automatedExternalDefibrillator;
        final Building building = this.buildingRepository.findByBuildingId(equipmentReqDTO.getBuildingId());
        if (externalDefibrillatorId != null) {
            automatedExternalDefibrillator = this.automatedExternalDefibrillatorRepository.findByExternalDefibrillatorId(externalDefibrillatorId);
            automatedExternalDefibrillator.getJobFilingInformation().setStatus(equipmentReqDTO.getJobFilingInformation().getStatus());
        } else {
            automatedExternalDefibrillator = new AutomatedExternalDefibrillator();
            automatedExternalDefibrillator.setUniqueId(equipmentReqDTO.getUniqueId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            automatedExternalDefibrillator.setBuilding(building);
            Long jobFillingIdCount = 0L;
            if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformation())) {
                jobFillingIdCount = this.jobFilingInformationRepository.countByJobNumber(equipmentReqDTO.getJobFilingInformation().getJobNumber());
            }
            if (jobFillingIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.JOB_FILLING_ID_EXISTS);
            }
            this.commonUtilService.saveEquipment(ConstantStore.AUTOMATED_EXTERNAL_DEFIBRILLATOR, building, 1L);
            this.commonEquipmentService.createEquipmentFloorRelation(equipmentReqDTO.getFloor(), ConstantStore.AUTOMATED_EXTERNAL_DEFIBRILLATOR, building);
        }
        if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformation())) {
            if (!ObjectUtils.isEmpty(equipmentReqDTO.getJobFilingInformation().getJobFilingId())) {
                this.commonEquipmentService.saveJobFiling(equipmentReqDTO.getJobFilingInformation());
            } else {
                automatedExternalDefibrillator.setJobFilingInformation(this.jobFilingInformationRepository.save(equipmentReqDTO.getJobFilingInformation()));
            }
        }
        automatedExternalDefibrillator.setDeviceType(ConstantStore.AUTOMATED_EXTERNAL_DEFIBRILLATOR);
        automatedExternalDefibrillator.setManagementNote(equipmentReqDTO.getManagementNote());
        automatedExternalDefibrillator.setLandmark(equipmentReqDTO.getLandmark());
        automatedExternalDefibrillator.setDisconnectedOn(equipmentReqDTO.getDisconnectedOn());
        automatedExternalDefibrillator.setFloor(equipmentReqDTO.getFloor());
        automatedExternalDefibrillator.setManagement(!ObjectUtils.isEmpty(equipmentReqDTO.getManagement()) ? new ManagementType().setId(equipmentReqDTO.getManagement()) : null);
        automatedExternalDefibrillator.setModel(equipmentReqDTO.getModel());
        automatedExternalDefibrillator.setCapacity(equipmentReqDTO.getCapacity());
        automatedExternalDefibrillator.setBatteryAndCabinetAlarmChangeDate(equipmentReqDTO.getBatteryAndCabinetAlarmChangeDate());
        automatedExternalDefibrillator.setEdBatteryDate(equipmentReqDTO.getEdBatteryDate());
        automatedExternalDefibrillator.setDefibPadExpirationDate(equipmentReqDTO.getDefibPadExpirationDate());
        automatedExternalDefibrillator.setInspection(equipmentReqDTO.getInspection());
        automatedExternalDefibrillator.setNextInspectionDate(equipmentReqDTO.getNextInspectionDate());
        automatedExternalDefibrillator.setLocation(equipmentReqDTO.getLocation());
        automatedExternalDefibrillator.setInstalledOn(equipmentReqDTO.getInstalledOn());
        automatedExternalDefibrillator.setMake(equipmentReqDTO.getMakeBy());
        automatedExternalDefibrillator.setInstalledBy(equipmentReqDTO.getInstalledBy());
        automatedExternalDefibrillator.setManagedBy(equipmentReqDTO.getManagedBy());
        automatedExternalDefibrillator.setSerialNo(equipmentReqDTO.getSerialNo());
        automatedExternalDefibrillator.setStatus(!ObjectUtils.isEmpty(equipmentReqDTO.getStatus()) ? new ElevatorStatus().setStatusId(equipmentReqDTO.getStatus()) : null);
        final AutomatedExternalDefibrillator externalDefibrillator = this.automatedExternalDefibrillatorRepository.save(automatedExternalDefibrillator);
        final List<AutomatedExternalDefibrillatorAgencyInfo> agencyInfo = new LinkedList<>();
        equipmentReqDTO.getAgencyInfo().forEach(automatedExternalDefibrillatorAgencyInfo -> {
            if (!ObjectUtils.isEmpty(automatedExternalDefibrillatorAgencyInfo.getId())) {
                final AutomatedExternalDefibrillatorAgencyInfo defibrillatorAgencyInfo = this.agencyInfoRepository.findById(automatedExternalDefibrillatorAgencyInfo.getId())
                        .orElseThrow(() -> new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND));
                defibrillatorAgencyInfo.setDefibPadsExpirationDate(automatedExternalDefibrillatorAgencyInfo.getDefibPadsExpirationDate());
                defibrillatorAgencyInfo.setBatteryCabinetTamperAlarmChangeDate(automatedExternalDefibrillatorAgencyInfo.getBatteryCabinetTamperAlarmChangeDate());
                defibrillatorAgencyInfo.setInspectionDate(automatedExternalDefibrillatorAgencyInfo.getInspectionDate());
                defibrillatorAgencyInfo.setInspectionFrequency(automatedExternalDefibrillatorAgencyInfo.getInspectionFrequency());
                defibrillatorAgencyInfo.setLastInspectionDate(automatedExternalDefibrillatorAgencyInfo.getLastInspectionDate());
                defibrillatorAgencyInfo.setEdBatteryDate(automatedExternalDefibrillatorAgencyInfo.getEdBatteryDate());
                defibrillatorAgencyInfo.setNextInspectionDate(automatedExternalDefibrillatorAgencyInfo.getNextInspectionDate());
                defibrillatorAgencyInfo.setNotes(automatedExternalDefibrillatorAgencyInfo.getNotes());
                agencyInfo.add(defibrillatorAgencyInfo);
            } else {
                automatedExternalDefibrillatorAgencyInfo.setAutomatedExternalDefibrillator(externalDefibrillator);
                agencyInfo.add(automatedExternalDefibrillatorAgencyInfo);
            }

        });
        this.agencyInfoRepository.saveAll(agencyInfo);
        return this.setEquipment(externalDefibrillator, null);
    }

    public List<AutomatedExternalDefibrillatorDTO> getAutomatedExternalDefibrillator(final Long externalDefibrillatorId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        final List<AutomatedExternalDefibrillator> automatedExternalDefibrillators = new LinkedList<>();
        final List<AutomatedExternalDefibrillatorDTO> automatedExternalDefibrillatorDTOS = new LinkedList<>();
        final List<AutomatedExternalDefibrillatorAgencyInfo> automatedExternalDefibrillatorAgencyInfos = new LinkedList<>();
        if (equipmentDetailsReqVO.getEquipmentType() != null && !equipmentDetailsReqVO.getEquipmentType().equalsIgnoreCase(ConstantStore.LAND_FILL)) {
            throw new ValidationError(ApplicationMessageStore.EQUIPMENT_TYPE_NOT_MATCH);
        }
        if (externalDefibrillatorId != null) {
            final AutomatedExternalDefibrillator automatedExternalDefibrillator = this.automatedExternalDefibrillatorRepository.findByExternalDefibrillatorId(externalDefibrillatorId);
            automatedExternalDefibrillators.add(automatedExternalDefibrillator);
            final List<AutomatedExternalDefibrillatorAgencyInfo> agencyInfoList = this.agencyInfoRepository.findByAutomatedExternalDefibrillator(automatedExternalDefibrillator);
            agencyInfoList.forEach(agencyInfo -> {
                agencyInfo.setAutomatedExternalDefibrillator(null);
                automatedExternalDefibrillatorAgencyInfos.add(agencyInfo);
            });
        } else {
            final Building building = this.buildingRepository.findByBuildingId(equipmentDetailsReqVO.getBuildingId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            if (equipmentDetailsReqVO.getFloor() == null) {
                automatedExternalDefibrillators.addAll(this.automatedExternalDefibrillatorRepository.findAllByBuilding(building));
            } else {
                automatedExternalDefibrillators.addAll(this.automatedExternalDefibrillatorRepository.findAllByBuildingAndFloor(building, equipmentDetailsReqVO.getFloor()));
            }
        }
        if (automatedExternalDefibrillators.isEmpty()) {
            throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
        }
        automatedExternalDefibrillators.forEach(automatedExternalDefibrillator -> {
            automatedExternalDefibrillatorDTOS.add(this.setEquipment(automatedExternalDefibrillator, automatedExternalDefibrillatorAgencyInfos));
        });
        return automatedExternalDefibrillatorDTOS;
    }

    public AutomatedExternalDefibrillatorDTO updateAutomatedExternalDefibrillator(final Long externalDefibrillatorId, final AutomatedExternalDefibrillatorDTO equipmentReqDTO) {
        return this.createAutomatedExternalDefibrillator(equipmentReqDTO, externalDefibrillatorId);
    }

    public void deleteAutomatedExternalDefibrillator(final Long externalDefibrillatorId, final boolean activeStatus, final boolean isPermanentDelete) {
        final User user = this.validatorService.validateUser();
        final String userRole = user.getRole().getRole();
        final AutomatedExternalDefibrillator automatedExternalDefibrillator;
        if (isPermanentDelete && (userRole.equalsIgnoreCase(ConstantStore.ADMIN_SUPER_USER) || userRole.equalsIgnoreCase(ConstantStore.ADMIN_USER))) {
            automatedExternalDefibrillator = this.automatedExternalDefibrillatorRepository.findByExternalDefibrillatorId(externalDefibrillatorId);
            if (externalDefibrillatorId == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            this.automatedExternalDefibrillatorRepository.delete(automatedExternalDefibrillator);
            this.commonUtilService.removeEquipment(ConstantStore.LAND_FILL, automatedExternalDefibrillator.getBuilding(), 1L);
        } else {
            automatedExternalDefibrillator = this.automatedExternalDefibrillatorRepository.findByExternalDefibrillatorIdAndIsActive(externalDefibrillatorId, true);
            if (automatedExternalDefibrillator == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            automatedExternalDefibrillator.setIsActive(activeStatus);
            this.automatedExternalDefibrillatorRepository.save(automatedExternalDefibrillator);
        }
    }

    private JobFilingInformation saveJobFilingInformation(final JobFilingInformation jobFilingInformation) {
        return this.jobFilingInformationRepository.save(jobFilingInformation);
    }

    private AutomatedExternalDefibrillatorDTO setEquipment(final AutomatedExternalDefibrillator automatedExternalDefibrillator, final List<AutomatedExternalDefibrillatorAgencyInfo> automatedExternalDefibrillatorAgencyInfos) {
        final AutomatedExternalDefibrillatorDTO automatedExternalDefibrillatorDTO = new AutomatedExternalDefibrillatorDTO();
        automatedExternalDefibrillatorDTO.setManagementNote(automatedExternalDefibrillator.getManagementNote());
        automatedExternalDefibrillatorDTO.setLandmark(automatedExternalDefibrillator.getLandmark());
        automatedExternalDefibrillatorDTO.setDisconnectedOn(automatedExternalDefibrillator.getDisconnectedOn());
        automatedExternalDefibrillatorDTO.setFloor(automatedExternalDefibrillator.getFloor());
        automatedExternalDefibrillatorDTO.setManagement(!ObjectUtils.isEmpty(automatedExternalDefibrillator.getManagement()) ? automatedExternalDefibrillator.getManagement().getId() : null);
        automatedExternalDefibrillatorDTO.setModel(automatedExternalDefibrillator.getModel());
        automatedExternalDefibrillatorDTO.setCapacity(automatedExternalDefibrillator.getCapacity());
        automatedExternalDefibrillatorDTO.setBatteryAndCabinetAlarmChangeDate(automatedExternalDefibrillator.getBatteryAndCabinetAlarmChangeDate());
        automatedExternalDefibrillatorDTO.setEdBatteryDate(automatedExternalDefibrillator.getEdBatteryDate());
        automatedExternalDefibrillatorDTO.setDefibPadExpirationDate(automatedExternalDefibrillator.getDefibPadExpirationDate());
        automatedExternalDefibrillatorDTO.setInspection(automatedExternalDefibrillator.getInspection());
        automatedExternalDefibrillatorDTO.setNextInspectionDate(automatedExternalDefibrillator.getNextInspectionDate());
        automatedExternalDefibrillatorDTO.setLocation(automatedExternalDefibrillator.getLocation());
        automatedExternalDefibrillatorDTO.setInstalledOn(automatedExternalDefibrillator.getInstalledOn());
        automatedExternalDefibrillatorDTO.setMakeBy(automatedExternalDefibrillator.getMake());
        automatedExternalDefibrillatorDTO.setInstalledBy(automatedExternalDefibrillator.getInstalledBy());
        automatedExternalDefibrillatorDTO.setManagedBy(automatedExternalDefibrillator.getManagedBy());
        automatedExternalDefibrillatorDTO.setSerialNo(automatedExternalDefibrillator.getSerialNo());
        automatedExternalDefibrillatorDTO.setStatus(!ObjectUtils.isEmpty(automatedExternalDefibrillator.getStatus()) ? automatedExternalDefibrillator.getStatus().getStatusId() : null);
        automatedExternalDefibrillatorDTO.setJobFilingInformation(!ObjectUtils.isEmpty(automatedExternalDefibrillator.getJobFilingInformation()) ? automatedExternalDefibrillator.getJobFilingInformation() : null);
        automatedExternalDefibrillatorDTO.setAgencyInfo(automatedExternalDefibrillatorAgencyInfos);
        automatedExternalDefibrillatorDTO.setId(automatedExternalDefibrillator.getExternalDefibrillatorId());
        automatedExternalDefibrillatorDTO.setUniqueId(automatedExternalDefibrillator.getUniqueId());
        automatedExternalDefibrillatorDTO.setBuildingId(automatedExternalDefibrillator.getBuilding().getBuildingId());
        automatedExternalDefibrillatorDTO.setDeviceType(automatedExternalDefibrillator.getDeviceType());
        return automatedExternalDefibrillatorDTO;
    }
}
