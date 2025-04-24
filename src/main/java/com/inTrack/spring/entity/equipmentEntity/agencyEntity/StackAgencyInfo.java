package com.inTrack.spring.entity.equipmentEntity.agencyEntity;

import com.inTrack.spring.entity.equipmentEntity.Stack;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.StackAgency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "stack_agency_info")
public class StackAgencyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "method_9_test_conducted")
    private String method9TestConducted; // Assuming values are "Yes", "No", "Not Required"

    @Column(name = "last_test_date")
    private Long lastTestDate;

    @Column(name = "next_test_date")
    private Long nextTestDate;

    @Column(name = "opacity_permissible_limit")
    private String opacityPermissibleLimit; // Assuming values are "Yes", "No"

    @Column(name = "tested_by")
    private String testedBy;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "stack_id")
    private Stack stack;

    @ManyToOne
    @JoinColumn(name = "stack_agency_id")
    private StackAgency stackAgency;
}
