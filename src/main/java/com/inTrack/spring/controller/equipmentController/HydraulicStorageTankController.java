package com.inTrack.spring.controller.equipmentController;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.HydraulicStorageTankReqDTO;
import com.inTrack.spring.dto.requestDTO.LandFillReqDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.equipmentService.HydraulicStorageTankService;
import com.inTrack.spring.service.equipmentService.LandFillService;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author vijayan
 */

@RestController
@RequiredArgsConstructor
public class HydraulicStorageTankController extends BaseController {

    private final HydraulicStorageTankService hydraulicStorageTankService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/hydraulic-storage")
    public ResponseDTO createStorageTank(@RequestBody final HydraulicStorageTankReqDTO equipmentReqDTO) {
        return super.success(responseDTO, this.hydraulicStorageTankService.createStorageTank(equipmentReqDTO, null), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/hydraulic-storage")
    public ResponseDTO getStorageTank(@RequestParam(required = false) final Long tankId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        return super.success(responseDTO, this.hydraulicStorageTankService.getStorageTank(tankId, equipmentDetailsReqVO), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/hydraulic-storage")
    public ResponseDTO updateStorageTank(@RequestBody final HydraulicStorageTankReqDTO equipmentReqDTO, @RequestParam final Long tankId) {
        return super.success(responseDTO, this.hydraulicStorageTankService.updateStorageTank(equipmentReqDTO, tankId), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/hydraulic-storage")
    public ResponseDTO deleteStorageTank(@RequestParam final Long tankId, @RequestParam final boolean activeStatus, @RequestParam(defaultValue = "false") final boolean isPermanentDelete) {
        this.hydraulicStorageTankService.deleteStorageTank(tankId, activeStatus, isPermanentDelete);
        return super.success(responseDTO, null, ApplicationMessageStore.SUCCESS);
    }
}
