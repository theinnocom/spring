package com.inTrack.spring.controller.equipmentController;


import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.requestDTO.ElevatorReqDTO;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.equipmentService.ElevatorService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vijayan
 */

@RestController
public class elevatorController extends BaseController {

    @Autowired
    private ElevatorService elevatorService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/elevator")
    public ResponseDTO createElevator(@RequestBody final ElevatorReqDTO elevatorCreateReqDTO) {
        return super.success(responseDTO, this.elevatorService.createElevator(elevatorCreateReqDTO, null), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/elevator")
    public ResponseDTO getElevator(@RequestParam(required = false) final Long id, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        return super.success(responseDTO, this.elevatorService.getElevator(id, equipmentDetailsReqVO), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/elevator")
    public ResponseDTO updateElevator(@RequestBody final ElevatorReqDTO elevatorReqDTO, @RequestParam final Long elevatorId) {
        return super.success(responseDTO, this.elevatorService.updateElevator(elevatorReqDTO, elevatorId), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/status")
    public ResponseDTO getElevatorStatus() {
        return super.success(responseDTO, this.elevatorService.getElevatorStatus(), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/elevator")
    public ResponseDTO deleteLandFill(@RequestParam final Long elevatorId, @RequestParam final boolean activeStatus, @RequestParam(defaultValue = "false") final boolean isPermanentDelete) {
        this.elevatorService.deleteElevator(elevatorId, activeStatus, isPermanentDelete);
        return super.success(responseDTO, null, ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/device-type")
    public ResponseDTO getDeviceType() {
        return super.success(responseDTO, this.elevatorService.getDeviceType(), ApplicationMessageStore.SUCCESS);
    }
}
