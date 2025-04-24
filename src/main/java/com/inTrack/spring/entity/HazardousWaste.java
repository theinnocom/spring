package com.inTrack.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "hazardous_waste")
public class HazardousWaste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "manifest_number", nullable = false)
    private Long manifestNumber;

    @Column(name = "generator_id", nullable = false)
    private String generatorId;

    @Column(name = "date_of_manifest")
    private String dateOfManifest;

    @Column(name = "total_waste_lbs")
    private Double totalWasteInLbs;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "company_name_collecting_waste")
    private String companyNameCollectingWaste;

    @Column(name = "waste_transporting_company_id")
    private String wasteTransportingCompanyId;

    @Column(name = "final_destination_facility_id")
    private String finalDestinationFacilityId;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "waste_generation_category_id")
    private WasteGenerationCategory wasteGenerationCategory;

    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;
}
