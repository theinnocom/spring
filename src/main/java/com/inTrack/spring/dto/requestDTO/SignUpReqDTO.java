package com.inTrack.spring.dto.requestDTO;

import com.inTrack.spring.dto.UserFacilityRelationDTO;
import com.inTrack.spring.entity.Subscription;
import com.inTrack.spring.entity.UserFacilityRelation;
import com.inTrack.spring.entity.UserMailPermission;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SignUpReqDTO {

    private String email;
    private String password;
    private String loginId;
    private boolean deletePermission;
    private String title;
    private String firstName;
    private String lastName;
    private Long userRole;
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
    private List<CertificateOfFitnessReqDTO> certificateOfFitnessReqDTOS;
    private List<Long> facilityIds;
    private List<UserFacilityRelationDTO> userFacilityRelations;
    private UserMailPermission userMailPermission;
    private Long subscriptionId;
    private String registration;
    private Boolean proposal;
    private String mobileNumber;
    private String alternateMobileNumber;
    private String designation;
}
