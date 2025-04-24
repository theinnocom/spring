package com.inTrack.spring.controller.equipmentController;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.EquipmentReqDTO;
import com.inTrack.spring.dto.requestDTO.GeneratorReqDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.equipmentService.BoilerService;
import com.inTrack.spring.service.equipmentService.GeneratorService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class GeneratorController extends BaseController {

    @Autowired
    private GeneratorService generatorService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/generator")
    public ResponseDTO createGenerator(@RequestBody final GeneratorReqDTO equipmentReqDTO) {
        return super.success(responseDTO, this.generatorService.createGenerator(equipmentReqDTO, null), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/generator")
    public ResponseDTO getGenerator(@RequestParam(required = false) final Long generatorId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        return super.success(responseDTO, this.generatorService.getGenerator(generatorId, equipmentDetailsReqVO), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/generator")
    public ResponseDTO updateGenerator(@RequestBody final GeneratorReqDTO equipmentReqDTO, @RequestParam final Long generatorId) {
        return super.success(responseDTO, this.generatorService.updateGenerator(generatorId, equipmentReqDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/generator")
    public ResponseDTO deleteGenerator(@RequestParam final Long generatorId, @RequestParam final boolean activeStatus, @RequestParam(defaultValue = "false") final boolean isPermanentDelete) {
        this.generatorService.deleteGenerator(generatorId, activeStatus, isPermanentDelete);
        return super.success(responseDTO, null, ApplicationMessageStore.SUCCESS);
    }

    /**
     * Drop down APIs
     */

    @GetMapping(value = "/drop-down/tier-level")
    public ResponseDTO getTireLevel(@RequestParam(required = false) final String search) {
        return super.success(responseDTO, this.generatorService.getTireLevel(search), ApplicationMessageStore.SUCCESS);
    }
}
