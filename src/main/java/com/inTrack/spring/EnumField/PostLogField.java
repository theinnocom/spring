package com.inTrack.spring.EnumField;

import lombok.Getter;

@Getter
public enum PostLogField {
    FACILITY_ID("facilityId"),
    FACILITY("facility");

    private final String columnName;

    PostLogField(String columnName) {
        this.columnName = columnName;
    }
}
