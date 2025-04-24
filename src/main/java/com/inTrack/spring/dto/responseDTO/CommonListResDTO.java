package com.inTrack.spring.dto.responseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonListResDTO {

    private Object data;
    private int pageNumber;
    private int totalPages;
    private int itemsPerPage;
    private Long totalItem;
    private int currentPage;
    private Boolean hasMorePage;
    private int lastPage;
    private int perPage;
    private Long total;
}
