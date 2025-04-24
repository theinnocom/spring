package com.inTrack.spring.entity.equipmentEntity.CogenTurbineDropDown;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Table(name = "cogen_primary_use")
@Accessors(chain = true)
public class CogenPrimaryUse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type")
    private String type;
}
