package com.inTrack.spring.UtilService;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationUtil {


    public static final Pageable pageableWithoutShorting(final int pageNumber, final int itemsPerPage) {
        Pageable pageable;
        if (pageNumber == 0 && itemsPerPage == 0) {
            pageable = PageRequest.of(0, 10);
        } else if (pageNumber == 0) {
            pageable = PageRequest.of(0, itemsPerPage);
        } else if (itemsPerPage == 0) {
            pageable = PageRequest.of(pageNumber - 1, 10);
        } else {
            pageable = PageRequest.of(pageNumber - 1, itemsPerPage);
        }
        return pageable;
    }

    public static final Pageable pageableWithShorting(final int pageNumber, final int itemsPerPage, final String sortingField, final String sortingDirection) {
        Sort.Direction direction;
        if (sortingDirection.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }
        final Sort sort = Sort.by(direction, sortingField);
        return PageRequest.of(pageNumber, itemsPerPage, sort);
    }

}
