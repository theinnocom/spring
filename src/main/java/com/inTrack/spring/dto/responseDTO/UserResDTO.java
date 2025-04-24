package com.inTrack.spring.dto.responseDTO;

import com.inTrack.spring.dto.UserFacilityRelationDTO;
import com.inTrack.spring.dto.requestDTO.CertificateOfFitnessReqDTO;
import com.inTrack.spring.dto.requestDTO.UserRoleReqDTO;
import com.inTrack.spring.entity.UserMailPermission;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResDTO extends TokenResponseDTO {

    private Long userId;
    private String role;
    private String email;
    private Boolean isProfileActive;
    private String password;
    private String loginId;
    private boolean deletePermission;
    private String title;
    private String firstName;
    private String lastName;
    private Long userRole;
    private String country;
    private String countryCode;
    private String city;
    private String zipcode;
    private Long expiryDate;
    private String alternateMail;
    private boolean updatePermission;
    private String userType;
    private boolean mustChangePassword;
    private List<UserFacilityRelationDTO> userFacilityRelations;
    private UserMailPermission userMailPermission;
    private Long subscriptionId;
    private String registration;
    private Boolean proposal;
    private String mobileNumber;
    private String alternateMobileNumber;
    private String designation;
    private String address;
}
