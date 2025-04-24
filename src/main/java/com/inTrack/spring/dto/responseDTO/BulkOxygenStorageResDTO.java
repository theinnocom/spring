package com.inTrack.spring.dto.responseDTO;

import com.inTrack.spring.dto.common.BulkOxygenStorageAgencyInfoDTO;
import com.inTrack.spring.entity.JobFilingInformation;
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
public class BulkOxygenStorageResDTO {

    private Long id;
    private String uniqueId;
    private Long floor;
    private Boolean isActive;
    private String location;
    private String landmark;
    private String makeBy;
    private String serialNo;
    private Long installedOn;
    private String installedBy;
    private String managedBy;
    private String deviceType;
    private JobFilingInformation jobFilingInformation;
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
    private Long status;
    private String applicationId;
    private List<BulkOxygenStorageAgencyInfoDTO> agencyInfoList;
    private String model;
}
