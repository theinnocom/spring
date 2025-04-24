package com.inTrack.spring.controller;

import com.inTrack.spring.dto.requestDTO.ViolationReqDTO;
import com.inTrack.spring.dto.requestDTO.ViolationSearchDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.ViolationService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Vijayan
 */

@RestController
public class ViolationController extends BaseController {

    @Autowired
    private ViolationService violationService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/violation")
    public ResponseDTO createViolation(@RequestBody final ViolationReqDTO violationReqDTO) {
        return super.success(responseDTO, this.violationService.createViolation(violationReqDTO, null), ApplicationMessageStore.VIOLATION_CREATE_SUCCESS);
    }

    @GetMapping(value = "/violation")
    public ResponseDTO getViolations(@RequestParam(required = false) final Long violationId, @RequestParam final Long buildingId, @RequestParam(defaultValue = "false") final Boolean deleteStatus) {
        return super.success(responseDTO, this.violationService.getViolations(violationId, buildingId, deleteStatus), ApplicationMessageStore.VIOLATION_FETCH_SUCCESS);
    }

    @PutMapping(value = "/violation/{violationId}")
    public ResponseDTO updateViolation(@PathVariable final Long violationId, @RequestBody final ViolationReqDTO violationReqDTO) {
        return super.success(responseDTO, this.violationService.updateViolation(violationId, violationReqDTO), ApplicationMessageStore.VIOLATION_UPDATE_SUCCESS);
    }

    @DeleteMapping(value = "/violation")
    public ResponseDTO deleteViolation(@RequestParam final Long violationId) {
        this.violationService.deleteViolation(violationId);
        return super.success(responseDTO, ApplicationMessageStore.VIOLATION_DELETE_SUCCESS);
    }

    @GetMapping("/violation/count")
    public ResponseDTO getViolationCount(@RequestParam final Long buildingId) {
        return super.success(responseDTO, this.violationService.getViolationCount(buildingId), ApplicationMessageStore.VIOLATION_COUNT_FETCH_SUCCESS);
    }

    @GetMapping(value = "/agency")
    public ResponseDTO getAgencies() {
        return super.success(responseDTO, this.violationService.getAgencies(), ApplicationMessageStore.AGENCIES_FETCH_SUCCESS);
    }

    @GetMapping(value = "/certificate-of-correction")
    public ResponseDTO getCertificateOfCorrection() {
        return super.success(responseDTO, this.violationService.getCertificateOfCorrection(), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/issuing-agency")
    public ResponseDTO getIssuingAgency() {
        return super.success(responseDTO, this.violationService.getAllIssuingAgencies(), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/hearing-status")
    public ResponseDTO getHearingStatus() {
        return super.success(responseDTO, this.violationService.getAllHearingStatusList(), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/search-violation")
    public ResponseDTO getSearchViolation(@RequestBody final ViolationSearchDTO violationSearchDTO) {
        return super.success(responseDTO, this.violationService.getViolations(violationSearchDTO), ApplicationMessageStore.SUCCESS);
    }
}
