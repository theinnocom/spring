package com.inTrack.spring.service.equipmentService;


import com.inTrack.spring.dto.FirstAidKitDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.FirstAidKit;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.FirstAidAgencyInfo;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.AgencyRepo.FirstAidAgencyInfoRepository;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
import com.inTrack.spring.repository.equipmentRepository.FirstAidKitRepository;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FirstAidKitService {

    private final FirstAidKitRepository firstAidKitRepository;
    private final BuildingRepository buildingRepository;
    private final JobFilingInformationRepository jobFilingInformationRepository;
    private final FirstAidAgencyInfoRepository firstAidAgencyInfoRepository;

    public FirstAidKitDTO saveFirstAidKit(final FirstAidKitDTO firstAidKitDTO) {
        final FirstAidKit firstAidKit;
        if (firstAidKitDTO.getId() == null) {
            firstAidKit = new FirstAidKit();
            final JobFilingInformation jobFilingInformation = this.jobFilingInformationRepository.save(firstAidKitDTO.getJobFilingInformation());
            firstAidKit.setJobFilingInformation(jobFilingInformation);
            if (firstAidKitDTO.getBuildingId() != null) {
                final Building building = buildingRepository.findByBuildingId(firstAidKitDTO.getBuildingId());
                if (building == null) {
                    throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
                }
                firstAidKit.setBuilding(building);
            }
        } else {
            firstAidKit = this.firstAidKitRepository.findById(firstAidKitDTO.getId()).orElseThrow(() -> new RuntimeException(ApplicationMessageStore.DATA_NOT_FOUND));
        }
        firstAidKit.setUniqueId(firstAidKitDTO.getUniqueId());
        firstAidKit.setEquipmentStatus(!ObjectUtils.isEmpty(firstAidKitDTO.getStatus()) ? new ElevatorStatus().setStatusId(firstAidKitDTO.getStatus()) : null);
        firstAidKit.setFloor(firstAidKitDTO.getFloor());
        firstAidKit.setLocation(firstAidKitDTO.getLocation());
        firstAidKit.setLandmark(firstAidKitDTO.getLandmark());
        firstAidKit.setInstalledOn(firstAidKitDTO.getInstalledOn());
        firstAidKit.setInstalledBy(firstAidKitDTO.getInstalledBy());
        firstAidKit.setManagement(!ObjectUtils.isEmpty(firstAidKitDTO.getManagement()) ? new ManagementType().setId(firstAidKitDTO.getManagement()) : null);
        firstAidKit.setManagedBy(firstAidKitDTO.getManagedBy());
        firstAidKit.setManagementNote(firstAidKitDTO.getManagementNote());
        firstAidKit.setDisconnectedOn(firstAidKitDTO.getDisconnectedOn());
        firstAidKit.setMake(firstAidKitDTO.getMake());
        firstAidKit.setModel(firstAidKitDTO.getModel());
        firstAidKit.setSerialNumber(firstAidKitDTO.getSerialNumber());
        firstAidKit.setKitUsedOn(firstAidKitDTO.getKitUsedOn());
        firstAidKit.setUsedFor(firstAidKitDTO.getUsedFor());
        firstAidKit.setAidKitRestoredAfterUse(firstAidKitDTO.getAidKitRestoredAfterUse());
        firstAidKit.setKitRestoredDate(firstAidKitDTO.getKitRestoredDate());
        firstAidKit.setKitExpiryDate(firstAidKitDTO.getKitExpiryDate());
        firstAidKit.setKitRestoredBy(firstAidKitDTO.getKitRestoredBy());
        firstAidKit.setEmployeeTrained(firstAidKitDTO.getEmployeeTrained());
        firstAidKit.setEmployeeName(firstAidKitDTO.getEmployeeName());
        firstAidKit.setTrainedPersonNames(firstAidKitDTO.getTrainedPersonNames());
        firstAidKit.setNote(firstAidKitDTO.getNote());
        final FirstAidKit aidKit = this.firstAidKitRepository.save(firstAidKit);
        final List<FirstAidAgencyInfo> firstAidAgencyInfoList = new LinkedList<>();
        firstAidKitDTO.getFirstAidAgencyInfoList().forEach(firstAidAgencyInfo -> {
            firstAidAgencyInfo.setFirstAidKit(aidKit);
            firstAidAgencyInfoList.add(firstAidAgencyInfo);
        });
        this.firstAidAgencyInfoRepository.saveAll(firstAidAgencyInfoList);
        return this.setFirstAidKit(aidKit, null);
    }

    public FirstAidKitDTO getFirstAidKit(final Long id) {
        final FirstAidKit firstAidKit = this.firstAidKitRepository.findById(id).orElseThrow(() -> new RuntimeException("First aid kit not found."));
        final List<FirstAidAgencyInfo> firstAidAgencyInfoList = this.firstAidAgencyInfoRepository.findByFirstAidKit(firstAidKit);
        return setFirstAidKit(firstAidKit, firstAidAgencyInfoList);
    }

    public List<FirstAidKitDTO> getAllFirstAidKit(final Long buildingId) {
        final Building building = buildingRepository.findByBuildingId(buildingId);
        if (building == null) {
            throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
        }
        final List<FirstAidKit> firstAidKits = this.firstAidKitRepository.findByBuilding(building);
        return firstAidKits.stream().map(firstAidKit -> this.setFirstAidKit(firstAidKit, null)).collect(Collectors.toList());
    }

    public FirstAidKitDTO updateFirstAidKitDTO(final FirstAidKitDTO firstAidKitDTO) {
        if (firstAidKitDTO.getId() == null) {
            throw new ValidationError(ApplicationMessageStore.ID_MANDATORY);
        }
        return this.saveFirstAidKit(firstAidKitDTO);
    }

    public void deleteFirstAidKit(final Long id) {
        final FirstAidKit firstAidKit = this.firstAidKitRepository.findById(id).orElseThrow(() -> new RuntimeException("First aid kit not found."));
        this.firstAidKitRepository.delete(firstAidKit);
    }

    private FirstAidKitDTO setFirstAidKit(final FirstAidKit firstAidKit, final List<FirstAidAgencyInfo> firstAidAgencyInfoList) {
        final FirstAidKitDTO firstAidKitDTO = new FirstAidKitDTO();
        firstAidKitDTO.setId(firstAidKit.getId());
        firstAidKitDTO.setUniqueId(firstAidKit.getUniqueId());
        firstAidKitDTO.setStatus(!ObjectUtils.isEmpty(firstAidKit.getEquipmentStatus()) ? firstAidKit.getEquipmentStatus().getStatusId() : null);
        firstAidKitDTO.setFloor(firstAidKit.getFloor());
        firstAidKitDTO.setLocation(firstAidKit.getLocation());
        firstAidKitDTO.setLandmark(firstAidKit.getLandmark());
        firstAidKitDTO.setInstalledOn(firstAidKit.getInstalledOn());
        firstAidKitDTO.setInstalledBy(firstAidKit.getInstalledBy());
        firstAidKitDTO.setManagement(!ObjectUtils.isEmpty(firstAidKit.getManagement()) ? firstAidKit.getManagement().getId() : null);
        firstAidKitDTO.setManagedBy(firstAidKit.getManagedBy());
        firstAidKitDTO.setManagementNote(firstAidKit.getManagementNote());
        firstAidKitDTO.setDisconnectedOn(firstAidKit.getDisconnectedOn());
        firstAidKitDTO.setMake(firstAidKit.getMake());
        firstAidKitDTO.setModel(firstAidKit.getModel());
        firstAidKitDTO.setSerialNumber(firstAidKit.getSerialNumber());
        firstAidKitDTO.setKitUsedOn(firstAidKit.getKitUsedOn());
        firstAidKitDTO.setUsedFor(firstAidKit.getUsedFor());
        firstAidKitDTO.setAidKitRestoredAfterUse(firstAidKit.getAidKitRestoredAfterUse());
        firstAidKitDTO.setKitRestoredDate(firstAidKit.getKitRestoredDate());
        firstAidKitDTO.setKitExpiryDate(firstAidKit.getKitExpiryDate());
        firstAidKitDTO.setKitRestoredBy(firstAidKit.getKitRestoredBy());
        firstAidKitDTO.setEmployeeTrained(firstAidKit.getEmployeeTrained());
        firstAidKitDTO.setEmployeeName(firstAidKit.getEmployeeName());
        firstAidKitDTO.setTrainedPersonNames(firstAidKit.getTrainedPersonNames());
        firstAidKitDTO.setMonthlyInspectionDate(firstAidKit.getMonthlyInspectionDate());
        firstAidKitDTO.setInspectedBy(firstAidKit.getInspectedBy());
        firstAidKitDTO.setAnnualInspectionDate(firstAidKit.getAnnualInspectionDate());
        firstAidKitDTO.setNote(firstAidKit.getNote());
        firstAidKitDTO.setBuildingId(firstAidKit.getBuilding().getBuildingId());
        firstAidKitDTO.setJobFilingInformation(!ObjectUtils.isEmpty(firstAidKit.getJobFilingInformation()) ? firstAidKit.getJobFilingInformation() : null);
        firstAidKitDTO.setFirstAidAgencyInfoList(!ObjectUtils.isEmpty(firstAidAgencyInfoList) ? firstAidAgencyInfoList : null);
        return firstAidKitDTO;
    }
}

