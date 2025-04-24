package com.inTrack.spring.controller;

import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.EquipmentPermitService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EquipmentPermitController extends BaseController {

    @Autowired
    private EquipmentPermitService equipmentPermitService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @GetMapping(value = "/equipment/permit/{equipmentName}")
    public ResponseDTO getEquipmentPermit(@PathVariable final String equipmentName) {
        return success(responseDTO, this.equipmentPermitService.getEquipmentPermit(equipmentName), ApplicationMessageStore.SUCCESS);
    }
}
