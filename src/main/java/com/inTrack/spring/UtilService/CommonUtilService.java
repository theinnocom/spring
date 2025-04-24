package com.inTrack.spring.UtilService;

import com.inTrack.spring.dto.responseDTO.CommonListResDTO;
import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.Equipment;
import com.inTrack.spring.repository.equipmentRepository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * @author Vijayan
 */
@Service
public class CommonUtilService {


    @Autowired
    private EquipmentRepository equipmentRepository;

    public void saveEquipment(final String equipmentType, final Building building, final Long equipmentCount) {
        Equipment equipment = this.equipmentRepository.findByEquipmentTypeAndBuildingAndFacility(equipmentType, building, building.getFacility());
        if (equipment == null) {
            equipment = new Equipment();
            equipment.setEquipmentCount(1L);
            equipment.setBuilding(building);
            equipment.setFacility(building.getFacility());
            equipment.setCreatedAt(System.currentTimeMillis());
            equipment.setEquipmentType(equipmentType);
        } else {
            equipment.setEquipmentCount(equipment.getEquipmentCount() + equipmentCount);
        }
        this.equipmentRepository.save(equipment);
    }

    public void removeEquipment(final String equipmentType, final Building building, final Long equipmentCount) {
        final Equipment equipment = this.equipmentRepository.findByEquipmentTypeAndBuildingAndFacility(equipmentType, building, building.getFacility());
        if (equipment != null && equipment.getEquipmentCount() != 0) {
            equipment.setEquipmentCount(equipment.getEquipmentCount() - equipmentCount);
            this.equipmentRepository.save(equipment);
        }
    }

    public CommonListResDTO setCommonListResDTO(final Page<?> page, final Object data, final boolean isPaginationNeeded) {
        final CommonListResDTO commonListResDTO = new CommonListResDTO();
        if (isPaginationNeeded) {
            final int totalPages = page.getTotalPages();
            final int currentPage = page.getNumber();
            final int itemsPerPage = page.getNumberOfElements();
            final Long totalElements = page.getTotalElements();
            commonListResDTO.setPageNumber(currentPage);
            commonListResDTO.setTotalPages(totalPages);
            commonListResDTO.setItemsPerPage(itemsPerPage);
            commonListResDTO.setTotalItem(totalElements);
            commonListResDTO.setCurrentPage(currentPage);
            commonListResDTO.setLastPage(totalPages);
            commonListResDTO.setHasMorePage(totalPages <= currentPage + 1);
            commonListResDTO.setPerPage(itemsPerPage);
            commonListResDTO.setTotal(totalElements);
        }
        commonListResDTO.setData(data);
        return commonListResDTO;
    }
}
