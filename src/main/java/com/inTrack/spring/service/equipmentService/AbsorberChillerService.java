package com.inTrack.spring.service.equipmentService;

import com.inTrack.spring.dto.requestDTO.ChillerReqDTO;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.User;
import com.inTrack.spring.entity.equipmentEntity.AbsorberChiller;
import com.inTrack.spring.entity.equipmentEntity.ChillerGroup;
import com.inTrack.spring.entity.equipmentEntity.TypeOfChiller;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.equipmentRepository.AbsorberChillerRepository;
import com.inTrack.spring.repository.BuildingRepository;
import com.inTrack.spring.repository.agencyRepository.JobFilingInformationRepository;
import com.inTrack.spring.repository.equipmentRepository.ChillerGroupRepository;
import com.inTrack.spring.repository.equipmentRepository.TypeOfChillerRepository;
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
public class AbsorberChillerService {

    @Autowired
    private AbsorberChillerRepository absorberChillerRepository;

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
    private TypeOfChillerRepository typeOfChillerRepository;

    @Autowired
    private ChillerGroupRepository chillerGroupRepository;

    @Transactional(rollbackOn = Exception.class)
    public AbsorberChiller createAbsorberChiller(final ChillerReqDTO equipmentReqDTO, final Long chillerId) {
        final AbsorberChiller absorberChiller;
        final Building building = this.buildingRepository.findByBuildingId(equipmentReqDTO.getBuildingId());
        if (chillerId != null) {
            absorberChiller = this.absorberChillerRepository.findByChillerId(chillerId);
            absorberChiller.getJobFilingInformation().setStatus(equipmentReqDTO.getJobFilingInformationReqDTO().getStatus());
        } else {
            absorberChiller = new AbsorberChiller();
            absorberChiller.setUniqueId(equipmentReqDTO.getUniqueId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            absorberChiller.setBuilding(building);
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
            absorberChiller.setJobFilingInformation(this.saveJobFilingInformation(jobFilingInformation));
            this.commonUtilService.saveEquipment(ConstantStore.ABSORBER_CHILLER, building, 1L);
            this.commonEquipmentService.createEquipmentFloorRelation(equipmentReqDTO.getFloor(), ConstantStore.ABSORBER_CHILLER, building);

        }
        absorberChiller.setDeviceType(ConstantStore.ABSORBER_CHILLER);
        absorberChiller.setManagement(equipmentReqDTO.getManagement());
        absorberChiller.setManagementNote(equipmentReqDTO.getManagementNote());
        absorberChiller.setLandmark(equipmentReqDTO.getLandmark());
        absorberChiller.setDisconnectedOn(equipmentReqDTO.getDisconnectedOn());
        absorberChiller.setFloor(equipmentReqDTO.getFloor());
        absorberChiller.setModel(equipmentReqDTO.getModel());
        absorberChiller.setInspector(equipmentReqDTO.getInspector());
        absorberChiller.setInspectionDate(equipmentReqDTO.getInspectionDate());
        absorberChiller.setFDNYInspectionDate(equipmentReqDTO.getFdnyInspectionDate());
        absorberChiller.setEUPCardAvailable(equipmentReqDTO.getEupcardAvailable());
        absorberChiller.setCapacity(equipmentReqDTO.getCapacity());
        absorberChiller.setChillerGroup(equipmentReqDTO.getChillerGroup());
        absorberChiller.setTypeOfChillerOthersList(equipmentReqDTO.getTypeOfChillerOthersList());
        absorberChiller.setTypeOfChiller(equipmentReqDTO.getTypeOfChiller());
        absorberChiller.setLocation(equipmentReqDTO.getLocation());
        absorberChiller.setInstalledOn(equipmentReqDTO.getInstalledOn());
        absorberChiller.setMakeBy(equipmentReqDTO.getMakeBy());
        absorberChiller.setInstalledBy(equipmentReqDTO.getInstalledBy());
        absorberChiller.setManagedBy(equipmentReqDTO.getManagedBy());
        absorberChiller.setSerialNo(equipmentReqDTO.getSerialNo());
        return this.absorberChillerRepository.save(absorberChiller);
    }

    public List<AbsorberChiller> getAbsorberChiller(final Long chillerId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        final List<AbsorberChiller> absorberChillers = new LinkedList<>();
        if (equipmentDetailsReqVO.getEquipmentType() != null && !equipmentDetailsReqVO.getEquipmentType().equalsIgnoreCase(ConstantStore.ABSORBER_CHILLER)) {
            throw new ValidationError(ApplicationMessageStore.EQUIPMENT_TYPE_NOT_MATCH);
        }
        if (chillerId != null) {
            absorberChillers.add(this.absorberChillerRepository.findByChillerId(chillerId));
        } else {
            final Building building = this.buildingRepository.findByBuildingId(equipmentDetailsReqVO.getBuildingId());
            if (building == null) {
                throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
            }
            if (equipmentDetailsReqVO.getFloor() == null) {
                absorberChillers.addAll(this.absorberChillerRepository.findAllByBuilding(building));
            } else {
                absorberChillers.addAll(this.absorberChillerRepository.findAllByBuildingAndFloor(building, equipmentDetailsReqVO.getFloor()));
            }
        }
        return absorberChillers;
    }

    public AbsorberChiller updateAbsorberChiller(final Long chillerId, final ChillerReqDTO equipmentReqDTO) {
        return this.createAbsorberChiller(equipmentReqDTO, chillerId);
    }

    public void deleteAbsorberChiller(final Long chillerId, final boolean activeStatus, final boolean isPermanentDelete) {
        final User user = this.validatorService.validateUser();
        final String userRole = user.getRole().getRole();
        final AbsorberChiller absorberChiller;
        if (isPermanentDelete && (userRole.equalsIgnoreCase(ConstantStore.ADMIN_SUPER_USER) || userRole.equalsIgnoreCase(ConstantStore.ADMIN_USER))) {
            absorberChiller = this.absorberChillerRepository.findByChillerId(chillerId);
            if (absorberChiller == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            this.absorberChillerRepository.delete(absorberChiller);
            this.commonUtilService.removeEquipment(ConstantStore.ABSORBER_CHILLER, absorberChiller.getBuilding(), 1L);
        } else {
            absorberChiller = this.absorberChillerRepository.findByChillerIdAndIsActive(chillerId, true);
            if (absorberChiller == null) {
                throw new ValidationError(ApplicationMessageStore.DATA_NOT_FOUND);
            }
            absorberChiller.setIsActive(activeStatus);
            this.absorberChillerRepository.save(absorberChiller);
        }
    }

    @Transactional
    private JobFilingInformation saveJobFilingInformation(final JobFilingInformation jobFilingInformation) {
        return this.jobFilingInformationRepository.save(jobFilingInformation);
    }

    public List<TypeOfChiller> getChillerTypes(final String search) {
        return this.typeOfChillerRepository.getChillerTypes(search);
    }

    public List<ChillerGroup> getChillerGroup(final String search) {
        return this.chillerGroupRepository.getChillerGroup(search);
    }

//    private EquipmentResDTO setEquipment(final AbsorberChiller absorberChiller) {
//        final EquipmentResDTO equipmentResDTO = new EquipmentResDTO();
//        equipmentResDTO.setId(absorberChiller.getChillerId());
//        equipmentResDTO.setFloor(absorberChiller.getFloor());
//        equipmentResDTO.setLocation(absorberChiller.getLocation());
//        equipmentResDTO.setInstalledOn(absorberChiller.getInstalledOn());
//        equipmentResDTO.setMakeBy(absorberChiller.getMakeBy());
//        equipmentResDTO.setInstalledBy(absorberChiller.getInstalledBy());
//        equipmentResDTO.setManagedBy(absorberChiller.getManagedBy());
//        equipmentResDTO.setSerialNo(absorberChiller.getSerialNo());
//        return equipmentResDTO;
//    }
}
