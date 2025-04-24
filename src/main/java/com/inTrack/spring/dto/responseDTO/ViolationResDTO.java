package com.inTrack.spring.dto.responseDTO;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.CertificateOfCorrection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViolationResDTO {

    private Long violationId;
    private String violationNo;
    private Long issuingAgency;
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
    private Boolean hearingAttended;
    private Boolean finePaid;
    private Boolean waived;
    private Boolean dismissed;
    private String statusComments;
    private CertificateOfCorrection certificateOfCorrection;
    private Building building;
    private Long createdAt;
    private Long updatedAt;
}
