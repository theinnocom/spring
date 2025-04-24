package com.inTrack.spring.controller.equipmentController;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.EquipmentReqDTO;
import com.inTrack.spring.dto.requestDTO.StackReqDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.equipmentService.BoilerService;
import com.inTrack.spring.service.equipmentService.StackService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vijayan
 */

@RestController
public class StackController extends BaseController {

    @Autowired
    private StackService stackService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/stack")
    public ResponseDTO createBoiler(@RequestBody final StackReqDTO equipmentReqDTO) {
        return super.success(responseDTO, this.stackService.createStack(equipmentReqDTO, null), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/stack")
    public ResponseDTO getBoilers(@RequestParam(required = false) final Long stackId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        return super.success(responseDTO, this.stackService.getStack(stackId, equipmentDetailsReqVO), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/stack")
    public ResponseDTO updateBoiler(@RequestBody final StackReqDTO equipmentReqDTO, @RequestParam final Long stackId) {
        return super.success(responseDTO, this.stackService.updateStack(stackId, equipmentReqDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/stack")
    public ResponseDTO deleteBoiler(@RequestParam final Long stackId, @RequestParam final boolean activeStatus, final boolean isPermanentDelete) {
        this.stackService.deleteStack(stackId, activeStatus, isPermanentDelete);
        return super.success(responseDTO, null, ApplicationMessageStore.SUCCESS);
    }
}
