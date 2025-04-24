package com.inTrack.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "work_permit")
public class WorkPermit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "permit_number", nullable = false)
    private String permitNumber;

    @Column(name = "work_type")
    private String workType;

    @Column(name = "job_type")
    private String jobType;

    @Column(name = "description")
    private String description;

    @Column(name = "filing_date")
    private Long filingDate;

    @Column(name = "job_start_date")
    private Long jobStartDate;

    @Column(name = "expiry_date")
    private Long expiryDate;

    @Column(name = "status")
    private String status;

    @Column(name = "issued_to")
    private String issuedTo;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "job_no", referencedColumnName = "job_filing_id")
    private JobFilingInformation jobFilingInformation;

    @ManyToOne
    @JoinColumn(name = "building_id", referencedColumnName = "building_id")
    private Building building;
}
