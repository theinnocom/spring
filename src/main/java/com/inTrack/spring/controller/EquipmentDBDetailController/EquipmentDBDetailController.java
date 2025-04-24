package com.inTrack.spring.controller.EquipmentDBDetailController;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.entity.EquipmentDBDetailEntity.EquipmentDetail;
import com.inTrack.spring.service.EquipmentDBDetailService.EquipmentDBDetailService;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EquipmentDBDetailController extends BaseController {

    private final EquipmentDBDetailService equipmentDBDetailService;

    @GetMapping(value = "/equipment-db")
    public ResponseDTO getDbDetail(@RequestParam final String equipmentName) {
        return success(new ResponseDTO(), this.equipmentDBDetailService.getTableColumnNamesAndTypes(equipmentName), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/equipment-agency")
    public ResponseDTO getAgencyDbDetail(@RequestParam final String equipmentName) {
        return success(new ResponseDTO(), this.equipmentDBDetailService.getAgencyTableColumnNamesAndTypes(equipmentName), ApplicationMessageStore.SUCCESS);
    }

    @PostMapping(value = "/equipment-detail")
    public ResponseDTO addEquipmentDetail(@RequestBody final List<EquipmentDetail> equipmentDetails) {
        this.equipmentDBDetailService.addEquipmentDetail(equipmentDetails);
        return success(new ResponseDTO(), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/equipment-detail")
    public ResponseDTO getEquipmentDetail(final String equipmentName) {
        return success(new ResponseDTO(), this.equipmentDBDetailService.getEquipmentDetail(equipmentName), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/general/equipment-detail")
    public ResponseDTO getGeneralEquipmentDetail() {
        return success(new ResponseDTO(), this.equipmentDBDetailService.getGeneralEquipmentDetail(), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/equipment/column-detail")
    public ResponseDTO getEquipmentColumnDetail(final String equipmentName) {
        return success(new ResponseDTO(), this.equipmentDBDetailService.getEquipmentColumnDetail(equipmentName), ApplicationMessageStore.SUCCESS);
    }

}
