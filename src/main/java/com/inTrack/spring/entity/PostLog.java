package com.inTrack.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "post_log")
@Getter
@Setter
public class PostLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "employee_no")
    private String employeeNo;

    @Column(name = "employee_name", nullable = false)
    private String employeeName;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "date_of_injury_or_illness")
    private Long dateOfInjuryOrIllness;

    @Column(name = "incident_time")
    private String incidentTime;

    @Column(name = "incident_location")
    private String incidentLocation;

    @Column(name = "injury_or_illness_description")
    private String injuryOrIllnessDescription;

    @Column(name = "harmful_object_or_substance")
    private String harmfulObjectOrSubstance;

    @Column(name = "activity_before_incident")
    private String activityBeforeIncident;

    @Column(name = "previous_illness_or_injury_description")
    private String previousIllnessOrInjuryDescription;

    @Column(name = "health_care_professional")
    private String healthCareProfessional;

    @Column(name = "injury_or_illness_category")
    private String injuryOrIllnessCategory;

    @Column(name = "days_away_from_work")
    private int daysAwayFromWork;

    @Column(name = "description")
    private String description;

    @Column(name = "annual_log_inspected")
    private Boolean annualLogInspected;

    @Column(name = "last_inspection_date")
    private Long lastInspectionDate;

    @Column(name = "next_inspection_date")
    private Long nextInspectionDate;

    @Column(name = "inspected_by")
    private String inspectedBy;

    @Column(name = "note")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", referencedColumnName = "facility_id")
    private Facility facility;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;
}

