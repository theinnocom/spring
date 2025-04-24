package com.inTrack.spring.dto.responseDTO;

import com.inTrack.spring.entity.Address;
import com.inTrack.spring.entity.FacilityOwnerDetail;
import com.inTrack.spring.entity.PermitType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacilityResDTO {

    private Long facilityId;
    private String facilityNo;
    private Long numberOfBuildings;
    private String facilityName;
    private String facilityCategory;
    private String contactPerson;
    private String contactTitle;
    private String addressLine;
    private String alterAddressLine;
    private String city;
    private String state;
    private String zipcode;
    private String facilityEmail;
    private String primaryPhone;
    private String alterPhone;
    private String faxNo;
    private String website;
    private FacilityOwnerDetail facilityOwnerDetail;
    private Long createdAt;
    private Long createdBy;
}
