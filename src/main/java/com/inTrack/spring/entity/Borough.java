package com.inTrack.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "borough")
public class Borough {

    @Id
    @Column(name = "borough_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boroughId;

    @Column(name = "borough_name")
    private String boroughName;
}
