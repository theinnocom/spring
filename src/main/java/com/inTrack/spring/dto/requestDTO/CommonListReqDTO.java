package com.inTrack.spring.dto.requestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonListReqDTO {
    private int pageNumber = 1;
    private int itemsPerPage = 10;
    private String search;
    private String sortingDirection;
}
