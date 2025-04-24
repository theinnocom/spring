package com.inTrack.spring.entity.equipmentEntity.equipmentPermits;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "inspection")
public class Inspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inspection_id")
    private Long inspectionId;

    @Column(name = "inspection_name")
    private String inspectionName;

    @Column(name = "created_at")
    private Long createdAt;
}
