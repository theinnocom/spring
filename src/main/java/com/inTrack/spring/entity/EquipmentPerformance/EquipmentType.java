package com.inTrack.spring.entity.EquipmentPerformance;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Table(name = "equipment_type")
@Entity
@Accessors(chain = true)
public class EquipmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_type_id", nullable = false)
    private Long equipmentTypeId;

    @Column(name = "type")
    private String type;
}
