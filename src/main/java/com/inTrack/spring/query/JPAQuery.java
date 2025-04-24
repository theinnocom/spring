package com.inTrack.spring.query;

import org.springframework.stereotype.Component;

@Component
public class JPAQuery {
    public static final String GET_EQUIPMENT_USING_BUILDING_ID = "select e from Equipment e where (case when ?1 <> 0 then e.building.buildingId = ?1 else true end) and e.equipmentCount <> 0";
}
