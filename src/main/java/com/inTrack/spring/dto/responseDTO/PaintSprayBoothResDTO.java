package com.inTrack.spring.dto.responseDTO;

import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaintSprayBoothResDTO {

    private Long id;
    private String uniqueId;
    private Long floor;
    private String location;
    private boolean activeStatus;
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
    private String stackExhausting;
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
    private ElevatorStatus status;
    private JobFilingInformationReqDTO jobFilingInformationReqDTO;
}
