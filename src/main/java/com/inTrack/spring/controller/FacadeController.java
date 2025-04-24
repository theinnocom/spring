package com.inTrack.spring.controller;

import com.inTrack.spring.dto.requestDTO.FacadeReqDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.FacadeService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Vijayan
 */

@RestController
public class FacadeController extends BaseController {

    @Autowired
    private FacadeService facadeService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/facade")
    public ResponseDTO createFacade(@RequestBody final FacadeReqDTO facadeReqDTO) {
        this.facadeService.createFacade(facadeReqDTO, null);
        return super.success(responseDTO, ApplicationMessageStore.FACADE_CREATE_SUCCESS);
    }

    @GetMapping(value = "/facade")
    public ResponseDTO getFacade(@RequestParam(required = false) final Long facadeId, @RequestParam(required = false) final Long buildingId) {
        return super.success(responseDTO, this.facadeService.getFacade(facadeId, buildingId), ApplicationMessageStore.FACADE_FETCH_SUCCESS);
    }

    @PutMapping(value = "/facade/{facadeId}")
    public ResponseDTO updateFacade(@RequestBody final FacadeReqDTO facadeReqDTO, @PathVariable final Long facadeId) {
        return super.success(responseDTO, this.facadeService.updateFacade(facadeId, facadeReqDTO), ApplicationMessageStore.FACADE_UPDATE_SUCCESS);
    }

    @DeleteMapping(value = "/facade")
    public ResponseDTO deleteFacade(@RequestParam final Long facadeId) {
        this.facadeService.deleteFacade(facadeId);
        return super.success(responseDTO, ApplicationMessageStore.FACADE_DELETE_SUCCESS);
    }
}
