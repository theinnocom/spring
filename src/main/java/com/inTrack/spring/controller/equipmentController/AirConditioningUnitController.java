package com.inTrack.spring.controller.equipmentController;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.requestDTO.ACUnitDTO;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.EquipmentReqDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.equipmentService.AirConditioningUnitService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vijayan
 */

@RestController
public class AirConditioningUnitController extends BaseController {

    @Autowired
    private AirConditioningUnitService airConditioningUnitService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/air-conditioning-unit")
    public ResponseDTO createAirConditioningUnit(@RequestBody final ACUnitDTO equipmentReqDTO) {
        return super.success(responseDTO, this.airConditioningUnitService.createAirConditioningUnit(equipmentReqDTO, null), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/air-conditioning-unit")
    public ResponseDTO getAirConditioningUnit(@RequestParam(required = false) final Long acUnitId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        return super.success(responseDTO, this.airConditioningUnitService.getAirConditioningUnit(acUnitId, equipmentDetailsReqVO), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/air-conditioning-unit")
    public ResponseDTO updateAirConditioningUnit(@RequestBody final ACUnitDTO equipmentReqDTO, @RequestParam final Long acUnitId) {
        return super.success(responseDTO, this.airConditioningUnitService.updateAirConditioningUnit(acUnitId, equipmentReqDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/air-conditioning-unit")
    public ResponseDTO deleteAirConditioningUnit(@RequestParam final Long acUnitId, @RequestParam final boolean activeStatus, @RequestParam(defaultValue = "false") final boolean isPermanentDelete) {
        this.airConditioningUnitService.deleteAirConditioningUnit(acUnitId, activeStatus, isPermanentDelete);
        return super.success(responseDTO, null, ApplicationMessageStore.SUCCESS);
    }
}
