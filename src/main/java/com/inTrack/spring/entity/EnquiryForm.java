package com.inTrack.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author vijayan
 */

@Entity
@Getter
@Setter
@Table(name = "enquire_form")
public class EnquiryForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enquiry_id")
    private Long enquiryId;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "organization")
    private String organization;

    @Column(name = "designation")
    private String designation;

    @Column(name = "title")
    private String title;

    @Column(name = "complied_with")
    private String compliedWith;

    @Column(name = "is_profile_active", nullable = false)
    private Boolean isProfileActive = false;

    @Column(name = "mobile_number", length = 20)
    private String mobileNumber;

    @Column(name = "alternate_mobile_number", length = 20)
    private String alternateMobileNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "city_name")
    private String city;

    @Column(name = "zipcode")
    private String zipcode;

    @OneToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;
}
