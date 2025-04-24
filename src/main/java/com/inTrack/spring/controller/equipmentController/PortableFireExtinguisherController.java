package com.inTrack.spring.controller.equipmentController;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.requestDTO.EquipmentDetailsReqVO;
import com.inTrack.spring.dto.requestDTO.EquipmentReqDTO;
import com.inTrack.spring.dto.requestDTO.PortableFireExtinguisherReqDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.equipmentService.LandFillService;
import com.inTrack.spring.service.equipmentService.PortableFireExtinguisherService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vijayan
 */

@RestController
public class PortableFireExtinguisherController extends BaseController {

    @Autowired
    private PortableFireExtinguisherService portableFireExtinguisherService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/fire-extinguisher")
    public ResponseDTO createPortableFireExtinguisher(@RequestBody final PortableFireExtinguisherReqDTO equipmentReqDTO) {
        return super.success(responseDTO, this.portableFireExtinguisherService.createPortableFireExtinguisher(equipmentReqDTO, null), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/fire-extinguisher")
    public ResponseDTO getPortableFireExtinguisher(@RequestParam(required = false) final Long fireExtinguisherId, final EquipmentDetailsReqVO equipmentDetailsReqVO) {
        return super.success(responseDTO, this.portableFireExtinguisherService.getPortableFireExtinguisher(fireExtinguisherId, equipmentDetailsReqVO), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/fire-extinguisher")
    public ResponseDTO updatePortableFireExtinguisher(@RequestBody final PortableFireExtinguisherReqDTO equipmentReqDTO, @RequestParam final Long fireExtinguisherId) {
        return super.success(responseDTO, this.portableFireExtinguisherService.updatePortableFireExtinguisher(fireExtinguisherId, equipmentReqDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/fire-extinguisher")
    public ResponseDTO deletePortableFireExtinguisher(@RequestParam final Long fireExtinguisherId, @RequestParam final boolean activeStatus, @RequestParam(defaultValue = "false") final boolean isPermanentDelete) {
        this.portableFireExtinguisherService.deletePortableFireExtinguisher(fireExtinguisherId, activeStatus, isPermanentDelete);
        return super.success(responseDTO, null, ApplicationMessageStore.SUCCESS);
    }

    /**
     * Drop down APIs
     */

    @GetMapping(value = "/drop-down/class-type")
    public ResponseDTO getClassType(@RequestParam(required = false) final String search) {
        return super.success(responseDTO, this.portableFireExtinguisherService.getClassType(search), ApplicationMessageStore.SUCCESS);
    }
}
