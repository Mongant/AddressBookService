package com.mongant.advice;

import com.mongant.exceptions.WrongParameterException;
import com.mongant.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongParameterException.class)
    public ApiError wrongRequestParameter(WrongParameterException ex) {
        return error(ex, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiError wrongRequestParameter(Exception ex) {
        return error(ex, ex.getMessage());
    }

    private ApiError error(Exception ex, String message) {
        ApiError apiError = new ApiError();
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            apiError.setNameFilter(request.getParameter("nameFilter"));
            apiError.setMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apiError;
    }
}
