package com.inTrack.spring.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EducationTrainingDTO {

    private Long id;
    private String trainingTitle;
    private String regulatoryAuthority;
    private String trainingOfficerName;
    private Long lastTrainingDate;
    private String description;
    private String trainingFrequency;
    private String note;
    private Long facility;
}
