package com.inTrack.spring.entity.equipmentEntity.HydraulicStorageTankDropDown;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Table(name = "hydraulic_storage_tank_usage")
@Accessors(chain = true)
public class HydraulicStorageTankUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;
}
