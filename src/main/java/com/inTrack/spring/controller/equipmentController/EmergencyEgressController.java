package com.inTrack.spring.controller.equipmentController;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.requestDTO.EmergencyEgressAndLightingReqDTO;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.equipmentService.EmergencyEgressService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vijayan
 */

@RestController
public class EmergencyEgressController extends BaseController {

    @Autowired
    private EmergencyEgressService emergencyEgressService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/emergency-egress")
    public ResponseDTO createEmergencyEgress(@RequestBody final EmergencyEgressAndLightingReqDTO equipmentReqDTO) {
        return super.success(responseDTO, this.emergencyEgressService.createEmergencyEgress(equipmentReqDTO, null), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/emergency-egress")
    public ResponseDTO getEmergencyEgress(@RequestParam(required = false) final Long emergencyEgressId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        return super.success(responseDTO, this.emergencyEgressService.getEmergencyEgress(emergencyEgressId, equipmentDetailsReqVO), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/emergency-egress")
    public ResponseDTO updateEmergencyEgress(@RequestBody final EmergencyEgressAndLightingReqDTO equipmentReqDTO, @RequestParam final Long emergencyEgressId) {
        return super.success(responseDTO, this.emergencyEgressService.updateEmergencyEgress(emergencyEgressId, equipmentReqDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/emergency-egress")
    public ResponseDTO deleteEmergencyEgress(@RequestParam final Long emergencyEgressId, @RequestParam final boolean activeStatus, @RequestParam(defaultValue = "false") final boolean isPermanentDelete) {
        this.emergencyEgressService.deleteEmergencyEgress(emergencyEgressId, activeStatus, isPermanentDelete);
        return super.success(responseDTO, null, ApplicationMessageStore.SUCCESS);
    }
}
