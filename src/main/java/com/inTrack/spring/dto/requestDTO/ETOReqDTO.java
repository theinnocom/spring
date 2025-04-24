package com.inTrack.spring.dto.requestDTO;

import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.GasMixtureType;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.ETOAgencyInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ETOReqDTO {

    private String uniqueId;
    private Long floor;
    private String location;
    private boolean activeStatus;
    private String make;
    private String serialNo;
    private Long installedOn;
    private String installedBy;
    private String managedBy;
    private String applicationId;
    private String stackExhausting;
    private String model;
    private String serialNumber;
    private double volumeCubicFt;
    private Long gasMixtureType;
    private double weightOfContainer;
    private Long installationDate;
    private double averageUseHoursPerDay;
    private double averageUseDaysPerWeek;
    private Boolean isAbator;
    private String abatorMake;
    private String abatorModel;
    private boolean dailyRecordsAvailable;
    private String comments;
    private Long buildingId;
    private Long management;
    private JobFilingInformationReqDTO jobFilingInformationReqDTO;
    private List<ETOAgencyInfo> etoAgencyInfos;
    private Long status;
}
