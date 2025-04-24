package com.inTrack.spring.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ElevatorStatusResDTO {

    private Long statusId;
    private String statusName;
}
