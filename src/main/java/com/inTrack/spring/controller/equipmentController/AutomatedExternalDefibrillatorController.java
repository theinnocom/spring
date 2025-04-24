package com.inTrack.spring.controller.equipmentController;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.requestDTO.AutomatedExternalDefibrillatorDTO;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.equipmentService.AutomatedExternalDefibrillatorService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vijayan
 */

@RestController
public class AutomatedExternalDefibrillatorController extends BaseController {

    @Autowired
    private AutomatedExternalDefibrillatorService automatedExternalDefibrillatorService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/external-defibrillator")
    public ResponseDTO createLandFill(@RequestBody final AutomatedExternalDefibrillatorDTO equipmentReqDTO) {
        return super.success(responseDTO, this.automatedExternalDefibrillatorService.createAutomatedExternalDefibrillator(equipmentReqDTO, null), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/external-defibrillator")
    public ResponseDTO getLandFill(@RequestParam(required = false) final Long externalDefibrillatorId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        return super.success(responseDTO, this.automatedExternalDefibrillatorService.getAutomatedExternalDefibrillator(externalDefibrillatorId, equipmentDetailsReqVO), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/external-defibrillator")
    public ResponseDTO updateLandFill(@RequestBody final AutomatedExternalDefibrillatorDTO equipmentReqDTO, @RequestParam final Long externalDefibrillatorId) {
        return super.success(responseDTO, this.automatedExternalDefibrillatorService.updateAutomatedExternalDefibrillator(externalDefibrillatorId, equipmentReqDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/external-defibrillator")
    public ResponseDTO deleteLandFill(@RequestParam final Long externalDefibrillatorId, @RequestParam final boolean activeStatus, @RequestParam(defaultValue = "false") final boolean isPermanentDelete) {
        this.automatedExternalDefibrillatorService.deleteAutomatedExternalDefibrillator(externalDefibrillatorId, activeStatus, isPermanentDelete);
        return super.success(responseDTO, null, ApplicationMessageStore.SUCCESS);
    }
}
