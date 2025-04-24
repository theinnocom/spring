package com.inTrack.spring.entity.equipmentEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "fume_hood_chemical_detail")
public class FumeHoodChemicalDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "chemical_name")
    private String chemicalName;

    @Column(name = "volume")
    private double volume;

    @Column(name = "density")
    private double density;

    @Column(name = "voc_percentage")
    private double vocPercentage;

    @Column(name = "voc")
    private double voc;

    @ManyToOne
    @JoinColumn(name = "fume_hood_id")
    private FumeHood fumeHood;
}
