package com.inTrack.spring.controller.equipmentController;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.EquipmentReqDTO;
import com.inTrack.spring.dto.requestDTO.LandFillReqDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.equipmentService.LandFillService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vijayan
 */

@RestController
public class LandFillController extends BaseController {

    @Autowired
    private LandFillService landFillService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/land-fill")
    public ResponseDTO createLandFill(@RequestBody final LandFillReqDTO equipmentReqDTO) {
        return super.success(responseDTO, this.landFillService.createLandFill(equipmentReqDTO, null), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/land-fill")
    public ResponseDTO getLandFill(@RequestParam(required = false) final Long landFillId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        return super.success(responseDTO, this.landFillService.getLandFill(landFillId, equipmentDetailsReqVO), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/land-fill")
    public ResponseDTO updateLandFill(@RequestBody final LandFillReqDTO equipmentReqDTO, @RequestParam final Long landFillId) {
        return super.success(responseDTO, this.landFillService.updateLandFill(landFillId, equipmentReqDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/land-fill")
    public ResponseDTO deleteLandFill(@RequestParam final Long landFillId, @RequestParam final boolean activeStatus, @RequestParam(defaultValue = "false") final boolean isPermanentDelete) {
        this.landFillService.deleteLandFill(landFillId, activeStatus, isPermanentDelete);
        return super.success(responseDTO, null, ApplicationMessageStore.SUCCESS);
    }
}
