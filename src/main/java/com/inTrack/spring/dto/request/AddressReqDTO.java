package com.inTrack.spring.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressReqDTO {

    private String addressDetails;
    private String alternativeAddress;
    private String optionalAddress;
    private String mobileNumber;
    private String faxNumber;
    private String city;
    private String state;
    private String zipcode;
    private String county;
    private Long boroughId;
}
