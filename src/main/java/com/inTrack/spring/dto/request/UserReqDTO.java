package com.inTrack.spring.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserReqDTO {

    private Long userId;
    private String firstName;
    private String lastName;
    private UserRoleReqDTO userRole;
    private boolean deletePermission;
    private boolean isProfileActive;
    private String country;
    private String countryCode;
    private String city;
    private String zipcode;
    private Long expiryDate;
    private String alternateMail;
    private boolean updatePermission;
    private byte userType;
}
