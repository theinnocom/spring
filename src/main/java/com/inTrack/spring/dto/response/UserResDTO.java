package com.inTrack.spring.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResDTO extends TokenResponseDTO {

    private Long userId;
    private String email;
    private String userName;
    private Boolean deletePermission;
    private Boolean isProfileActive;
    private String role;
    private Long expiryDate;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String countryCode;
    private String zipcode;
}
