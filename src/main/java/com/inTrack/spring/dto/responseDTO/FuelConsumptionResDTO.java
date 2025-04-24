package com.inTrack.spring.dto.responseDTO;

import com.inTrack.spring.entity.FuelType;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FuelConsumptionResDTO {

    private Long id;
    private Long year;
    private FuelConsumptionMonthResDTO fuelConsumptionMonthResDTO;
    private FuelType fuelType;
    private Long facilityId;
    private Double annualFuelConsumption;
}
