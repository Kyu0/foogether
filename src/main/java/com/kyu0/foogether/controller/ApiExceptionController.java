package com.kyu0.foogether.controller;

import javax.validation.ConstraintViolationException;

import com.kyu0.foogether.utility.api.ApiResult;
import com.kyu0.foogether.utility.api.ApiUtils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionController {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult<?> validationExceptionHandle(MethodArgumentNotValidException e) {
        return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResult<?> columnNotValidExceptionHandle(ConstraintViolationException e) {
        return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }
}
