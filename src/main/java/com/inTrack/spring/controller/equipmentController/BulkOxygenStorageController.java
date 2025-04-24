package com.inTrack.spring.controller.equipmentController;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.requestDTO.BulkOxygenStorageReqDTO;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.equipmentService.BulkOxygenStorageService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vijayan
 */

@RestController
public class BulkOxygenStorageController extends BaseController {

    @Autowired
    private BulkOxygenStorageService bulkOxygenStorageService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/oxygen-storage")
    public ResponseDTO createLandFill(@RequestBody final BulkOxygenStorageReqDTO equipmentReqDTO) {
        return super.success(responseDTO, this.bulkOxygenStorageService.createOxygenStorageTank(equipmentReqDTO, null), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/oxygen-storage")
    public ResponseDTO getLandFill(@RequestParam(required = false) final Long oxygenStorageId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        return super.success(responseDTO, this.bulkOxygenStorageService.getOxygenStorageTank(oxygenStorageId, equipmentDetailsReqVO), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/oxygen-storage")
    public ResponseDTO updateLandFill(@RequestBody final BulkOxygenStorageReqDTO equipmentReqDTO, @RequestParam final Long oxygenStorageId) {
        return super.success(responseDTO, this.bulkOxygenStorageService.updateOxygenStorageTank(oxygenStorageId, equipmentReqDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/oxygen-storage")
    public ResponseDTO deleteLandFill(@RequestParam final Long oxygenStorageId, @RequestParam final boolean activeStatus, @RequestParam(defaultValue = "false") final boolean isPermanentDelete) {
        this.bulkOxygenStorageService.deleteOxygenStorageTank(oxygenStorageId, activeStatus, isPermanentDelete);
        return super.success(responseDTO, null, ApplicationMessageStore.SUCCESS);
    }

}
