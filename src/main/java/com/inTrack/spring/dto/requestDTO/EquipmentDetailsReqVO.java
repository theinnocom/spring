package com.inTrack.spring.dto.requestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EquipmentDetailsReqVO {

    private Long floor;
    private Long buildingId;
    private String equipmentType;
}
