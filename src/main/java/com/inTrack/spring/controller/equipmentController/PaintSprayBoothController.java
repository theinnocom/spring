package com.inTrack.spring.controller.equipmentController;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.EquipmentReqDTO;
import com.inTrack.spring.dto.requestDTO.PaintSprayBoothReqDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.equipmentService.PainSprayBoothService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vijayan
 */

@RestController
public class PaintSprayBoothController extends BaseController {

    @Autowired
    private PainSprayBoothService painSprayBoothService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/paint-spray")
    public ResponseDTO createPaintSprayBooth(@RequestBody final PaintSprayBoothReqDTO equipmentReqDTO) {
        return super.success(responseDTO, this.painSprayBoothService.createPaintSprayBooth(equipmentReqDTO, null), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/paint-spray")
    public ResponseDTO getPaintSprayBooth(@RequestParam(required = false) final Long paintSprayId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        return super.success(responseDTO, this.painSprayBoothService.getPaintSprayBooth(paintSprayId, equipmentDetailsReqVO), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/paint-spray")
    public ResponseDTO updatePaintSprayBooth(@RequestBody final PaintSprayBoothReqDTO equipmentReqDTO, @RequestParam final Long paintSprayId) {
        return super.success(responseDTO, this.painSprayBoothService.updatePaintSprayBooth(paintSprayId, equipmentReqDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/paint-spray")
    public ResponseDTO deletePaintSprayBooth(@RequestParam final Long paintSprayId, @RequestParam final boolean activeStatus, @RequestParam(defaultValue = "false") final boolean isPermanentDelete) {
        this.painSprayBoothService.deletePaintSprayBooth(paintSprayId, activeStatus, isPermanentDelete);
        return super.success(responseDTO, null, ApplicationMessageStore.SUCCESS);
    }
}
