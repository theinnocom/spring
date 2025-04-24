package com.inTrack.spring.dto.responseDTO;

import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.GasMixtureType;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.ETOAgencyInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ETOResDTO {

    private Long etoId;
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
    private String deviceType;
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
    private Long management;
    private Long buildingId;
    private Long status;
    private JobFilingInformation jobFilingInformation;
    private List<ETOAgencyInfo> etoAgencyInfo;
}
