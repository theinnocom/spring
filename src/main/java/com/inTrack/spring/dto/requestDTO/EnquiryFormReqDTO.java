package com.inTrack.spring.dto.requestDTO;

import com.inTrack.spring.entity.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnquiryFormReqDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String organization;
    private String designation;
    private String title;
    private String compliedWith;
    private String mobileNumber;
    private String alterMobileNumber;
    private String address;
    private String zipCode;
    private String city;
}
