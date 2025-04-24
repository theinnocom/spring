package com.inTrack.spring.entity.equipmentEntity.IncineratorCrematoriesDropDown;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Entity
@Table(name = "waste_type_burner")
@Accessors(chain = true)
public class WasteTypeBurner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;
}
