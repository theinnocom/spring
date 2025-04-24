package com.inTrack.spring.entity.EquipmentPerformance;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "equipment_data")
public class EquipmentData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_data_id", nullable = false)
    private Long equipmentDataId;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JoinColumn(name = "equipment_field")
    private EquipmentField equipmentField;

    @ManyToOne
    @JoinColumn(name = "equipment_master_id")
    private EquipmentMaster equipmentMaster;
}
