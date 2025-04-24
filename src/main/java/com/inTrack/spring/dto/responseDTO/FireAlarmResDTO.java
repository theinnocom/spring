package com.inTrack.spring.dto.responseDTO;

import com.inTrack.spring.dto.requestDTO.JobFilingInformationReqDTO;
import com.inTrack.spring.entity.equipmentEntity.BatteryType;
import com.inTrack.spring.entity.equipmentEntity.ControlEquipment;
import com.inTrack.spring.entity.equipmentEntity.FiberOpticConnection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FireAlarmResDTO {

    private Long fireAlarmId;
    private Long floor;
    private String location;
    private boolean activeStatus;
    private String makeBy;
    private String serialNo;
    private Long installedOn;
    private String installedBy;
    private String managedBy;
    private Long buildingId;
    private String batteryLocation;
    private String transientSuppressors;
    private String ControlUnitTroubleSignals;
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
    private FiberOpticConnection fiberOpticConnection;
    private ControlEquipment controlEquipment;
    private BatteryType batteryType;
    private JobFilingInformationReqDTO jobFilingInformationReqDTO;
}
