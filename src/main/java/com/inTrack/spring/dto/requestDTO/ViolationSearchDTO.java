package com.inTrack.spring.dto.requestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViolationSearchDTO extends CommonListReqDTO {

    private Long from;
    private Long to;
    private Long issuingAgency = 0L;
    private Long hearingStatus = 0L;
}
