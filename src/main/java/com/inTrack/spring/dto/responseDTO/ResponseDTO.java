package com.inTrack.spring.dto.responseDTO;

import lombok.Data;

import java.util.List;

@Data
public class ResponseDTO {

    private int code;
    private String status;
    private String message;
    private List<String> validationErrors;
    private Long requestedTime;
    private String executionTime;
    private Object response;
    private String detailedMessage;

    public ResponseDTO() {
        this.requestedTime = System.currentTimeMillis();
    }

    public void setExecutionTime() {
        this.executionTime = System.currentTimeMillis() - this.requestedTime + " ms";
    }

}
