package com.inTrack.spring.dto.requestDTO;

import com.inTrack.spring.dto.responseDTO.BoilerCogenAgencyInfoDTO;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.HydraulicStorageTankDropDown.HydraulicStorageTankUsage;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HydraulicStorageTankReqDTO {

    private Long id;
    private String uniqueId;
    private Long status;
    private Long floor;
    private String location;
    private String landmark;
    private Long installedOn;
    private String installedBy;
    private String managedBy;
    private String managementNote;
    private Long disconnectedOn;
    private Long usage; // Dropdown: Hydraulic oil, Lube oil, Transformer oil, Waste Water, Used oil, other oil
    private boolean secondaryContainment; // yes, no
    private boolean agencyApprovalRequired; // yes, no
    private String tankNumber; // Search button (Storage tank)
    private boolean spillKitAvailable; // yes, no
    private Long management;
    private String comments;
    private Long buildingId;
    private JobFilingInformationReqDTO jobFilingInformationReqDTO;
    private List<BoilerCogenAgencyInfoDTO> agencyInfoList;
}
