package com.inTrack.spring.controller.equipmentController;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.MiscellaneousDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.equipmentService.MiscellaneousService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vijayan
 */

@RestController
public class MiscellaneousController extends BaseController {

    @Autowired
    private MiscellaneousService miscellaneousService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/miscellaneous")
    public ResponseDTO createMiscellaneous(@RequestBody final MiscellaneousDTO equipmentReqDTO) {
        return super.success(responseDTO, this.miscellaneousService.createMiscellaneous(equipmentReqDTO, null), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/miscellaneous")
    public ResponseDTO getLandFill(@RequestParam(required = false) final Long id, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        return super.success(responseDTO, this.miscellaneousService.getMiscellaneous(id, equipmentDetailsReqVO), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/miscellaneous")
    public ResponseDTO updateMiscellaneous(@RequestBody final MiscellaneousDTO equipmentReqDTO, @RequestParam final Long id) {
        return super.success(responseDTO, this.miscellaneousService.updateMiscellaneous(equipmentReqDTO, id), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/miscellaneous")
    public ResponseDTO deleteMiscellaneous(@RequestParam final Long id, @RequestParam final boolean activeStatus, @RequestParam(defaultValue = "false") final boolean isPermanentDelete) {
        this.miscellaneousService.deleteMiscellaneous(id, activeStatus, isPermanentDelete);
        return super.success(responseDTO, null, ApplicationMessageStore.SUCCESS);
    }
}
