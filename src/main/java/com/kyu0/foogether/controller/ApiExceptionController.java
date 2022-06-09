package com.kyu0.foogether.controller;

import java.util.NoSuchElementException;

import javax.persistence.EntityExistsException;
import javax.validation.ConstraintViolationException;

import com.kyu0.foogether.utility.api.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// TODO : 정리하기
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResult<?> enumNotParsableExceptionHandle(HttpMessageNotReadableException e) {
        return ApiUtils.error("role 컬럼에는 ['OWNER', 'ADMIN', 'RIDER', 'CUSTOMER'] 만 입력해주세요.", HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(EntityExistsException.class)
    public ApiResult<?> entityExistsExceptionHandle(EntityExistsException e) {
        return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ApiResult<?> notFindExceptionHandle(NoSuchElementException e) {
        return ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }
}
