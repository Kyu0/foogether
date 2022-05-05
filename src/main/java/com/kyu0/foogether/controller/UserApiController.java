package com.kyu0.foogether.controller;

import com.kyu0.foogether.model.User;
import com.kyu0.foogether.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {
    private static final Logger logger = LoggerFactory.getLogger(UserApiController.class);

    private UserService userService;

    @Autowired
    public UserApiController (UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public void save(@RequestBody User user) {
        logger.info("user save");

        userService.save(user);

        logger.info("{}", userService.findById(user.getId()));
    }

    @PostMapping("/login")
    public Boolean login(@RequestBody User user) {
        logger.info("login");

        return userService.findByIdAndPassword(user.getId(), user.getPassword()).isPresent();
    }
}
    