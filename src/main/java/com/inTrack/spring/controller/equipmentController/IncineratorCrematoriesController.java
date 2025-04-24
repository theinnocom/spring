package com.inTrack.spring.controller.equipmentController;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.EquipmentReqDTO;
import com.inTrack.spring.dto.requestDTO.IncineratorCrematoriesReqDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.entity.equipmentEntity.IncineratorFuelConsumption;
import com.inTrack.spring.service.equipmentService.BoilerService;
import com.inTrack.spring.service.equipmentService.IncineratorCrematoriesService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vijayan
 */

@RestController
public class IncineratorCrematoriesController extends BaseController {

    @Autowired
    private IncineratorCrematoriesService incineratorCrematoriesService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/incinerator-crematories")
    public ResponseDTO createIncineratorCrematories(@RequestBody final IncineratorCrematoriesReqDTO equipmentReqDTO) {
        return super.success(responseDTO, this.incineratorCrematoriesService.createIncineratorCrematories(equipmentReqDTO, null), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/incinerator-crematories")
    public ResponseDTO getIncineratorCrematories(@RequestParam(required = false) final Long id, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        return super.success(responseDTO, this.incineratorCrematoriesService.getIncineratorCrematories(id, equipmentDetailsReqVO), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/incinerator-crematories")
    public ResponseDTO updateIncineratorCrematories(@RequestBody final IncineratorCrematoriesReqDTO equipmentReqDTO, @RequestParam final Long id) {
        return super.success(responseDTO, this.incineratorCrematoriesService.updateIncineratorCrematories(id, equipmentReqDTO), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/incinerator-crematories/fuel-consumption")
    public ResponseDTO updateIncineratorFuelConsumption(@RequestBody final IncineratorFuelConsumption equipmentReqDTO) {
        return super.success(responseDTO, this.incineratorCrematoriesService.updateIncineratorFuelConsumption(equipmentReqDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/incinerator-crematories")
    public ResponseDTO deleteIncineratorCrematories(@RequestParam final Long id, @RequestParam final boolean activeStatus, @RequestParam(defaultValue = "false") final boolean isPermanentDelete) {
        this.incineratorCrematoriesService.deleteIncineratorCrematories(id, activeStatus, isPermanentDelete);
        return super.success(responseDTO, null, ApplicationMessageStore.SUCCESS);
    }

    /**
     * Drop down
     */

    @GetMapping(value = "/unit-type")
    public ResponseDTO getUnitType() {
        return super.success(responseDTO, this.incineratorCrematoriesService.getUnitType(), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/waste-type-burner")
    public ResponseDTO getWasteTypeBurner() {
        return super.success(responseDTO, this.incineratorCrematoriesService.getWasteTypeBurner(), ApplicationMessageStore.SUCCESS);
    }
}
