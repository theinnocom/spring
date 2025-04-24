package com.inTrack.spring.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneratorAgencyInfoDTO {

    private Long id;
    private String agencyNumber;
    private Long issueDate;
    private Long expirationDate;
    private String status;
    private Long submittedDate;
    private String note;
    private Boolean qualifiesForDRProgram;
    private Long stackLastTestDate;
    private Long stackNextTestDate;
    private String noxLevel;
    private String unitType;
    private String testConductedBy;
    private Boolean otherGeneratorCombined;
    private String combinedGenerator;
    private Boolean protocolSubmittedToDEC;
    private Boolean qualifiesForDOE;
    private Boolean registeredWithDOE;
    private Boolean renewedWithDOE;
    private Long generator;
    private Long generatorAgency;
}

