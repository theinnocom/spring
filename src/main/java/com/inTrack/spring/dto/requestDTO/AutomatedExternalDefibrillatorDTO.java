package com.inTrack.spring.dto.requestDTO;

import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.AutomatedExternalDefibrillatorAgencyInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AutomatedExternalDefibrillatorDTO {

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
    private Long management;
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
    private JobFilingInformation jobFilingInformation;
    private Long status;
    private List<AutomatedExternalDefibrillatorAgencyInfo> agencyInfo;
}
