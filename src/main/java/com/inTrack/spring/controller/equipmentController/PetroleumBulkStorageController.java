package com.inTrack.spring.controller.equipmentController;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.EquipmentReqDTO;
import com.inTrack.spring.dto.requestDTO.PetroleumBulkStorageReqDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.equipmentService.LandFillService;
import com.inTrack.spring.service.equipmentService.PetroleumBulkStorageService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vijayan
 */

@RestController
public class PetroleumBulkStorageController extends BaseController {

    @Autowired
    private PetroleumBulkStorageService petroleumBulkStorageService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/petroleum-storage")
    public ResponseDTO createLandFill(@RequestBody final PetroleumBulkStorageReqDTO equipmentReqDTO) {
        return super.success(responseDTO, this.petroleumBulkStorageService.createPetroleumBulkStorage(equipmentReqDTO, null), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/petroleum-storage")
    public ResponseDTO getLandFill(@RequestParam(required = false) final Long petroleumStorageId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        return super.success(responseDTO, this.petroleumBulkStorageService.getPetroleumBulkStorage(petroleumStorageId, equipmentDetailsReqVO), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/petroleum-storage")
    public ResponseDTO updateLandFill(@RequestBody final PetroleumBulkStorageReqDTO equipmentReqDTO, @RequestParam final Long petroleumStorageId) {
        return super.success(responseDTO, this.petroleumBulkStorageService.updatePetroleumBulkStorage(petroleumStorageId, equipmentReqDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/petroleum-storage")
    public ResponseDTO deleteLandFill(@RequestParam final Long petroleumStorageId, @RequestParam final boolean activeStatus, @RequestParam(defaultValue = "false") final boolean isPermanentDelete) {
        this.petroleumBulkStorageService.deletePetroleumBulkStorage(petroleumStorageId, activeStatus, isPermanentDelete);
        return super.success(responseDTO, null, ApplicationMessageStore.SUCCESS);
    }
}
