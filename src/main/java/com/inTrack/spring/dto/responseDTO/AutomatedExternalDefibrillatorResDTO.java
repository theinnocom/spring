package com.inTrack.spring.dto.responseDTO;

import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.AutomatedExternalDefibrillatorAgencyInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutomatedExternalDefibrillatorResDTO {

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
    private ManagementType management;
    private String model;
    private String capacity;
    private Long edBatteryDate; // Expires 5 years from the date
    private Long defibPadExpirationDate;
    private String inspection; // Monthly, Quarterly
    private Long nextInspectionDate;
    private Long batteryAndCabinetAlarmChangeDate;
    private String landmark;
    private String deviceType;
    private Long disconnectedOn;
    private String managementNote;
    private JobFilingInformationReqDTO jobFilingInformationReqDTO;
    private ElevatorStatus status;
    private AutomatedExternalDefibrillatorAgencyInfo agencyInfo;
}
