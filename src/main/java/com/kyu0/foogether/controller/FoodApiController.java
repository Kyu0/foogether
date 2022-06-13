package com.kyu0.foogether.controller;

import java.util.NoSuchElementException;

import com.kyu0.foogether.model.Food;
import com.kyu0.foogether.model.Restaurant;
import com.kyu0.foogether.service.FoodService;
import com.kyu0.foogether.service.RestaurantService;
import com.kyu0.foogether.utility.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import lombok.*;

@RestController
public class FoodApiController {

    private FoodService foodService;
    private RestaurantService restaurantService;

    @Autowired
    public FoodApiController(FoodService foodService, RestaurantService restaurantService) {
        this.foodService = foodService;
        this.restaurantService = restaurantService;
    }
    
    @PostMapping("/api/v1/food")
    public ApiResult<?> save(@RequestBody FoodSaveRequest request) {
        request.setRestaurant(restaurantService.findById(request.getRestaurantId()).orElseThrow(() -> new NoSuchElementException("해당 ID를 가진 가게가 없습니다.")));

        return ApiUtils.success(foodService.save(request.toEntity()));
    }

    // DTO 선언

    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class FoodSaveRequest {
        private Integer id;
        private String name;
        private Integer price;
        private String description;
        private boolean isSoldout = true;
        private Integer restaurantId;
        private Restaurant restaurant;
        
        public FoodSaveRequest(Integer id, String name, Integer price, String description, Integer restaurantId) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.description = description;
            this.restaurantId = restaurantId;
        }

        public Food toEntity() {
            return Food.builder()
                        .id(id)
                        .name(name)
                        .price(price)
                        .description(description)
                        .isSoldout(isSoldout)
                        .restaurant(restaurant)
                    .build();
        }
    } 
}
