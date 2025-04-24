package com.inTrack.spring.dto.requestDTO;

import com.inTrack.spring.dto.common.BulkOxygenStorageAgencyInfoDTO;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.ElevatorStatus;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.BulkOxygenStorageAgencyInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BulkOxygenStorageReqDTO {

    private String uniqueId;
    private Long floor;
    private Boolean isActive;
    private String location;
    private String makeBy;
    private String serialNo;
    private Long installedOn;
    private String installedBy;
    private String managedBy;
    private String deviceType;
    private JobFilingInformationReqDTO jobFilingInformationReqDTO;
    private Long disconnectedOn;
    private String capacity;
    private String fireDepartmentApprovalObtained;
    private String fireDepartmentApprovalNumber;
    private boolean pressureTestPerformed;
    private Long lastTestDate;
    private Long nextTestDate;
    private String note;
    private String eesJobNumber;
    private String comments;
    private Long management;
    private String managementNote;
    private Long buildingId;
    private String applicationId;
    private Long status;
    private List<BulkOxygenStorageAgencyInfoDTO> agencyInfoList;
    private String model;
}
