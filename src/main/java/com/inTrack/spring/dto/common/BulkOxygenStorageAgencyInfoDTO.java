package com.inTrack.spring.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BulkOxygenStorageAgencyInfoDTO {

    private Long id;
    private String permitId;
    private Long issueDate;
    private Long expirationDate;
    private String note;
    private String fireDepartmentPermitObtained;
    private String testPerformed;
    private Long lastTestDate;
    private Long nextTestDate;
    private Long bulkOxygenStorageAgency;
}
