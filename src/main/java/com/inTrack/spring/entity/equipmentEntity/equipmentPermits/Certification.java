package com.inTrack.spring.entity.equipmentEntity.equipmentPermits;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "certification")
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certification_id")
    private Long certificationId;

    @Column(name = "certification_name")
    private String certificationName;

    @Column(name = "created_at")
    private Long createdAt;
}
