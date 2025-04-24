package com.inTrack.spring.dto.requestDTO;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BuildingReqDTO {

    private String buildingName;
    private Long lotNumber;
    private String binNo;
    private Long blockNumber;
    private Long noOfStories;
    private AddressReqDTO address;
    private String city;
    private Byte certificateAvailability;
    private String certificateNumber;
    private String occupancyClassification;
    private String notes;
    private boolean isActive = true;
    private Long boroughId;
    private Long facilityId;
    private String addressLine;
    private String alterAddressLine;
    private String state;
    private String zipcode;
}
