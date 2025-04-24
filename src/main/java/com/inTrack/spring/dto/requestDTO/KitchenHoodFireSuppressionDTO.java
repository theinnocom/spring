package com.inTrack.spring.dto.requestDTO;

import com.inTrack.spring.dto.responseDTO.BoilerCogenAgencyInfoDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KitchenHoodFireSuppressionDTO {

    private Long id;
    private Long floor;
    private String location;
    private boolean activeStatus;
    private String make;
    private String serialNo;
    private Long installedOn;
    private String installedBy;
    private String managedBy;
    private Long buildingId;
    private JobFilingInformationReqDTO jobFilingInformationReqDTO;
    private String uniqueId;
    private Long management;
    private String landmark;
    private Long disconnectedOn;
    private String managementNote;
    private String model;
    private String capacity;
    private String deviceType;
    private boolean isPortableFireExtinguisher;
    private String internalManagement;
    private String regulatoryAuthority;
    private String permitNumber;
    private Long permitDate;
    private String isInspectionTagAttached;
    private String inspectionTestFrequency;
    private Long typeOfSuppression;
    private Long status;
    private List<BoilerCogenAgencyInfoDTO> agencyInfoList;
}
