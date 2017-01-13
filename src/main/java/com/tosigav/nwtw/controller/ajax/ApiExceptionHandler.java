package com.tosigav.nwtw.controller.ajax;

import com.tosigav.nwtw.exception.DataDuplicateException;
import com.tosigav.nwtw.exception.DataNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author ronny susetyo  <ronny at susetyo.com>
 * @since 23 Mar 2015
 */
@ControllerAdvice
public class ApiExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(DataDuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public Map dataDuplicateException(DataDuplicateException e) {
        Map result = new LinkedHashMap();
        result.put("success", false);
        result.put("msg", e.getMessage());
        return result;
    }
    
    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Map dataNotFoundException(DataNotFoundException e) {
        Map result = new LinkedHashMap();
        result.put("success", false);
        result.put("msg", e.getMessage());
        return result;
    }
    
    @ExceptionHandler(UnsupportedOperationException.class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @ResponseBody
    public Map  exceptionUnsupportedOperationException(UnsupportedOperationException e) {
        Map result = new LinkedHashMap();
        result.put("success", false);
        result.put("msg", e.getMessage());
        return result;
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map exceptionHandler(Exception e) {
        LOGGER.error("Internal server error", e);
        Map result = new LinkedHashMap();
        result.put("success", false);
        result.put("msg", e.getMessage());
        return result;
    }
    
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map unrecognizedPropertyException(HttpMessageNotReadableException e) {
        Map result = new LinkedHashMap();
        result.put("success", false);
        result.put("msg", e.getMessage());
        return result;
    }
    
    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Map exceptionEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        Map result = new LinkedHashMap();
        result.put("success", false);
        result.put("msg", e.getMessage());
        return result;
    }
    
    @ExceptionHandler(AuthorizationServiceException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Map exceptioAuthorizationServiceException(AuthorizationServiceException e) {
        Map result = new LinkedHashMap();
        result.put("success", false);
        result.put("msg", e.getMessage());
        return result;
    }

//    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "[No such Order,telo]")  // 404
//    public class OrderNotFoundException extends RuntimeException {
//        // ...
//    }
}
