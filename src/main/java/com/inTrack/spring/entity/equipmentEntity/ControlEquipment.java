package com.inTrack.spring.entity.equipmentEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Table(name = "control_equipment")
@Accessors(chain = true)
public class ControlEquipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "control_equipment_id")
    private Long controlEquipmentId;

    @Column(name = "type")
    private String type;
}
