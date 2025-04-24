package com.inTrack.spring.entity.equipmentEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Table(name = "type_of_chiller")
@Accessors(chain = true)
public class TypeOfChiller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chiller_type_id")
    private Long chillerTypeId;

    @Column(name = "type")
    private String type;
}
