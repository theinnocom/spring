package com.inTrack.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "educational_training")
public class EducationTraining {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "training_title")
    private String trainingTitle;

    @Column(name = "regulatory_authority")
    private String regulatoryAuthority;

    @Column(name = "training_officer_name")
    private String trainingOfficerName;

    @Column(name = "last_training_date")
    private Long lastTrainingDate;

    @Column(name = "description")
    private String description;

    @Column(name = "training_frequency")
    private String trainingFrequency;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;
}
