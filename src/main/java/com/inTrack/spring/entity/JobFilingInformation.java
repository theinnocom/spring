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
@Table(name = "job_filing_information")
public class JobFilingInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_filing_id")
    private Long jobFilingId;

    @Column(name = "job_number", unique = true)
    private String jobNumber;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private String status;

    @Column(name = "description")
    private String description;

    @Column(name = "approval_date")
    private Long approvalDate;

    @Column(name = "sign_off_date")
    private Long signOffDate;

    @Column(name = "commands")
    private String commands;

    @Column(name = "notes")
    private String notes;

    @Column(name = "equipment_name")
    private String equipmentName;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;
}
