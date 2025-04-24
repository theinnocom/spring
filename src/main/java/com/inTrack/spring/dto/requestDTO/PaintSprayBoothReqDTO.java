package com.inTrack.spring.dto.requestDTO;

import com.inTrack.spring.dto.responseDTO.BoilerCogenAgencyInfoDTO;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.Stack;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaintSprayBoothReqDTO {

    private String uniqueId;
    private Long floor;
    private String location;
    private boolean activeStatus;
    private String applicationId;
    private String make;
    private String managementNote;
    private String makeBy;
    private String serialNo;
    private Long installedOn;
    private String installedBy;
    private String managedBy;
    private Long buildingId;
    private String model;
    private String serialNumber;
    private Long stackExhausting;
    private int hoursOfOperationPerDay;
    private int daysOfOperationPerWeek;
    private double totalVocContentPerMonth;
    private double totalAnnualVocContent;
    private boolean capOnVocContent;
    private Double paintLbPerGal;
    private Double solventLbPerGal;
    private Double inkLbPerGal;
    private Double monthlyLimitGalPerMonth;
    private String comments;
    private Long status;
    private Long management;
    private JobFilingInformationReqDTO jobFilingInformationReqDTO;
    private List<BoilerCogenAgencyInfoDTO> agencyInfoList;
}
