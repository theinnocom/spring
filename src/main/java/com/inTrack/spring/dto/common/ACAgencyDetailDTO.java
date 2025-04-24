package com.inTrack.spring.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ACAgencyDetailDTO {

    private Long id;
    private String agencyId;
    private Long fdnyInspectionDate;
    private String fdnyInspectedBy;
    private String fdnyNote;
    private Boolean eupCardAvailable;
    private Long eupInspectionDate;
    private Long eupNextInspectionDate;
    private String eupNote;
    private Long epaSubmittedDate;
    private Boolean isEPARefrigerationRecoveryRegistered;
    private Long epaApprovalDate;
    private String epaNote;
}

