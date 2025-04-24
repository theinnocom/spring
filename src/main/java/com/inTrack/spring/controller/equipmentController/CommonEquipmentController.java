package com.inTrack.spring.controller.equipmentController;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.equipmentService.CommonEquipmentService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonEquipmentController extends BaseController {

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @Autowired
    private CommonEquipmentService commonEquipmentService;

    @GetMapping(value = "/equipment-details")
    public ResponseDTO getEquipmentDetails(@RequestParam(required = false) final Long buildingId) {
        return super.success(responseDTO, this.commonEquipmentService.getEquipmentDetails(buildingId), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/equipment/floor")
    public ResponseDTO getEquipmentListBasedOnFloors() {
        return super.success(responseDTO, this.commonEquipmentService.getEquipmentListBasedOnFloors(), ApplicationMessageStore.EQUIPMENT_FETCH_SUCCESS);
    }

    @GetMapping(value = "/equipment/type")
    public ResponseDTO getAllEquipmentType() {
        return super.success(responseDTO, this.commonEquipmentService.getAllEquipmentType(), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/management-type")
    public ResponseDTO getManagementType() {
        return super.success(responseDTO, this.commonEquipmentService.getManagementType(), ApplicationMessageStore.SUCCESS);
    }
}
