package com.inTrack.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "permit_type")
public class PermitType {

    @Id
    @Column(name = "permit_type_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permitTypeId;

    @Column(name = "type_name")
    private String typeName;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;
}
