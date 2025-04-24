package com.inTrack.spring.controller;

import com.inTrack.spring.dto.common.PlaceOfAssemblyDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.PlaceOfAssemblyService;
import com.inTrack.spring.store.ApplicationMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PlaceOfAssemblyController extends BaseController {

    private final PlaceOfAssemblyService placeOfAssemblyService;

    @PostMapping(value = "/place-of-assembly")
    public ResponseDTO createPlaceOfAssembly(@RequestBody final PlaceOfAssemblyDTO placeOfAssemblyDTO) {
        return success(new ResponseDTO(), this.placeOfAssemblyService.createPlaceOfAssembly(placeOfAssemblyDTO), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/place-of-assembly")
    public ResponseDTO getPlaceOfAssembly(@RequestParam final Long id) {
        return success(new ResponseDTO(), this.placeOfAssemblyService.getPlaceOfAssembly(id), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/place-of-assembly/building")
    public ResponseDTO getAllPlaceOfAssembly(@RequestParam final Long buildingId) {
        return success(new ResponseDTO(), this.placeOfAssemblyService.getAllPlaceOfAssembly(buildingId), ApplicationMessageStore.SUCCESS);
    }

    @PutMapping(value = "/place-of-assembly")
    public ResponseDTO updatePlaceOfAssembly(@RequestBody final PlaceOfAssemblyDTO placeOfAssemblyDTO) {
        return success(new ResponseDTO(), this.placeOfAssemblyService.updatePlaceOfAssembly(placeOfAssemblyDTO), ApplicationMessageStore.SUCCESS);
    }

    @DeleteMapping(value = "/place-of-assembly")
    public ResponseDTO deletePlaceOfAssembly(@RequestParam final Long id) {
        this.placeOfAssemblyService.deletePlaceOfAssembly(id);
        return success(new ResponseDTO(), ApplicationMessageStore.SUCCESS);
    }

    @GetMapping(value = "/place-of-assembly-type")
    public ResponseDTO getPlaceOfAssemblyType() {
        return success(new ResponseDTO(), this.placeOfAssemblyService.getAllPlaceOfAssemblyTypes(), ApplicationMessageStore.SUCCESS);
    }
}
