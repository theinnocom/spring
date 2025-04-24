package com.inTrack.spring.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoolingTowerAgencyInfoDTO {

    private Long id;
    private Long lastCertificationDate;
    private Long nextCertificationDate;
    private String certifiedBy;
    private Long lastInspectionDate;
    private Long nextInspectionDate;
    private String inspectedBy;
    private Long lastTestDate;
    private Long nextTestDate;
    private String testedBy;
    private String note;
    private Long coolingTowerAgency;
}
