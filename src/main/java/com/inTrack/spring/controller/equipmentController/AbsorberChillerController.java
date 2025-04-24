package com.inTrack.spring.controller.equipmentController;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.requestDTO.ChillerReqDTO;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.EquipmentReqDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.equipmentService.AbsorberChillerService;
import com.inTrack.spring.service.equipmentService.BoilerService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vijayan
 */

@RestController
public class AbsorberChillerController extends BaseController {

    @Autowired
    private AbsorberChillerService absorberChillerService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/absorber-chiller")
    public ResponseDTO createAbsorberChiller(@RequestBody final ChillerReqDTO equipmentReqDTO) {
        return super.success(responseDTO, this.absorberChillerService.createAbsorberChiller(equipmentReqDTO, null), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/absorber-chiller")
    public ResponseDTO getAbsorberChillers(@RequestParam(required = false) final Long chillerId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        return super.success(responseDTO, this.absorberChillerService.getAbsorberChiller(chillerId, equipmentDetailsReqVO), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/absorber-chiller")
    public ResponseDTO updateAbsorberChiller(@RequestBody final ChillerReqDTO equipmentReqDTO, @RequestParam final Long chillerId) {
        return super.success(responseDTO, this.absorberChillerService.updateAbsorberChiller(chillerId, equipmentReqDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/absorber-chiller")
    public ResponseDTO deleteAbsorberChiller(@RequestParam final Long chillerId, @RequestParam final boolean activeStatus, @RequestParam(defaultValue = "false") final boolean isPermanentDelete) {
        this.absorberChillerService.deleteAbsorberChiller(chillerId, activeStatus, isPermanentDelete);
        return super.success(responseDTO, null, ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/drop-down/chiller-type")
    public ResponseDTO getChillerTypes(@RequestParam final  String search) {
        return super.success(responseDTO, this.absorberChillerService.getChillerTypes(search), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/drop-down/chiller-group")
    public ResponseDTO getChillerGroup(@RequestParam final  String search) {
        return super.success(responseDTO, this.absorberChillerService.getChillerGroup(search), ApplicationMessageStore.SUCCESS);
    }
}
