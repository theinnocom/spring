package com.inTrack.spring.dto.responseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificateOfFitnessResDTO {

    private Long equipmentId;
    private String name;
    private String certificateNumber;
    private String phoneNumber;
    private Long issuedDate;
    private Long expiryDate;
    private String type;
    private String description;
    private String specialNote;
    private String comment;
    private Boolean isActive;
    private Long facilityId;
    private String note;
    private Long userId;
}
