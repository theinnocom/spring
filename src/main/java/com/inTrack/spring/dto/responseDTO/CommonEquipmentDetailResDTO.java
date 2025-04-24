package com.inTrack.spring.dto.responseDTO;

import com.inTrack.spring.entity.Building;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommonEquipmentDetailResDTO {

    private String equipmentType;
    private Long equipmentCount;
    private Long buildingId;
    private Long equipmentId;
}
