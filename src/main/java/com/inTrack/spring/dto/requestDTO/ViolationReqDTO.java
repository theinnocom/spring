package com.inTrack.spring.dto.requestDTO;

import com.inTrack.spring.entity.CertificateOfCorrection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViolationReqDTO {

    private Long issuingAgency;
    private String violationNo;
    private Long issuedDate;
    private Long hearingDate;
    private String respondentName;
    private String code;
    private String codeSection;
    private String description;
    private String penaltyImposed;
    private Double paidAmount;
    private Double balanceDue;
    private Long hearingStatus;
    private String hearingResult;
    private Long complianceDate;
    private String note;
    private Long buildingId;
    private Boolean hearingAttended = false;
    private Boolean finePaid = false;
    private Boolean waived = false;
    private Boolean dismissed = false;
    private String statusComments;
    private CertificateOfCorrection certificateOfCorrection;
}
