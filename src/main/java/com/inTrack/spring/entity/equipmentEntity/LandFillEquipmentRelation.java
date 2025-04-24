package com.inTrack.spring.entity.equipmentEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "land_fill_equipment_relation")
public class LandFillEquipmentRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "equipment_type")
    private String equipmentType;

    @Column(name = "equipment_id")
    private Long equipmentId;

    @ManyToOne
    @JoinColumn(name = "land_fill_id", referencedColumnName = "land_fill_id")
    private LandFill landFill;
}
