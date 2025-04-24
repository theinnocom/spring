package com.inTrack.spring.controller;

import com.inTrack.spring.dto.common.SpccDto;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.SpccService;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SpccController extends BaseController {

    private final SpccService spccService;

    @PostMapping(value = "/spcc")
    public ResponseDTO createSpcc(@RequestBody final SpccDto spccDto) {
        return success(new ResponseDTO(), this.spccService.createSpcc(spccDto), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/spcc/facility/{id}")
    public ResponseDTO getAllSpcc(@PathVariable final Long id) {
        return success(new ResponseDTO(), this.spccService.getAllSpccByFacility(id), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/spcc/{id}")
    public ResponseDTO getSpcc(@PathVariable final Long id) {
        return success(new ResponseDTO(), this.spccService.getSpccById(id), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/spcc")
    public ResponseDTO updateSpcc(@RequestBody final SpccDto spccDto) {
        return success(new ResponseDTO(), this.spccService.updateSpcc(spccDto), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/spcc/{id}")
    public ResponseDTO deleteSpcc(@PathVariable final Long id) {
        this.spccService.deleteSpcc(id);
        return success(new ResponseDTO(), ApplicationMessageStore.SUCCESS);
    }

    /**
     * Drop down API
     */

    @GetMapping(value = "/audit-cycle")
    public ResponseDTO getAuditCycle() {
        return success(new ResponseDTO(), this.spccService.getAuditCycle(), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/certificate-type")
    public ResponseDTO getAllCertificateTypes() {
        return success(new ResponseDTO(), this.spccService.getAllCertificateTypes(), ApplicationMessageStore.SUCCESS);
    }


    /**
     * PBS capacity
     */

    @GetMapping(value = "/pbs-capacity")
    public ResponseDTO getPbsCapacity(@RequestParam final Long pbsNo, @RequestParam final Long facilityId) {
        return success(new ResponseDTO(), this.spccService.getPbsCapacity(pbsNo, facilityId), ApplicationMessageStore.SUCCESS);
    }
}
