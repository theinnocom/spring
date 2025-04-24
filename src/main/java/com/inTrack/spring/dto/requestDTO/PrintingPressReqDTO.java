package com.inTrack.spring.dto.requestDTO;

import com.inTrack.spring.dto.responseDTO.BoilerCogenAgencyInfoDTO;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PrintingPressReqDTO {

    private String uniqueId;
    private Long floor;
    private String location;
    private boolean activeStatus;
    private String make;
    private String serialNo;
    private Long installedOn;
    private String installedBy;
    private String managedBy;
    private Long buildingId;
    private double totalVOC;
    private String stackExhaustSearchButton;
    private double stackExhaustHeight;
    private double stackExhaustDiameter;
    private double stackExhaustVelocity;
    private String filterUsedType;
    private double efficiencyPercentage;
    private boolean inkOrSolventUsageCap;
    private Double permittedMaxVOC;
    private Long status;
    private String applicationId;
    private Long management;
    private Long stack;
    private JobFilingInformationReqDTO jobFilingInformationReqDTO;
    private List<BoilerCogenAgencyInfoDTO> agencyInfoList;
}
