package com.inTrack.spring.controller;

import com.inTrack.spring.dto.HazardousWasteDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.HazardousWasteService;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HazardousWasteController extends BaseController {

    private final HazardousWasteService hazardousWasteService;
    private static final ResponseDTO responseDTO = new ResponseDTO();


    @PostMapping(value = "/HazardousWaste")
    public ResponseDTO saveHazardousWaste(@RequestBody final HazardousWasteDTO hazardousWasteDTO) {
        return success(responseDTO, this.hazardousWasteService.saveHazardousWaste(hazardousWasteDTO), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/HazardousWaste/{id}")
    public ResponseDTO getHazardousWaste(@PathVariable final Long id) {
        return success(responseDTO, this.hazardousWasteService.getHazardousWasteUsingId(id), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/HazardousWaste/facility")
    public ResponseDTO getHazardousWasteByFacility(@RequestParam final Long id) {
        return success(responseDTO, this.hazardousWasteService.getHazardousWaste(id), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/HazardousWaste")
    public ResponseDTO updateHazardousWaste(@RequestBody final HazardousWasteDTO hazardousWasteDTO) {
        return success(responseDTO, this.hazardousWasteService.updateHazardousWaste(hazardousWasteDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/HazardousWaste")
    public ResponseDTO deleteHazardousWaste(@RequestParam final Long id) {
        this.hazardousWasteService.deleteHazardousWaste(id);
        return success(responseDTO, ApplicationMessageStore.SUCCESS);
    }
}
