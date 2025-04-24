package com.inTrack.spring.service.equipmentService;

import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.dto.responseDTO.CommonEquipmentDetailResDTO;
import com.inTrack.spring.dto.responseDTO.EquipmentFloorDetailResDTO;
import com.inTrack.spring.dto.responseDTO.FloorDetailResDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.EquipmentFloorRelation;
import com.inTrack.spring.entity.EquipmentPerformance.EquipmentType;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.repository.ManagementTypeRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
import com.inTrack.spring.repository.equipmentRepository.EquipmentFloorRelationRepository;
import com.inTrack.spring.repository.equipmentRepository.EquipmentRepository;
import com.inTrack.spring.repository.equipmentRepository.EquipmentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommonEquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final EquipmentFloorRelationRepository equipmentFloorRelationRepository;
    private final EquipmentTypeRepository equipmentTypeRepository;
    private final ManagementTypeRepository managementTypeRepository;
    private final JobFilingInformationRepository jobFilingInformationRepository;

    public List<CommonEquipmentDetailResDTO> getEquipmentDetails(final Long buildingId) {
        if (buildingId != null && buildingId > 0) {
            return this.equipmentRepository.findByBuilding(buildingId)
                    .stream()
                    .map(equipment -> new CommonEquipmentDetailResDTO(
                            StringUtils.capitalize(equipment.getEquipmentType().toLowerCase()),
                            equipment.getEquipmentCount(),
                            equipment.getBuilding().getBuildingId(),
                            equipment.getEquipmentId()
                    )).collect(Collectors.toList());
        } else {
            return this.equipmentRepository.findByBuilding(0L)
                    .stream()
                    .map(equipment -> new CommonEquipmentDetailResDTO(
                            StringUtils.capitalize(equipment.getEquipmentType().toLowerCase()),
                            equipment.getEquipmentCount(),
                            equipment.getBuilding().getBuildingId(),
                            equipment.getEquipmentId()
                    )).collect(Collectors.toList());
        }
    }

    public void createEquipmentFloorRelation(final Long floor, final String equipmentType, final Building building) {
        EquipmentFloorRelation equipmentFloorRelation = this.equipmentFloorRelationRepository.findByFloorAndEquipmentType(floor, equipmentType);
        if (equipmentFloorRelation != null) {
            return;
        }
        equipmentFloorRelation = new EquipmentFloorRelation();
        equipmentFloorRelation.setEquipmentType(equipmentType);
        equipmentFloorRelation.setFloor(floor);
        equipmentFloorRelation.setBuilding(building);
        equipmentFloorRelation.setCreatedAt(System.currentTimeMillis());
        this.equipmentFloorRelationRepository.save(equipmentFloorRelation);
    }

    public List<EquipmentFloorDetailResDTO> getEquipmentListBasedOnFloors() {
        final List<EquipmentFloorRelation> equipmentFloorRelations = this.equipmentFloorRelationRepository.findAll();
        final List<EquipmentFloorDetailResDTO> equipmentFloorDetailResDTOS = new LinkedList<>();
        List<FloorDetailResDTO> floorDetailResDTOS = new LinkedList<>();
        EquipmentFloorDetailResDTO equipmentFloorDetailResDTO = new EquipmentFloorDetailResDTO();
        for (EquipmentFloorRelation equipmentFloorRelation : equipmentFloorRelations) {
            FloorDetailResDTO floorDetailResDTO = new FloorDetailResDTO();
            if (equipmentFloorDetailResDTO.getFloor() != null && equipmentFloorDetailResDTO.getFloor().equals(equipmentFloorRelation.getFloor())) {
                floorDetailResDTO.setId(equipmentFloorRelation.getId());
                floorDetailResDTO.setEquipmentType(equipmentFloorRelation.getEquipmentType());
                floorDetailResDTO.setBuilding(equipmentFloorRelation.getBuilding().getBuildingId());
                floorDetailResDTOS.add(floorDetailResDTO);
                equipmentFloorDetailResDTO.setFloorDetailResDTOS(floorDetailResDTOS);
            } else {
                equipmentFloorDetailResDTO = new EquipmentFloorDetailResDTO();
                equipmentFloorDetailResDTO.setFloor(equipmentFloorRelation.getFloor());
                floorDetailResDTO.setId(equipmentFloorRelation.getId());
                floorDetailResDTO.setEquipmentType(equipmentFloorRelation.getEquipmentType());
                floorDetailResDTO.setBuilding(equipmentFloorRelation.getBuilding().getBuildingId());
                equipmentFloorDetailResDTO.setFloor(equipmentFloorRelation.getFloor());
                floorDetailResDTOS = new LinkedList<>();
                floorDetailResDTOS.add(floorDetailResDTO);
                equipmentFloorDetailResDTO.setFloorDetailResDTOS(floorDetailResDTOS);
                equipmentFloorDetailResDTOS.add(equipmentFloorDetailResDTO);
            }
        }
        return equipmentFloorDetailResDTOS;
    }

    public List<String> getAllEquipmentType() {
        return equipmentTypeRepository.findAll()
                .stream()
                .map(EquipmentType::getType)
                .collect(Collectors.toList());
    }

    public List<ManagementType> getManagementType() {
        return managementTypeRepository.findAll();
    }

    public JobFilingInformation saveJobFiling(final JobFilingInformationReqDTO jobFilingInformationReqDTO) {
        final JobFilingInformation jobFilingInformation = this.jobFilingInformationRepository.findByJobFilingId(jobFilingInformationReqDTO.getJobFilingId());
        jobFilingInformation.setStatus(jobFilingInformationReqDTO.getStatus());
        jobFilingInformation.setType(jobFilingInformationReqDTO.getType());
        jobFilingInformation.setDescription(jobFilingInformationReqDTO.getDescription());
        jobFilingInformation.setJobNumber(jobFilingInformationReqDTO.getJobNumber());
        jobFilingInformation.setApprovalDate(jobFilingInformationReqDTO.getApprovalDate());
        jobFilingInformation.setEquipmentName(jobFilingInformationReqDTO.getEquipmentName());
        jobFilingInformation.setSignOffDate(jobFilingInformationReqDTO.getSignOffDate());
        jobFilingInformation.setApprovalDate(jobFilingInformationReqDTO.getApprovalDate());
        jobFilingInformation.setCommands(jobFilingInformationReqDTO.getCommands());
        jobFilingInformation.setDescription(jobFilingInformationReqDTO.getDescription());
        return this.jobFilingInformationRepository.save(jobFilingInformation);
    }

    public JobFilingInformation saveJobFiling(final JobFilingInformation jobFilingInformationReqDTO) {
        final JobFilingInformation jobFilingInformation = this.jobFilingInformationRepository.findByJobFilingId(jobFilingInformationReqDTO.getJobFilingId());
        jobFilingInformation.setStatus(jobFilingInformationReqDTO.getStatus());
        jobFilingInformation.setType(jobFilingInformationReqDTO.getType());
        jobFilingInformation.setDescription(jobFilingInformationReqDTO.getDescription());
        jobFilingInformation.setJobNumber(jobFilingInformationReqDTO.getJobNumber());
        jobFilingInformation.setApprovalDate(jobFilingInformationReqDTO.getApprovalDate());
        jobFilingInformation.setEquipmentName(jobFilingInformationReqDTO.getEquipmentName());
        jobFilingInformation.setSignOffDate(jobFilingInformationReqDTO.getSignOffDate());
        jobFilingInformation.setApprovalDate(jobFilingInformationReqDTO.getApprovalDate());
        jobFilingInformation.setCommands(jobFilingInformationReqDTO.getCommands());
        jobFilingInformation.setDescription(jobFilingInformationReqDTO.getDescription());
        return this.jobFilingInformationRepository.save(jobFilingInformation);
    }
}
