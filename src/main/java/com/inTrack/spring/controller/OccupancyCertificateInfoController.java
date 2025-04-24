package com.inTrack.spring.controller;

import com.inTrack.spring.dto.common.OccupancyCertificateInformationDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.OccupancyCertificateInformationService;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OccupancyCertificateInfoController extends BaseController {

    private final OccupancyCertificateInformationService informationService;

    @PostMapping(value = "/occupancy-certification")
    public ResponseDTO createOccupancyCertificateInformation(@RequestBody final OccupancyCertificateInformationDTO informationDTO) {
        return success(new ResponseDTO(), this.informationService.createOccupancyCertificateInformation(informationDTO), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/occupancy-certification")
    public ResponseDTO getOccupancyCertificateInformation(@RequestParam final Long id) {
        return success(new ResponseDTO(), this.informationService.getOccupancyCertificateInformation(id), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/occupancy-certification/building")
    public ResponseDTO getAllOccupancyCertificateInformation(@RequestParam final Long buildingId) {
        return success(new ResponseDTO(), this.informationService.getAllOccupancyCertificateInformation(buildingId), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/occupancy-certification")
    public ResponseDTO updateOccupancyCertificateInformation(@RequestBody final OccupancyCertificateInformationDTO informationDTO) {
        return success(new ResponseDTO(), this.informationService.updateOccupancyCertificateInformation(informationDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/occupancy-certification")
    public ResponseDTO deleteOccupancyCertificateInformation(@RequestParam final Long id) {
        this.informationService.deleteOccupancyCertificateInformation(id);
        return success(new ResponseDTO(), ApplicationMessageStore.SUCCESS);
    }
}
