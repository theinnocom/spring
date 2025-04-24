package com.inTrack.spring.dto.responseDTO;

import com.inTrack.spring.entity.Building;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EquipmentFloorDetailResDTO {

    private Long floor;
    private List<FloorDetailResDTO> floorDetailResDTOS;
}
