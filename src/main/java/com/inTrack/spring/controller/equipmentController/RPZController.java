package com.inTrack.spring.controller.equipmentController;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.EquipmentReqDTO;
import com.inTrack.spring.dto.requestDTO.RPZReqDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.equipmentService.RPZService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vijayan
 */

@RestController
public class RPZController extends BaseController {

    @Autowired
    private RPZService rpzService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/rpz")
    public ResponseDTO createRPZEquipment(@RequestBody final RPZReqDTO equipmentReqDTO) {
        return super.success(responseDTO, this.rpzService.createRPZEquipment(equipmentReqDTO, null), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/rpz")
    public ResponseDTO getRPZEquipment(@RequestParam(required = false) final Long rpzId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        return super.success(responseDTO, this.rpzService.getRPZEquipment(rpzId, equipmentDetailsReqVO), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/rpz")
    public ResponseDTO updateRPZEquipment(@RequestBody final RPZReqDTO equipmentReqDTO, @RequestParam final Long rpzId) {
        return super.success(responseDTO, this.rpzService.updateRPZEquipment(rpzId, equipmentReqDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/rpz")
    public ResponseDTO deleteRPZEquipment(@RequestParam final Long rpzId, @RequestParam final boolean activeStatus, @RequestParam(defaultValue = "false") final boolean isPermanentDelete) {
        this.rpzService.deleteRPZEquipment(rpzId, activeStatus, isPermanentDelete);
        return super.success(responseDTO, null, ApplicationMessageStore.SUCCESS);
    }

    /**
     * Drop down APIs
     */

    @GetMapping(value = "/drop-down/rpz-type")
    public ResponseDTO getTypeOfRpz(@RequestParam(required = false) final String search) {
        return super.success(responseDTO, this.rpzService.getTypeOfRpz(search), ApplicationMessageStore.SUCCESS);
    }
}
