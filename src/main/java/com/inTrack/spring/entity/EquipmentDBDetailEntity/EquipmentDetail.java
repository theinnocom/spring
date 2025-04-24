package com.inTrack.spring.entity.EquipmentDBDetailEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "equipment_detail")
public class EquipmentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "column_name")
    private String columnName;

    @Column(name = "column_type")
    private String columnType;

    @Column(name = "equipment_name")
    private String equipmentName;
}
