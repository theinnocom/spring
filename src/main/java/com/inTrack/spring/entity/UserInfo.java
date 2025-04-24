package com.inTrack.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author vijayan
 */

@Getter
@Setter
@Entity
@Table(name = "user_info")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_info_id", unique = true, nullable = false)
    private Long userInfoId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mobile_number", length = 20)
    private String mobileNumber;

    @Column(name = "alternate_mobile_number", length = 20)
    private String alternateMobileNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "country_name")
    private String country;

    @Column(name = "city_name")
    private String city;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
