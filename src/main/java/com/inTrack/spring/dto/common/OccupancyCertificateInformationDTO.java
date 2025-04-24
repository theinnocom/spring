package com.inTrack.spring.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OccupancyCertificateInformationDTO {

    private Long id;
    private String occupancyNumber;
    private Integer numberOfStories;
    private Double heightInFeet;
    private String constructionClassification;
    private String groupClassification;
    private String protectionEquipment;
    private String buildingType;
    private String note;
    private Long building;
}
