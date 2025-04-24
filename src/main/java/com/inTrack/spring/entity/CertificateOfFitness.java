package com.inTrack.spring.entity;

import com.inTrack.spring.entity.EquipmentPerformance.EquipmentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "certificate_of_fitness")
public class CertificateOfFitness {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificate_id")
    private Long certificateId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "certificate_number", unique = true)
    private String certificateNumber;

    @Column(name = "issued_date")
    private Long issuedDate;

    @Column(name = "expiry_date")
    private Long expiryDate;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
