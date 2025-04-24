package com.inTrack.spring.controller;

import com.inTrack.spring.dto.requestDTO.BuildingReqDTO;
import com.inTrack.spring.dto.requestDTO.CommonListReqDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.BuildingService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BuildingController extends BaseController {

    @Autowired
    private BuildingService buildingService;

    @PostMapping(value = "/building")
    public ResponseDTO createBuilding(@RequestBody final BuildingReqDTO buildingReqDTO) {
        final ResponseDTO responseDTO = new ResponseDTO();
        return super.success(responseDTO, this.buildingService.createBuilding(buildingReqDTO, null), ApplicationMessageStore.BUILDING_CREATE_SUCCESS);
    }

    @GetMapping(value = "/building")
    public ResponseDTO getBuilding(@RequestParam(required = false) final Long buildingId, @RequestParam final Long facilityId) {
        final ResponseDTO responseDTO = new ResponseDTO();
        return super.success(responseDTO, this.buildingService.getBuildings(buildingId, facilityId), ApplicationMessageStore.BUILDING_FETCH_SUCCESS);
    }

    @PutMapping(value = "/building")
    public ResponseDTO updateBuilding(@RequestBody final BuildingReqDTO buildingReqDTO, @RequestParam final Long buildingId) {
        final ResponseDTO responseDTO = new ResponseDTO();
        return super.success(responseDTO, this.buildingService.updateBuilding(buildingReqDTO, buildingId), ApplicationMessageStore.BUILDING_UPDATE_SUCCESS);
    }

    @DeleteMapping(value = "/building")
    public ResponseDTO deleteBuilding(@RequestParam final Long buildingId) {
        final ResponseDTO responseDTO = new ResponseDTO();
        this.buildingService.deleteBuilding(buildingId);
        return super.success(responseDTO, null, ApplicationMessageStore.BUILDING_REMOVE_SUCCESS);
    }
}
