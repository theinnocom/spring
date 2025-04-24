package com.inTrack.spring.controller.equipmentController;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.FireAlarmDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.equipmentService.FireAlarmService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vijayan
 */

@RestController
public class FireAlarmController extends BaseController {

    @Autowired
    private FireAlarmService fireAlarmService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/fire-alarm")
    public ResponseDTO createFireAlarm(@RequestBody final FireAlarmDTO fireAlarmDTO) {
        return super.success(responseDTO, this.fireAlarmService.createFireAlarm(fireAlarmDTO, null), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/fire-alarm")
    public ResponseDTO getFireAlarm(@RequestParam(required = false) final Long fireAlarmId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        return super.success(responseDTO, this.fireAlarmService.getFireAlarm(fireAlarmId, equipmentDetailsReqVO), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/fire-alarm")
    public ResponseDTO updateFireAlarm(@RequestBody final FireAlarmDTO fireAlarmDTO, @RequestParam final Long fireAlarmId) {
        return super.success(responseDTO, this.fireAlarmService.updateFireAlarm(fireAlarmId, fireAlarmDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/fire-alarm")
    public ResponseDTO deleteFireAlarm(@RequestParam final Long fireAlarmId, @RequestParam final boolean activeStatus, @RequestParam(defaultValue = "false") final boolean isPermanentDelete) {
        this.fireAlarmService.deleteFireAlarm(fireAlarmId, activeStatus, isPermanentDelete);
        return super.success(responseDTO, null, ApplicationMessageStore.SUCCESS);
    }

    /**
     * Drop down APIs
     */
    @GetMapping(value = "/drop-down/control-equipment")
    public ResponseDTO getControlEquipment(@RequestParam(required = false) final String search) {
        return super.success(responseDTO, this.fireAlarmService.getControlEquipment(search), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/drop-down/battery-type")
    public ResponseDTO getBatteryType(@RequestParam(required = false) final String search) {
        return super.success(responseDTO, this.fireAlarmService.getBatteryType(search), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/drop-down/battery-location")
    public ResponseDTO getBatteryLocation(@RequestParam(required = false) final String search) {
        return super.success(responseDTO, this.fireAlarmService.getBatteryLocation(search), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/drop-down/fiber-connection")
    public ResponseDTO getFiberOpticCableConnection(@RequestParam(required = false) final String search) {
        return super.success(responseDTO, this.fireAlarmService.getFiberOpticCableConnection(search), ApplicationMessageStore.SUCCESS);
    }
}
