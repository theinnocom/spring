package com.inTrack.spring.dto;

import com.inTrack.spring.entity.EquipmentDBDetailEntity.EquipmentDetail;
import com.inTrack.spring.entity.EquipmentDBDetailEntity.GeneralEquipment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class EquipmentColumnDetail {

    private List<GeneralEquipment> generalEquipments;
    private List<EquipmentDetail> equipmentDetails;
    private List<Map<String, String>> agencyDetails;
}
