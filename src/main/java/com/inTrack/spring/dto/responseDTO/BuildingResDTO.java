package com.inTrack.spring.dto.responseDTO;

import com.inTrack.spring.entity.Address;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BuildingResDTO {

    private Long buildingId;
    private String buildingName;
    private String binNo;
    private Long lotNumber;
    private Long blockNumber;
    private Long noOfStories;
    private Address address;
    private String city;
    private Byte certificateAvailability;
    private String certificateNumber;
    private String occupancyClassification;
    private String notes;
    private Long facilityId;
    private String addressLine;
    private String alterAddressLine;
    private String state;
    private String zipcode;
}
