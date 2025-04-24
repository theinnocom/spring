package com.inTrack.spring.controller.equipmentController;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.requestDTO.CogenTurbineReqDTO;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.EquipmentReqDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.equipmentService.BoilerService;
import com.inTrack.spring.service.equipmentService.CogenEngineTurbineService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vijayan
 */

@RestController
public class CogenEngineTurbineController extends BaseController {

    @Autowired
    private CogenEngineTurbineService cogenEngineTurbineService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/cogen-engine-turbine")
    public ResponseDTO createCogenEngineTurbine(@RequestBody final CogenTurbineReqDTO equipmentReqDTO) {
        return super.success(responseDTO, this.cogenEngineTurbineService.createCogenEngineTurbine(equipmentReqDTO, null), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/cogen-engine-turbine")
    public ResponseDTO getCogenEngineTurbine(@RequestParam(required = false) final Long engineId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        return super.success(responseDTO, this.cogenEngineTurbineService.getCogenEngineTurbine(engineId, equipmentDetailsReqVO), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/cogen-engine-turbine")
    public ResponseDTO updateCogenEngineTurbine(@RequestBody final CogenTurbineReqDTO equipmentReqDTO, @RequestParam final Long engineId) {
        return super.success(responseDTO, this.cogenEngineTurbineService.updateCogenEngineTurbine(engineId, equipmentReqDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/cogen-engine-turbine")
    public ResponseDTO deleteCogenEngineTurbine(@RequestParam final Long engineId, @RequestParam final boolean activeStatus, @RequestParam(defaultValue = "false") final boolean isPermanentDelete) {
        this.cogenEngineTurbineService.deleteCogenEngineTurbine(engineId, activeStatus, isPermanentDelete);
        return super.success(responseDTO, null, ApplicationMessageStore.SUCCESS);
    }
}
