package com.inTrack.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "place_of_assembly")
public class PlaceOfAssembly {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "permit_number", nullable = false)
    private String permitNumber;

    @Column(name = "capacity")
    private Double capacity;

    @Column(name = "permit_obtained_date")
    private Long permitObtainedDate;

    @Column(name = "note", length = 500)
    private String note;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private PlaceOfAssemblyType type;
}
