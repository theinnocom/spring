package com.inTrack.spring.dto.responseDTO;

import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.TypeOfRpz;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BoilerCogenAgencyInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RPZResDTO {

    private Long rpzId;
    private Long floor;
    private String location;
    private boolean activeStatus;
    private String make;
    private String serialNo;
    private Long installedOn;
    private String installedBy;
    private String managedBy;
    private Long buildingId;
    private JobFilingInformation jobFilingInformationReqDTO;
    private String uniqueId;
    private String deviceType;
    private Long management;
    private String landmark;
    private Long disconnectedOn;
    private String managementNote;
    private String model;
    private String capacity;
    private String size;
    private String meterReading;
    private String waterReadingNumber;
    private Double linePressure;
    private String typeOfService;
    private Long typeOfRpz;
    private Long status;
    private String comments;
    private List<BoilerCogenAgencyInfoDTO> agencyInfoList;
}
