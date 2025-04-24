package com.inTrack.spring.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StackAgencyInfoDTO {

    private Long id;
    private String method9TestConducted; // Assuming values are "Yes", "No", "Not Required"
    private Long lastTestDate;
    private Long nextTestDate;
    private String opacityPermissibleLimit; // Assuming values are "Yes", "No"
    private String testedBy;
    private String note;
    private Long stackAgency;
}
