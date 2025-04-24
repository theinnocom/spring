package com.inTrack.spring.controller.equipmentController;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.EquipmentReqDTO;
import com.inTrack.spring.dto.requestDTO.FumeHoodReqDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.equipmentService.FumeHoodService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vijayan
 */

@RestController
public class FumeHoodController extends BaseController {

    @Autowired
    private FumeHoodService fumeHoodService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/fume-hood")
    public ResponseDTO createFumeHood(@RequestBody final FumeHoodReqDTO equipmentReqDTO) {
        return super.success(responseDTO, this.fumeHoodService.createFumeHood(equipmentReqDTO, null), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/fume-hood")
    public ResponseDTO getFumeHood(@RequestParam(required = false) final Long fumeHoodId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        return super.success(responseDTO, this.fumeHoodService.getFumeHood(fumeHoodId, equipmentDetailsReqVO), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/fume-hood")
    public ResponseDTO updateFumeHood(@RequestBody final FumeHoodReqDTO equipmentReqDTO, @RequestParam final Long fumeHoodId) {
        return super.success(responseDTO, this.fumeHoodService.updateFumeHood(fumeHoodId, equipmentReqDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/fume-hood")
    public ResponseDTO deleteFumeHood(@RequestParam final Long fumeHoodId, @RequestParam final boolean activeStatus, @RequestParam(defaultValue = "false") final boolean isPermanentDelete) {
        this.fumeHoodService.deleteFumeHood(fumeHoodId, activeStatus, isPermanentDelete);
        return super.success(responseDTO, null, ApplicationMessageStore.SUCCESS);
    }
}
