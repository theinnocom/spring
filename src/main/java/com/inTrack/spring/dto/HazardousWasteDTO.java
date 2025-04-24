package com.inTrack.spring.dto;

import com.inTrack.spring.entity.WasteGenerationCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HazardousWasteDTO {
    private Long id;
    private Long manifestNumber;
    private String generatorId;
    private String dateOfManifest;
    private Double totalWasteInLbs;
    private String contactPerson;
    private String phoneNumber;
    private String companyNameCollectingWaste;
    private String wasteTransportingCompanyId;
    private String finalDestinationFacilityId;
    private String note;
    private WasteGenerationCategory wasteGenerationCategory;
    private Long facilityId;
    private WasteDTO wasteDTO;
}
