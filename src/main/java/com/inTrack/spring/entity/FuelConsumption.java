package com.inTrack.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "fuel_consumption")
public class FuelConsumption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "year", nullable = false)
    private Long year;

    @Column(name = "january", nullable = false)
    private Double january;

    @Column(name = "february", nullable = false)
    private Double february;

    @Column(name = "march", nullable = false)
    private Double march;

    @Column(name = "april", nullable = false)
    private Double april;

    @Column(name = "may", nullable = false)
    private Double may;

    @Column(name = "june", nullable = false)
    private Double june;

    @Column(name = "july", nullable = false)
    private Double july;

    @Column(name = "august", nullable = false)
    private Double august;

    @Column(name = "september", nullable = false)
    private Double september;

    @Column(name = "october", nullable = false)
    private Double october;

    @Column(name = "november", nullable = false)
    private Double november;

    @Column(name = "december", nullable = false)
    private Double december;

    @Column(name = "annual_fuel_consumption")
    private Double annualFuelConsumption;

    @ManyToOne
    @JoinColumn(name = "fuel_type_id")
    private FuelType fuelType;

    @ManyToOne
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;
}
