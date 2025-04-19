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
@Table(name = "facility")
public class Facility {

    @Id
    @Column(name = "facility_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facilityId;

    @Column(name = "facility_no")
    private String facilityNo;

    @Column(name = "facility_name")
    private String facilityName;

    @Column(name = "facility_category")
    private String facilityCategory;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "contact_title")
    private String contactTitle;

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

    @Column(name = "facility_email")
    private String facilityEmail;

    @Column(name = "primary_phone")
    private String primaryPhone;

    @Column(name = "alter_phone")
    private String alterPhone;

    @Column(name = "fax_no")
    private String faxNo;

    @Column(name = "website")
    private String website;

    @Column(name = "active")
    private Boolean active = true;

    @Column(name = "number_of_buildings")
    private Long numberOfBuildings;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @ManyToOne
    @JoinColumn(name = "facility_owner_id")
    private FacilityOwnerDetail facilityOwnerDetail;
}
