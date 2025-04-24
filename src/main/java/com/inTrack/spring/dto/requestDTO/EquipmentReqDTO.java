package com.inTrack.spring.dto.requestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EquipmentReqDTO {

    private Long floor;
    private String location;
    private boolean activeStatus;
    private String makeBy;
    private String serialNo;
    private Long installedOn;
    private String installedBy;
    private String managedBy;
    private Long buildingId;
    private JobFilingInformationReqDTO jobFilingInformationReqDTO;
}
