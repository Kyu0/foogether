package com.kyu0.foogether.controller;

import java.util.Optional;

import com.kyu0.foogether.model.Restaurant;
import com.kyu0.foogether.service.RestaurantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestaurantApiController {
    
    private RestaurantService restaurantService;

    @Autowired
    public RestaurantApiController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/api/v1/restaurant")
    public Restaurant save(Restaurant restaurant) {
        return restaurantService.save(restaurant);
    }

    @GetMapping("/api/v1/restaurant/{id}")
    public Optional<Restaurant> findById(@PathVariable(name = "id") Integer id) {
        return restaurantService.findById(id);
    }
}
