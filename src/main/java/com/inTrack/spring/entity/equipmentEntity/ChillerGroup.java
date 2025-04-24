package com.inTrack.spring.entity.equipmentEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Table(name = "chiller_group")
@Accessors(chain = true)
public class ChillerGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chiller_group_id")
    private Long chillerGroupId;

    @Column(name = "group_name")
    private String groupName;
}
