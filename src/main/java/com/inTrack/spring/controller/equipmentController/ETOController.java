package com.inTrack.spring.controller.equipmentController;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.requestDTO.ETOReqDTO;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.EquipmentReqDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.equipmentService.ETOService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vijayan
 */

@RestController
public class ETOController extends BaseController {

    @Autowired
    private ETOService etoService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/eto")
    public ResponseDTO createETOEquipment(@RequestBody final ETOReqDTO equipmentReqDTO) {
        return super.success(responseDTO, this.etoService.createETOEquipment(equipmentReqDTO, null), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/eto")
    public ResponseDTO getETOEquipment(@RequestParam(required = false) final Long etoId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        return super.success(responseDTO, this.etoService.getETOEquipment(etoId, equipmentDetailsReqVO), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/eto")
    public ResponseDTO updateETOEquipment(@RequestBody final ETOReqDTO equipmentReqDTO, @RequestParam final Long etoId) {
        return super.success(responseDTO, this.etoService.updateETOEquipment(etoId, equipmentReqDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/eto")
    public ResponseDTO deleteETOEquipment(@RequestParam final Long etoId, @RequestParam final boolean activeStatus, @RequestParam(defaultValue = "false") final boolean isPermanentDelete) {
        this.etoService.deleteETOEquipment(etoId, activeStatus, isPermanentDelete);
        return super.success(responseDTO, null, ApplicationMessageStore.SUCCESS);
    }
}
