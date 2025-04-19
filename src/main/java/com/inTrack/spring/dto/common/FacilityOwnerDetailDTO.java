package com.inTrack.spring.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacilityOwnerDetailDTO {

    private Long id;
    private String ownerName;
    private String addressLine;
    private String alterAddressLine;
    private String city;
    private String state;
    private String zipcode;
    private String email;
    private String mobilePhone;
    private String alterMobilePhone;
    private String faxNo;
}
