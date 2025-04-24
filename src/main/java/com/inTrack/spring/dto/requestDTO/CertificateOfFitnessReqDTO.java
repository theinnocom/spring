package com.inTrack.spring.dto.requestDTO;

import com.inTrack.spring.entity.Building;
import com.inTrack.spring.entity.EquipmentPerformance.EquipmentType;
import com.inTrack.spring.entity.Facility;
import com.inTrack.spring.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificateOfFitnessReqDTO {

    private String certificateNumber;
    private String name;
    private Long issuedDate;
    private Long expiryDate;
    private String type;
    private String description;
    private Boolean isActive;
    private String phoneNumber;
    private String note;
    private String specialNote;
    private Long facility;
    private Long equipment;
    private Long userId;
}
