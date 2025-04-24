package com.inTrack.spring.dto;

import com.inTrack.spring.entity.HazardousWaste;
import com.inTrack.spring.entity.TypeOfWaste;
import com.inTrack.spring.entity.WasteUnit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WasteDTO {
    private Long id;
    private String waste;
    private Double wasteVolumeInGallons;
    private Double wasteDensity;
    private Double wasteQuantityInLbs;
    private String nameOfWaste;
    private TypeOfWaste typeOfWaste;
    private WasteUnit wasteUnit;
    private Long hazardousWasteId;
}
