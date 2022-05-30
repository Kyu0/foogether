package com.kyu0.foogether.controller;

import com.kyu0.foogether.dto.user.UserDto;
import com.kyu0.foogether.service.UserService;
import com.kyu0.foogether.utility.api.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ApiResult<?> save(@RequestBody UserDto userDto) {
        logger.info("received params : {}", userDto);
        
        try {
            return ApiUtils.success(userService.save(userDto));
        }
        catch (IllegalArgumentException e) {
            return ApiUtils.error(String.format("전달 받은 파라미터가 잘못되었습니다. {0}", e.getMessage()), HttpStatus.BAD_REQUEST.value());
        }
    }
}