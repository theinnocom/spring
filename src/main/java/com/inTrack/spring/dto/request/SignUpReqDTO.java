package com.inTrack.spring.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpReqDTO {

    private String email;
    private String password;
    private String userName;
    private boolean deletePermission;
    private String title;
    private String firstName;
    private String lastName;
    private UserRoleReqDTO userRole;
    private boolean isProfileActive = true;
    private String country;
    private String countryCode;
    private String city;
    private String zipcode;
    private Long expiryDate;
    private String alternateMail;
    private boolean updatePermission;
    private byte userType;
    private boolean mustChangePassword;
}
