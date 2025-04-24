package com.inTrack.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "waste")
public class Waste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "waste")
    private String waste;

    @Column(name = "waste_volume_gallons")
    private Double wasteVolumeInGallons;

    @Column(name = "waste_density")
    private Double wasteDensity;

    @Column(name = "waste_quantity_lbs")
    private Double wasteQuantityInLbs;

    @Column(name = "name_of_waste")
    private String nameOfWaste;

    @ManyToOne
    @JoinColumn(name = "type_of_waste_id")
    private TypeOfWaste typeOfWaste;

    @ManyToOne
    @JoinColumn(name = "waste_unit_id")
    private WasteUnit wasteUnit;

    @ManyToOne
    @JoinColumn(name = "hazardous_waste_id")
    private HazardousWaste hazardousWaste;
}
