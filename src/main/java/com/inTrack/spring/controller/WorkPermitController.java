package com.inTrack.spring.controller;

import com.inTrack.spring.dto.common.WorkPermitDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.WorkPermitService;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class WorkPermitController extends BaseController {

    private final WorkPermitService workPermitService;

    @PostMapping(value = "/work-permit")
    public ResponseDTO createWorkPermit(@RequestBody final WorkPermitDTO workPermitDTO) {
        return success(new ResponseDTO(), this.workPermitService.createWorkPermit(workPermitDTO), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/work-permit")
    public ResponseDTO getWorkPermit(@RequestParam final Long id) {
        return success(new ResponseDTO(), this.workPermitService.getWorkPermit(id), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/work-permit/building")
    public ResponseDTO getALlWorkPermit(@RequestParam final Long buildingId) {
        return success(new ResponseDTO(), this.workPermitService.getAllWorkPermits(buildingId), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/work-permit")
    public ResponseDTO updateWorkPermit(@RequestBody final WorkPermitDTO workPermitDTO) {
        return success(new ResponseDTO(), this.workPermitService.updateWorkPermit(workPermitDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/work-permit")
    public ResponseDTO deleteWorkPermit(@RequestParam final Long id) {
        this.workPermitService.deleteWorkPermit(id);
        return success(new ResponseDTO(), ApplicationMessageStore.SUCCESS);
    }
}
