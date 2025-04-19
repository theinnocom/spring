package com.inTrack.spring.controller;

import com.inTrack.spring.dto.request.FacilityReqDTO;
import com.inTrack.spring.dto.common.ResponseDTO;
import com.inTrack.spring.service.FacilityService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vijayan
 */

@RestController
public class FacilityController extends BaseController {

    @Autowired
    private FacilityService facilityService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/facility")
    public ResponseDTO createFacility(@RequestBody final FacilityReqDTO facilityReqDTO) {
        return super.success(responseDTO, this.facilityService.createFacility(facilityReqDTO), ApplicationMessageStore.FACILITY_CREATE_SUCCESS);
    }

    @GetMapping(value = "/facilities")
    public ResponseDTO getFacility(@RequestParam(required = false) final Long id) {
        return super.success(responseDTO, this.facilityService.getFacilities(id), ApplicationMessageStore.FACILITY_FETCH_SUCCESS);
    }

    @PutMapping(value = "/facility")
    public ResponseDTO updateFacility(@RequestBody final FacilityReqDTO facilityReqDTO) {
        return super.success(responseDTO, this.facilityService.updateFacility(facilityReqDTO), ApplicationMessageStore.FACILITY_UPDATE_SUCCESS);
    }

    @DeleteMapping(value = "/facility")
    public ResponseDTO deleteFacility(@RequestParam final Long id) {
        this.facilityService.deleteFacility(id);
        return super.success(responseDTO, null, ApplicationMessageStore.FACILITY_DELETE_SUCCESS);
    }
}
