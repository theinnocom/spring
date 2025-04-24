package com.inTrack.spring.dto.requestDTO;

import com.inTrack.spring.dto.common.ACAgencyDetailDTO;
import com.inTrack.spring.entity.JobFilingInformation;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.ChillerGroup;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.TypeOfChiller;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.ACAgencyDetail;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ACUnitDTO {

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
    private String model;
    private String capacity;
    private Long fdnyInspectionDate;
    private String eupcardAvailable;
    private Long inspectionDate;
    private String inspector;
    private boolean refrigerationRecoveryInvolved;
    private boolean epaRefrigerationRecoveryInvolved;
    private boolean epaRecordMaintained;
    private Long epaSubmittedDate;
    private Long epaApprovalDate;
    private Long chillerGroup;
    private Long management;
    private String landmark;
    private String deviceType;
    private Long disconnectedOn;
    private String managementNote;
    private String typeOfChillerOthersList;
    private Long typeOfChiller;
    private Long status;
    private JobFilingInformation jobFilingInformation;
    private List<ACAgencyDetailDTO> acAgencyDetails;
}
