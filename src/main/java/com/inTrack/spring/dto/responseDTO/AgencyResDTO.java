package com.inTrack.spring.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AgencyResDTO {

    private Long agencyId;
    private String agencyName;
    private String agencyShortName;
    private Long createdAt;
    private Long updatedAt;
}
