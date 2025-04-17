package com.inTrack.spring.controller;



import com.inTrack.spring.dto.common.ResponseDTO;
import com.inTrack.spring.store.ApplicationMessageStore;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author vijayan
 */
public class BaseController {

    public ResponseDTO success(final ResponseDTO responseDTO, final Object response, final String message) {
        responseDTO.setCode(HttpServletResponse.SC_OK);
        responseDTO.setStatus(ApplicationMessageStore.SUCCESS);
        responseDTO.setMessage(message);
        responseDTO.setResponse(response);
        responseDTO.setExecutionTime();
        return responseDTO;
    }

    public ResponseDTO success(final ResponseDTO responseDTO, final String message) {
        responseDTO.setCode(HttpServletResponse.SC_OK);
        responseDTO.setStatus(ApplicationMessageStore.SUCCESS);
        responseDTO.setMessage(message);
        responseDTO.setExecutionTime();
        return responseDTO;
    }
}
