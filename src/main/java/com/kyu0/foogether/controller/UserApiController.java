package com.kyu0.foogether.controller;

import javax.persistence.RollbackException;
import javax.validation.Valid;

import com.kyu0.foogether.dto.user.UserDto;
import com.kyu0.foogether.service.UserService;
import com.kyu0.foogether.utility.api.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserApiController {
    private static final Logger logger = LoggerFactory.getLogger(UserApiController.class);

    private UserService userService;

    @Autowired
    public UserApiController (UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/v1/user")
    public ApiResult<?> save(@Valid @RequestBody UserDto userDto) {
        logger.info("received params : {}", userDto);
    
        return ApiUtils.success(userService.save(userDto));
    }
}