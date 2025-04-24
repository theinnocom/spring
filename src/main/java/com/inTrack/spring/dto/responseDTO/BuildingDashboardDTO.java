package com.inTrack.spring.dto.responseDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BuildingDashboardDTO {
    private Long totalViolationCount;
    private List<ViolationCountDTO> violationCountDTO;
}
