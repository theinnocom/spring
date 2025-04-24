package com.inTrack.spring.dto.requestDTO;

import com.inTrack.spring.dto.common.FacilityOwnerDetailDTO;
import com.inTrack.spring.entity.FacilityOwnerDetail;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacilityReqDTO {

    private Long id;
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
    private FacilityOwnerDetailDTO facilityOwnerDetailDTO;
}
