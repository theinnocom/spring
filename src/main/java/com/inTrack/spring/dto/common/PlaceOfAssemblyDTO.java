package com.inTrack.spring.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceOfAssemblyDTO {

    private Long id;
    private String permitNumber;
    private Double capacity;
    private Long permitObtainedDate;
    private String note;
    private Long building;
    private Long type;
}
