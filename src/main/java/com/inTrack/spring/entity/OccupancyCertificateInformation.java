package com.inTrack.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "occupancy_certificate_information")
public class OccupancyCertificateInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "occupancy_number")
    private String occupancyNumber;

    @Column(name = "number_of_stories")
    private Integer numberOfStories;

    @Column(name = "height_in_feet")
    private Double heightInFeet;

    @Column(name = "construction_classification")
    private String constructionClassification;

    @Column(name = "group_classification")
    private String groupClassification;

    @Column(name = "protection_equipment")
    private String protectionEquipment;

    @Column(name = "building_type")
    private String buildingType;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;
}
