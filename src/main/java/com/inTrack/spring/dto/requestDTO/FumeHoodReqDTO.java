package com.inTrack.spring.dto.requestDTO;

import com.inTrack.spring.dto.responseDTO.BoilerCogenAgencyInfoDTO;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.FumeHoodChemicalDetail;
import com.inTrack.spring.entity.equipmentEntity.Stack;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FumeHoodReqDTO {

    private String uniqueId;
    private Long floor;
    private String location;
    private boolean activeStatus;
    private String makeBy;
    private String serialNo;
    private Long installedOn;
    private String installedBy;
    private String managedBy;
    private Long buildingId;
    private Long status;
    private Long stack;
    private String type;
    private Double HoursOfOperation = 0D;
    private Long management;
    private List<FumeHoodChemicalDetail> fumeHoodChemicalDetails;
    private JobFilingInformationReqDTO jobFilingInformationReqDTO;
    private List<BoilerCogenAgencyInfoDTO> agencyInfoList;
}
