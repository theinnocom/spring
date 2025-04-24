package com.inTrack.spring.dto.requestDTO;

import com.inTrack.spring.dto.responseDTO.BoilerCogenAgencyInfoDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FireAlarmDTO {

    private Long id;
    private String uniqueId;
    private Long floor;
    private String location;
    private boolean activeStatus = true;
    private String makeBy;
    private String serialNo;
    private Long installedOn;
    private String installedBy;
    private String managedBy;
    private Long buildingId;
    private Long batteryLocation;
    private String transientSuppressors;
    private String controlUnitTroubleSignals;
    private String remoteAnnunciations;
    private String initiatingDevices;
    private String guardsTourEquipment;
    private String interfaceEquipment;
    private String alarmNotificationAppliances;
    private String supervisingStationFireAlarmSystem;
    private String emergencyCommunicationsEquipment;
    private String specialProcedures;
    private Long lastInspectionDate;
    private Long nextInspectionDate;
    private String model;
    private String capacity;
    private String fireAlarmType;
    private Long fiberOpticConnection;
    private Long controlEquipment;
    private Long batteryType;
    private Long management;
    private String landmark;
    private String deviceType;
    private Long disconnectedOn;
    private String managementNote;
    private Long status;
    private String note;
    private JobFilingInformationReqDTO jobFilingInformationReqDTO;
    private List<BoilerCogenAgencyInfoDTO> agencyInfoList;
}
