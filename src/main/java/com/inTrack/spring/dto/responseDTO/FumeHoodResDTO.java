package com.inTrack.spring.dto.responseDTO;

import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.FumeHoodChemicalDetail;
import com.inTrack.spring.entity.equipmentEntity.Stack;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FumeHoodResDTO {

    private Long id;
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
    private Double HoursOfOperation;
    private Long management;
    private String deviceType;
    private List<FumeHoodChemicalDetail> fumeHoodChemicalDetails;
    private JobFilingInformation jobFilingInformation;
    private List<BoilerCogenAgencyInfoDTO> agencyInfoList;
}
