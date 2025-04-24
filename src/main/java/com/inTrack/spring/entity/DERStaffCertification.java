package com.inTrack.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "der_staff_certification")
public class DERStaffCertification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "staff_name")
    private String staffName;

    @Column(name = "issue_date")
    private Long issueDate;

    @Column(name = "authorization_number")
    private String authorizationNumber;

    @Column(name = "certification_available")
    private Boolean certificationAvailable; // true for Yes, false for No

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "certificate_type_id")
    private CertificateType certificateType;

    @ManyToOne
    @JoinColumn(name = "spcc_id")
    private Spcc spcc;
}
