package com.inTrack.spring.controller.equipmentController;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.KitchenHoodFireSuppressionDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.equipmentService.KitchenHoodFireSuppressionService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vijayan
 */

@RestController
public class KitchenHoodFireSuppressionController extends BaseController {

    @Autowired
    private KitchenHoodFireSuppressionService kitchenHoodFireSuppressionService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/kitchen-hood")
    public ResponseDTO createKitchenHoodFireSuppression(@RequestBody final KitchenHoodFireSuppressionDTO equipmentReqDTO) {
        return super.success(responseDTO, this.kitchenHoodFireSuppressionService.createKitchenHoodFireSuppression(equipmentReqDTO, null), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/kitchen-hood")
    public ResponseDTO getKitchenHoodFireSuppression(@RequestParam(required = false) final Long kitchenHoodId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        return super.success(responseDTO, this.kitchenHoodFireSuppressionService.getKitchenHoodFireSuppression(kitchenHoodId, equipmentDetailsReqVO), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/kitchen-hood")
    public ResponseDTO updateKitchenHoodFireSuppression(@RequestBody final KitchenHoodFireSuppressionDTO equipmentReqDTO, @RequestParam final Long kitchenHoodId) {
        return super.success(responseDTO, this.kitchenHoodFireSuppressionService.updateKitchenHoodFireSuppression(kitchenHoodId, equipmentReqDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/kitchen-hood")
    public ResponseDTO deleteKitchenHoodFireSuppression(@RequestParam final Long kitchenHoodId, @RequestParam final boolean activeStatus, @RequestParam(defaultValue = "false") final boolean isPermanentDelete) {
        this.kitchenHoodFireSuppressionService.deleteKitchenHoodFireSuppression(kitchenHoodId, activeStatus, isPermanentDelete);
        return super.success(responseDTO, null, ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/suppression-type")
    public ResponseDTO getSuppressionType() {
        return super.success(responseDTO, this.kitchenHoodFireSuppressionService.getSuppressionType(), ApplicationMessageStore.SUCCESS);
    }
}
