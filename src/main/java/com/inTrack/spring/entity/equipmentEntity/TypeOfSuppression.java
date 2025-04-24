package com.inTrack.spring.entity.equipmentEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@Table(name = "type_of_suppression")
@Accessors(chain = true)
public class TypeOfSuppression {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "suppression_type_id")
    private Long suppressionTypeId;

    @Column(name = "type")
    private String type;
}
