package com.inTrack.spring.dto.responseDTO;

import com.inTrack.spring.entity.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnquiryFormResDTO {

    private Long enquiryId;
    private String firstName;
    private String lastName;
    private String email;
    private String organization;
    private String title;
    private String compliedWith;
    private Boolean isProfileActive;
    private String mobileNumber;
    private String alterMobileNumber;
    private String address;
    private String zipCode;
    private String city;
    private String designation;

}
