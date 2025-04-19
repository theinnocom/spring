package com.inTrack.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "building")
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_id", nullable = false, unique = true)
    private Long buildingId;

    @Column(name = "building_name")
    private String buildingName;

    @Column(name = "bin_no", unique = true)
    private String binNo;

    @Column(name = "block_number")
    private Long blockNumber;

    @Column(name = "lot_number")
    private Long lotNumber;

    @Column(name = "no_of_stories", nullable = false)
    private Long noOfStories;

    @Column(name = "height_in_feet")
    private Long heightInFeet;

    @Column(name = "certificate_availability")
    private String certificateAvailability;

    @Column(name = "certificate_number", unique = true)
    private String certificateNumber;

    @Column(name = "occupancy_classification")
    private String occupancyClassification;

    @Column(name = "construction_classification")
    private String constructionClassification;

    @Column(name = "notes")
    private String notes;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "address_line")
    private String addressLine;

    @Column(name = "alter_address_line")
    private String alterAddressLine;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zipcode")
    private String zipcode;

    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;
}
