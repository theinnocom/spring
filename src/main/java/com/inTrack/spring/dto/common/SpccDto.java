package com.inTrack.spring.dto.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpccDto {
    private Long id;
    private String pbsNumber;
    private Long pbsExpiration;
    private Double cumulativeAst;
    private Double cumulativeUst;
    private Boolean spccPlanRequired;
    private Long lastPlanDate;
    private Long nextPlanDate;
    private Boolean spccTrainingRequired;
    private Long lastTrainingDate;
    private Long nextTrainingDate;
    private Boolean spccAuditRequired;
    private Integer der40CertifiedStaffCount;
    private String jobNumber;
    private String note;
    private Long auditCycle;
    private Long facility;
    private List<DERStaffCertificationDTO> derStaffCertificationDTOS;
}
