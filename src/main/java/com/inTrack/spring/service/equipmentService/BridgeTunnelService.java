package com.inTrack.spring.service.equipmentService;

import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.EquipmentReqDTO;
import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.dto.responseDTO.EquipmentResDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.User;
import com.inTrack.spring.entity.equipmentEntity.BridgeTunnel;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
import com.inTrack.spring.repository.equipmentRepository.BridgeTunnelRepository;
import com.inTrack.spring.UtilService.CommonUtilService;
import com.inTrack.spring.service.ValidatorService;
import com.inTrack.spring.store.ApplicationMessageStore;
import com.inTrack.spring.store.ConstantStore;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author vijayan
 */

@Service
public class BridgeTunnelService {

    @Autowired
    private BridgeTunnelRepository bridgeTunnelRepository;

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

    @Transactional(rollbackOn = Exception.class)
    public EquipmentResDTO createBridgeTunnel(final EquipmentReqDTO equipmentReqDTO, final Long bridgeTunnelId) {
        final BridgeTunnel bridgeTunnel;
        final Building building = this.buildingRepository.findByBuildingId(equipmentReqDTO.getBuildingId());
        if (bridgeTunnelId != null) {
            bridgeTunnel = this.bridgeTunnelRepository.findByBridgeTunnelId(bridgeTunnelId);
        } else {
            bridgeTunnel = new BridgeTunnel();
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            bridgeTunnel.setBuilding(building);
            final Long jobFillingIdCount = this.jobFilingInformationRepository.countByJobNumber(equipmentReqDTO.getJobFilingInformationReqDTO().getJobNumber());
            if (jobFillingIdCount > 0) {
                throw new ValidationError(ApplicationMessageStore.JOB_FILLING_ID_EXISTS);
            }
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
            bridgeTunnel.setJobFilingInformation(this.saveJobFilingInformation(jobFilingInformation));
            this.commonUtilService.saveEquipment(ConstantStore.BRIDGE_TUNNEL, building, 1L);
            this.commonEquipmentService.createEquipmentFloorRelation(equipmentReqDTO.getFloor(), ConstantStore.BRIDGE_TUNNEL, building);

        }
        bridgeTunnel.setFloor(equipmentReqDTO.getFloor());
        bridgeTunnel.setLocation(equipmentReqDTO.getLocation());
        bridgeTunnel.setInstalledOn(equipmentReqDTO.getInstalledOn());
        bridgeTunnel.setMakeBy(equipmentReqDTO.getMakeBy());
        bridgeTunnel.setInstalledBy(equipmentReqDTO.getInstalledBy());
        bridgeTunnel.setManagedBy(equipmentReqDTO.getManagedBy());
        bridgeTunnel.setSerialNo(equipmentReqDTO.getSerialNo());
        return this.setEquipment(this.bridgeTunnelRepository.save(bridgeTunnel));
    }

    public List<EquipmentResDTO> getBridgeTunnel(final Long bridgeTunnelId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        final List<BridgeTunnel> bridgeTunnels = new LinkedList<>();
        if (equipmentDetailsReqVO.getEquipmentType() != null && !equipmentDetailsReqVO.getEquipmentType().equalsIgnoreCase(ConstantStore.BRIDGE_TUNNEL)) {
            throw new ValidationError(ApplicationMessageStore.EQUIPMENT_TYPE_NOT_MATCH);
        }
        if (bridgeTunnelId != null) {
            bridgeTunnels.add(this.bridgeTunnelRepository.findByBridgeTunnelId(bridgeTunnelId));
        } else {
            final Building building = this.buildingRepository.findByBuildingId(equipmentDetailsReqVO.getBuildingId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            if (equipmentDetailsReqVO.getFloor() == null) {
                bridgeTunnels.addAll(this.bridgeTunnelRepository.findAllByBuilding(building));
            } else {
                bridgeTunnels.addAll(this.bridgeTunnelRepository.findAllByBuildingAndFloor(building, equipmentDetailsReqVO.getFloor()));
            }
        }
        if (bridgeTunnels.isEmpty()) {
            throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
        }
        return bridgeTunnels.stream().map(this::setEquipment).toList();
    }

    public EquipmentResDTO updateBridgeTunnel(final Long bridgeTunnelId, final EquipmentReqDTO equipmentReqDTO) {
        return this.createBridgeTunnel(equipmentReqDTO, bridgeTunnelId);
    }

    public void deleteBridgeTunnel(final Long bridgeTunnelId, final boolean activeStatus, final boolean isPermanentDelete) {
        final User user = this.validatorService.validateUser();
        final String userRole = user.getRole().getRole();
        final BridgeTunnel bridgeTunnel;
        if (isPermanentDelete && (userRole.equalsIgnoreCase(ConstantStore.ADMIN_SUPER_USER) || userRole.equalsIgnoreCase(ConstantStore.ADMIN_USER))) {
            bridgeTunnel = this.bridgeTunnelRepository.findByBridgeTunnelId(bridgeTunnelId);
            if (bridgeTunnel == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            this.bridgeTunnelRepository.delete(bridgeTunnel);
            this.commonUtilService.removeEquipment(ConstantStore.BRIDGE_TUNNEL, bridgeTunnel.getBuilding(), 1L);
        } else {
            bridgeTunnel = this.bridgeTunnelRepository.findByBridgeTunnelIdAndIsActive(bridgeTunnelId, true);
            if (bridgeTunnel == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            bridgeTunnel.setIsActive(activeStatus);
            this.bridgeTunnelRepository.save(bridgeTunnel);
        }
    }

    private JobFilingInformation saveJobFilingInformation(final JobFilingInformation jobFilingInformation) {
        return this.jobFilingInformationRepository.save(jobFilingInformation);
    }

    private EquipmentResDTO setEquipment(final BridgeTunnel bridgeTunnel) {
        final EquipmentResDTO equipmentResDTO = new EquipmentResDTO();
        equipmentResDTO.setId(bridgeTunnel.getBridgeTunnelId());
        equipmentResDTO.setFloor(bridgeTunnel.getFloor());
        equipmentResDTO.setLocation(bridgeTunnel.getLocation());
        equipmentResDTO.setInstalledOn(bridgeTunnel.getInstalledOn());
        equipmentResDTO.setMakeBy(bridgeTunnel.getMakeBy());
        equipmentResDTO.setInstalledBy(bridgeTunnel.getInstalledBy());
        equipmentResDTO.setManagedBy(bridgeTunnel.getManagedBy());
        equipmentResDTO.setSerialNo(bridgeTunnel.getSerialNo());
        equipmentResDTO.setDeviceType(ConstantStore.BRIDGE_TUNNEL);
        equipmentResDTO.setJobFilingInformation(bridgeTunnel.getJobFilingInformation());
        return equipmentResDTO;
    }
}
