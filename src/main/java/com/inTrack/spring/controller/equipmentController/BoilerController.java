package com.inTrack.spring.controller.equipmentController;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.requestDTO.BoilerReqDTO;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.EquipmentReqDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.equipmentService.BoilerService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vijayan
 */

@RestController
public class BoilerController extends BaseController {

    @Autowired
    private BoilerService boilerService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/boiler")
    public ResponseDTO createBoiler(@RequestBody final BoilerReqDTO equipmentReqDTO) {
        return super.success(responseDTO, this.boilerService.createBoiler(equipmentReqDTO, null), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/boiler")
    public ResponseDTO getBoilers(@RequestParam(required = false) final Long boilerId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        return super.success(responseDTO, this.boilerService.getBoilers(boilerId, equipmentDetailsReqVO), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/boiler")
    public ResponseDTO updateBoiler(@RequestBody final BoilerReqDTO equipmentReqDTO, @RequestParam final Long boilerId) {
        return super.success(responseDTO, this.boilerService.updateBoiler(boilerId, equipmentReqDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/boiler")
    public ResponseDTO deleteBoiler(@RequestParam final Long boilerId, @RequestParam final boolean activeStatus, @RequestParam final boolean isPermanentDelete) {
        this.boilerService.deleteBoiler(boilerId, activeStatus, isPermanentDelete);
        return super.success(responseDTO, null, ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/drop-down/boiler-pressure")
    public ResponseDTO getBoilerPressureType(@RequestParam final String type) {
        return super.success(responseDTO, this.boilerService.getBoilerPressureType(type), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/drop-down/primary-use")
    public ResponseDTO getPrimaryUse(@RequestParam final String type) {
        return super.success(responseDTO, this.boilerService.getPrimaryUse(type), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/drop-down/test-on-fuel")
    public ResponseDTO getTestOnFuel(@RequestParam final String type) {
        return super.success(responseDTO, this.boilerService.getTestOnFuel(type), ApplicationMessageStore.SUCCESS);
    }
}
