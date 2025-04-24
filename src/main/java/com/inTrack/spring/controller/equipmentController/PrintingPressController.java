package com.inTrack.spring.controller.equipmentController;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.EquipmentReqDTO;
import com.inTrack.spring.dto.requestDTO.PrintingPressReqDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.equipmentService.BoilerService;
import com.inTrack.spring.service.equipmentService.PrintingPressService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vijayan
 */

@RestController
public class PrintingPressController extends BaseController {

    @Autowired
    private PrintingPressService printingPressService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/printing-press")
    public ResponseDTO createPrintingPress(@RequestBody final PrintingPressReqDTO equipmentReqDTO) {
        return super.success(responseDTO, this.printingPressService.createPrintingPress(equipmentReqDTO, null), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/printing-press")
    public ResponseDTO getPrintingPress(@RequestParam(required = false) final Long printerId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        return super.success(responseDTO, this.printingPressService.getPrintingPress(printerId, equipmentDetailsReqVO), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/printing-press")
    public ResponseDTO updatePrintingPress(@RequestBody final PrintingPressReqDTO equipmentReqDTO, @RequestParam final Long printerId) {
        return super.success(responseDTO, this.printingPressService.updatePrintingPress(printerId, equipmentReqDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/printing-press")
    public ResponseDTO deletePrintingPress(@RequestParam final Long printerId, @RequestParam final boolean activeStatus, @RequestParam(defaultValue = "false") final boolean isPermanentDelete) {
        this.printingPressService.deletePrintingPress(printerId, activeStatus, isPermanentDelete);
        return super.success(responseDTO, null, ApplicationMessageStore.SUCCESS);
    }
}
