package com.inTrack.spring.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AgencyDTO {

    private Long id;
    private String type;

    public AgencyDTO(Long id, String type) {
        this.id = id;
        this.type = type;
    }
}
