package com.inTrack.spring.controller.equipmentController;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.equipmentService.PetroleumBulkStorageDropDownService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/drop-down")
public class PetroleumBulkStorageDropDownController extends BaseController {

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @Autowired
    private PetroleumBulkStorageDropDownService petroleumBulkStorageDropDownService;


    @GetMapping(value = "/dispenser")
    public ResponseDTO getDispenser(@RequestParam(required = false) final String search) {
        return super.success(responseDTO, this.petroleumBulkStorageDropDownService.getDispenser(search), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/external-protection")
    public ResponseDTO getExternalProtection(@RequestParam(required = false) final String search) {
        return super.success(responseDTO, this.petroleumBulkStorageDropDownService.getExternalProtection(search), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/internal-protection")
    public ResponseDTO getInternalProtection(@RequestParam(required = false) final String search) {
        return super.success(responseDTO, this.petroleumBulkStorageDropDownService.getInternalProtection(search), ApplicationMessageStore.SUCCESS);
    }


    @GetMapping(value = "/overFill-protection")
    public ResponseDTO getOverFillProtection(@RequestParam(required = false) final String search) {
        return super.success(responseDTO, this.petroleumBulkStorageDropDownService.getOverFillProtection(search), ApplicationMessageStore.SUCCESS);
    }


    @GetMapping(value = "/pipe-leak-detection")
    public ResponseDTO getPipeLeakDetection(@RequestParam(required = false) final String search) {
        return super.success(responseDTO, this.petroleumBulkStorageDropDownService.getPipeLeakDetection(search), ApplicationMessageStore.SUCCESS);
    }


    @GetMapping(value = "/piping-location")
    public ResponseDTO getPipingLocation(@RequestParam(required = false) final String search) {
        return super.success(responseDTO, this.petroleumBulkStorageDropDownService.getPipingLocation(search), ApplicationMessageStore.SUCCESS);
    }


    @GetMapping(value = "/piping-secondary-containment")
    public ResponseDTO getPipingSecondaryContainment(@RequestParam(required = false) final String search) {
        return super.success(responseDTO, this.petroleumBulkStorageDropDownService.getPipingSecondaryContainment(search), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/piping-type")
    public ResponseDTO getPipingType(@RequestParam(required = false) final String search) {
        return super.success(responseDTO, this.petroleumBulkStorageDropDownService.getPipingType(search), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/product-stored")
    public ResponseDTO getProductStored(@RequestParam(required = false) final String search) {
        return super.success(responseDTO, this.petroleumBulkStorageDropDownService.getProductStored(search), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/spill-prevention")
    public ResponseDTO getSpillPrevention(@RequestParam(required = false) final String search) {
        return super.success(responseDTO, this.petroleumBulkStorageDropDownService.getSpillPrevention(search), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/subpart")
    public ResponseDTO getSubpart(@RequestParam(required = false) final String search) {
        return super.success(responseDTO, this.petroleumBulkStorageDropDownService.getSubpart(search), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/tank-leak-detection")
    public ResponseDTO getTankLeakDetection(@RequestParam(required = false) final String search) {
        return super.success(responseDTO, this.petroleumBulkStorageDropDownService.getTankLeakDetection(search), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/tank-location")
    public ResponseDTO getTankLocation(@RequestParam(required = false) final String search) {
        return super.success(responseDTO, this.petroleumBulkStorageDropDownService.getTankLocation(search), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/tank-secondary-containment")
    public ResponseDTO getTankSecondaryContainment(@RequestParam(required = false) final String search) {
        return super.success(responseDTO, this.petroleumBulkStorageDropDownService.getTankSecondaryContainment(search), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/tank-status")
    public ResponseDTO getTankStatus(@RequestParam(required = false) final String search) {
        return super.success(responseDTO, this.petroleumBulkStorageDropDownService.getTankStatus(search), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/tank-type")
    public ResponseDTO getTankType(@RequestParam(required = false) final String search) {
        return super.success(responseDTO, this.petroleumBulkStorageDropDownService.getTankType(search), ApplicationMessageStore.SUCCESS);
    }

}
