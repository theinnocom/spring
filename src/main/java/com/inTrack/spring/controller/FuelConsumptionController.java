package com.inTrack.spring.controller;

import com.inTrack.spring.dto.requestDTO.FuelConsumptionReqDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.FuelConsumptionService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vijayan
 */

@RestController
public class FuelConsumptionController extends BaseController {

    @Autowired
    private FuelConsumptionService fuelConsumptionService;

    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/fuel-consumption")
    public ResponseDTO createFuelConsumption(@RequestBody final FuelConsumptionReqDTO fuelConsumptionReqDTO) {
        return super.success(responseDTO, this.fuelConsumptionService.createFuelConsumption(fuelConsumptionReqDTO, null), ApplicationMessageStore.FUEL_CONSUMPTION_CREATE_SUCCESS);
    }

    @GetMapping(value = "/fuel-consumption")
    public ResponseDTO getFuelConsumption(@RequestParam(required = false) final Long id, @RequestParam final Long facilityId, @RequestParam(required = false) final Long year) {
        return super.success(responseDTO, this.fuelConsumptionService.getFuelConsumption(id, facilityId, year), ApplicationMessageStore.FUEL_CONSUMPTION_FETCH_SUCCESS);
    }

    @PutMapping(value = "/fuel-consumption/{id}")
    public ResponseDTO updateFuelConsumption(@RequestBody final FuelConsumptionReqDTO fuelConsumptionReqDTO, @PathVariable final Long id) {
        return super.success(responseDTO, this.fuelConsumptionService.updateFuelConsumption(fuelConsumptionReqDTO, id), ApplicationMessageStore.FUEL_CONSUMPTION_UPDATE_SUCCESS);
    }

    @DeleteMapping(value = "/fuel-consumption/{id}")
    public ResponseDTO deleteFuelConsumption(@PathVariable final Long id) {
        this.fuelConsumptionService.deleteFuelConsumption(id);
        return super.success(responseDTO, ApplicationMessageStore.FUEL_CONSUMPTION_DELETE_SUCCESS);
    }

    @GetMapping(value = "/fuel-type")
    public ResponseDTO getFuelType() {
        return super.success(responseDTO, this.fuelConsumptionService.getFuelType(), ApplicationMessageStore.FUEL_TYPE_FETCH_SUCCESS);
    }
}
