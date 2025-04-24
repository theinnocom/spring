package com.inTrack.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {

    @Id
    @Column(name = "address_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @Column(name = "address_details")
    private String addressDetails;

    @Column(name = "alternative_address")
    private String alternativeAddress;

    @Column(name = "optional_address")
    private String optionalAddress;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "fax_number")
    private String faxNumber;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "county")
    private String county;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @ManyToOne
    @JoinColumn(name = "borough_id")
    private Borough borough;
}
