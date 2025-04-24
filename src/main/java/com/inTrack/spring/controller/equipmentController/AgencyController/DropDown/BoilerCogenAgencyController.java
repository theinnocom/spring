package com.inTrack.spring.controller.equipmentController.AgencyController.DropDown;

import com.inTrack.spring.controller.BaseController;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.equipmentService.AgencyService.AgencyService;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vijayan
 */
@RestController
@RequiredArgsConstructor
public class BoilerCogenAgencyController extends BaseController {

    private final AgencyService agencyService;
    private static final ResponseDTO responseDTO = new ResponseDTO();

    @GetMapping(value = "/agency-equipment")
    public ResponseDTO getAgencyList(@RequestParam final String equipmentName) {
        return success(responseDTO, agencyService.getAgencyList(equipmentName), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/agency-equipment/bulk-oxygen")
    public ResponseDTO getBulkOxygenAgencyList() {
        return success(responseDTO, agencyService.getBulkOxygenStorageAgencies(), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/agency-equipment/cooling-tower")
    public ResponseDTO getCoolingTowerAgencyList() {
        return success(responseDTO, agencyService.getCoolingTowerAgencies(), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/agency-equipment/generator")
    public ResponseDTO getGeneratorAgencyList() {
        return success(responseDTO, agencyService.getGeneratorAgencies(), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/agency-equipment/incinerator-crematories")
    public ResponseDTO getIncineratorCrematoriesAgencyList() {
        return success(responseDTO, agencyService.getIncineratorCrematoriesAgencies(), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/agency-equipment/parameter-type")
    public ResponseDTO getParameterTypeAgencyList() {
        return success(responseDTO, agencyService.getParameterTypes(), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/agency-equipment/stack")
    public ResponseDTO getStackAgencyList() {
        return success(responseDTO, agencyService.getStackAgencies(), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/agency-equipment/inspection-type")
    public ResponseDTO getInspectionType() {
        return success(responseDTO, agencyService.getInspectionType(), ApplicationMessageStore.SUCCESS);
    }
}
