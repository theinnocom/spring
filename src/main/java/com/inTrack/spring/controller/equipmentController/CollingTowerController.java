package com.inTrack.spring.controller.equipmentController;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.requestDTO.CoolingTowerReqDTO;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.equipmentService.CoolingTowerService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vijayan
 */

@RestController
public class CollingTowerController extends BaseController {

    @Autowired
    private CoolingTowerService collingTowerService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/cooling-tower")
    public ResponseDTO createCollingTower(@RequestBody final CoolingTowerReqDTO equipmentReqDTO) {
        return super.success(responseDTO, this.collingTowerService.createCoolingTower(equipmentReqDTO, null), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/cooling-tower")
    public ResponseDTO getCoolingTower(@RequestParam(required = false) final Long collingTowerId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        return super.success(responseDTO, this.collingTowerService.getCoolingTower(collingTowerId, equipmentDetailsReqVO), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/cooling-tower")
    public ResponseDTO updateCoolingTower(@RequestBody final CoolingTowerReqDTO equipmentReqDTO, @RequestParam final Long coolingTowerId) {
        return super.success(responseDTO, this.collingTowerService.updateCoolingTower(coolingTowerId, equipmentReqDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/cooling-tower")
    public ResponseDTO deleteCoolingTower(@RequestParam final Long coolingTowerId, @RequestParam final boolean activeStatus, @RequestParam(defaultValue = "false") final boolean isPermanentDelete) {
        this.collingTowerService.deleteCoolingTower(coolingTowerId, activeStatus, isPermanentDelete);
        return super.success(responseDTO, null, ApplicationMessageStore.SUCCESS);
    }

    /**
     * Drop down APIs
     */

    @GetMapping(value = "/drop-down/cooling-tower-type")
    public ResponseDTO getTypeOfCoolingTower(@RequestParam(required = false) final String search) {
        return super.success(responseDTO, this.collingTowerService.getTypeOfCoolingTower(search), ApplicationMessageStore.SUCCESS);
    }
}
