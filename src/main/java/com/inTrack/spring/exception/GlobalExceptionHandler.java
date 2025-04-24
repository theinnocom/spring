package com.inTrack.spring.exception;

import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.store.ApplicationMessageStore;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.LinkedList;
import java.util.List;

/**
 * @author vijayan
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BadCredentialsException.class, InternalAuthenticationServiceException.class, SecurityError.class})
    public final ResponseEntity<ResponseDTO> handleException(final AuthenticationException ex, final WebRequest request) {
        final ResponseDTO responseVO = new ResponseDTO();
        responseVO.setCode(HttpServletResponse.SC_UNAUTHORIZED);
        responseVO.setStatus(ApplicationMessageStore.ERROR);
        responseVO.setMessage(ex.getMessage());
        responseVO.setExecutionTime();
        return ResponseEntity.ok(responseVO);
    }

    @ExceptionHandler(ResourceNotFoundError.class)
    public final ResponseEntity<ResponseDTO> handleException(final ResourceNotFoundError ex, final WebRequest request) {
        final ResponseDTO responseVO = new ResponseDTO();
        responseVO.setCode(HttpServletResponse.SC_NOT_FOUND);
        responseVO.setStatus(ApplicationMessageStore.ERROR);
        responseVO.setMessage(ex.getMessage());
        responseVO.setExecutionTime();
        return ResponseEntity.ok(responseVO);
    }

    @ExceptionHandler(ValidationError.class)
    public final ResponseEntity<ResponseDTO> handleException(final ValidationError ex, final WebRequest request) {
        final ResponseDTO responseVO = new ResponseDTO();
        responseVO.setCode(HttpServletResponse.SC_EXPECTATION_FAILED);
        responseVO.setStatus(ApplicationMessageStore.ERROR);
        responseVO.setMessage(ex.getMessage());
        responseVO.setExecutionTime();
        return ResponseEntity.ok(responseVO);
    }

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public final ResponseEntity<ResponseDTO> handleValidationException(final Exception ex, final WebRequest request) throws Exception {
        final ResponseDTO responseVO = new ResponseDTO();
        responseVO.setCode(HttpServletResponse.SC_BAD_REQUEST);
        responseVO.setStatus(ApplicationMessageStore.ERROR);
        responseVO.setMessage(ApplicationMessageStore.BAD_REQUEST);
        final List<String> validationErrors = new LinkedList<>();
        BindingResult bindingResult = null;
        if (ex instanceof BindException) {
            final BindException bindException = (BindException) ex;
            bindingResult = bindException.getBindingResult();
        } else if (ex instanceof MethodArgumentNotValidException) {
            final MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) ex;
            bindingResult = methodArgumentNotValidException.getBindingResult();
        }
        if (bindingResult != null) {
            bindingResult.getAllErrors().forEach(objectError -> validationErrors.add(objectError.getDefaultMessage()));
            responseVO.setValidationErrors(validationErrors);
        }
        responseVO.setExecutionTime();
        return ResponseEntity.ok(responseVO);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<ResponseDTO> handleException(final AccessDeniedException ex, final WebRequest request) {
        final Boolean isTokenExpired = (Boolean) request.getAttribute("isExpired", 0);
        final ResponseDTO responseVO = new ResponseDTO();
        responseVO.setCode(HttpServletResponse.SC_UNAUTHORIZED);
        responseVO.setStatus(ApplicationMessageStore.ERROR);
        responseVO.setMessage(ApplicationMessageStore.ACCESS_DENIED);
        if (isTokenExpired != null && isTokenExpired) {
            responseVO.setMessage(ApplicationMessageStore.ACCESS_DENIED_DUE_TO_EXPIRED_TOKEN);
        }
        responseVO.setExecutionTime();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseVO);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public final ResponseEntity<ResponseDTO> handleException(final MissingServletRequestParameterException ex, final WebRequest request) {
        final ResponseDTO responseVO = new ResponseDTO();
        responseVO.setCode(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        responseVO.setStatus(ApplicationMessageStore.ERROR);
        responseVO.setMessage(ex.getMessage());
        responseVO.setExecutionTime();
        return ResponseEntity.ok(responseVO);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public final ResponseEntity<ResponseDTO> handleException(final HttpRequestMethodNotSupportedException ex, final WebRequest request) {
        final ResponseDTO responseVO = new ResponseDTO();
        responseVO.setCode(HttpServletResponse.SC_EXPECTATION_FAILED);
        responseVO.setStatus(ApplicationMessageStore.ERROR);
        responseVO.setMessage(ex.getMessage());
        responseVO.setExecutionTime();
        return ResponseEntity.ok(responseVO);
    }

    @ExceptionHandler(ApplicationError.class)
    public final ResponseEntity<ResponseDTO> handleException(final ApplicationError ex, final WebRequest request) {
        final ResponseDTO responseVO = new ResponseDTO();
        responseVO.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        responseVO.setStatus(ApplicationMessageStore.ERROR);
        responseVO.setMessage(ApplicationMessageStore.GENERIC_ERROR);
        responseVO.setDetailedMessage(ex.getMessage());
        responseVO.setExecutionTime();
        return ResponseEntity.ok(responseVO);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ResponseDTO> handleException(final Exception ex, final WebRequest request) throws Exception {
        final ResponseDTO responseVO = new ResponseDTO();
        responseVO.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        responseVO.setStatus(ApplicationMessageStore.ERROR);
        responseVO.setMessage(ApplicationMessageStore.GENERIC_ERROR);
        responseVO.setDetailedMessage(ex.getMessage());
        responseVO.setExecutionTime();
        return ResponseEntity.ok(responseVO);
    }

}
