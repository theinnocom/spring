package com.inTrack.spring.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inTrack.spring.dto.common.ResponseDTO;
import com.inTrack.spring.store.ApplicationMessageStore;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author vijayan
 */

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        final Boolean isExpired = (Boolean) request.getAttribute("isExpired");
        final ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(HttpServletResponse.SC_UNAUTHORIZED);
        responseDTO.setStatus(ApplicationMessageStore.ERROR);
        response.setContentType("application/json");
        if (isExpired != null && isExpired) {
            responseDTO.setMessage(ApplicationMessageStore.ACCESS_DENIED_DUE_TO_EXPIRED_TOKEN);
        } else {
            responseDTO.setMessage(ApplicationMessageStore.ACCESS_DENIED);
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getOutputStream().println(OBJECT_MAPPER.writeValueAsString(responseDTO));
    }
}
