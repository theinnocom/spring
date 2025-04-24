package com.inTrack.spring.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.*;

/**
 * Filter to allow Cross-Origin requests (e.g. by front-end). Without the filter
 * the requests are blocked.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@CrossOrigin(
        allowCredentials = "true",
        origins = "*",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.OPTIONS}
)
public class SimpleCORSFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleCORSFilter.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final List<String> KEYS_TO_BE_EXCLUDED_IN_LOGS = Arrays.asList("password", "videoFile", "imageFile", "certificationFile", "resumeFile", "photo", "handoutFile", "image", "video", "resume");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final String requestId = UUID.randomUUID().toString();
        final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        final HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.setHeader("X-Requested-With", "XMLHttpRequest");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "x-auth-token, x-requested-with, Access-Control-Allow-Origin, Access-Control-Allow-Credentials, Origin, Accept, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, token, authorization, offset, isDST, isAdmin, portal");
        if (httpServletRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        } else if (servletRequest instanceof HttpServletRequest && servletRequest instanceof HttpServletResponse) {
            final ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(httpServletRequest);
            final ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(httpServletResponse);
            try {
                filterChain.doFilter(requestWrapper, responseWrapper);
            } finally {
                this.performRequestAudit(requestId, requestWrapper);
                responseWrapper.copyBodyToResponse();
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    public void destroy() {
    }

    private void performRequestAudit(final String requestId, final ContentCachingRequestWrapper requestWrapper) {
        String offset = "Unknown";
        String qString = "Unknown";
        String reqPath = "Unknown";
        String activityId = "Unknown";
        if (requestWrapper != null) {
            activityId = (String) requestWrapper.getAttribute("activityId");
            offset = requestWrapper.getHeader("offset") == null ? "N/A" : requestWrapper.getHeader("offset");
            final Map<String, String[]> paramMap = requestWrapper.getParameterMap();
            final Map<String, String[]> tempParamMap = new LinkedHashMap<>();
            if (paramMap != null && !paramMap.isEmpty()) {
                tempParamMap.putAll(paramMap);
                for (final String s : KEYS_TO_BE_EXCLUDED_IN_LOGS) {
                    tempParamMap.remove(s);
                }
                try {
                    qString = OBJECT_MAPPER.writeValueAsString(tempParamMap);
                } catch (final Exception e) {
                    LOGGER.error("Error fetching the query parameters from the request: " + requestId);
                }
            }
            reqPath = requestWrapper.getServletPath();
        }
        if (requestWrapper != null && requestWrapper.getContentAsByteArray() != null
                && requestWrapper.getContentAsByteArray().length > 0) {
            LOGGER.info("Activity Id :: " + activityId + ", Mode :: Enter, Method type :: " + requestWrapper.getMethod() + ", Path :: " + reqPath + ", Params :: " + qString + ", Timezone offset :: " + offset + ", Request Object :: " + this.getPayLoadFromByteArray(activityId, requestWrapper.getContentAsByteArray(), requestWrapper.getCharacterEncoding(), requestWrapper.getServletPath()));
        } else {
            LOGGER.info("Activity Id :: " + activityId + ", Mode :: Enter, Method type :: " + requestWrapper.getMethod() + ", Path :: " + reqPath + ", Params :: " + qString + ", Timezone offset :: " + offset + ", Request Object :: No request body present");
        }
    }

    private String getPayLoadFromByteArray(final String requestId, final byte[] requestBuffer, final String charEncoding, final String servletPath) {
        String payLoad;
        try {
            payLoad = new String(requestBuffer, charEncoding);
            if (servletPath.equalsIgnoreCase("/oauth/token")) {
                final String[] oauthReqArr = payLoad.split("&");
                final String[] outputArr = Arrays.copyOf(oauthReqArr, oauthReqArr.length - 1);
                payLoad = OBJECT_MAPPER.writeValueAsString(outputArr);
            } else {
                final Map<String, Object> reqObj = OBJECT_MAPPER.readValue(payLoad, Map.class);
                if (reqObj != null) {
                    for (final String s : KEYS_TO_BE_EXCLUDED_IN_LOGS) {
                        reqObj.remove(s);
                    }
                }
                payLoad = OBJECT_MAPPER.writeValueAsString(reqObj);
            }
        } catch (final Exception e) {
            payLoad = "Unknown";
            LOGGER.error("Error fetching body from the request: " + requestId, e);
        }
        return payLoad;
    }

}