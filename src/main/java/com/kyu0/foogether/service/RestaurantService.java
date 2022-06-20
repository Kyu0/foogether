package com.kyu0.foogether.service;

import java.util.*;

import com.kyu0.foogether.dao.RestaurantRepository;
import com.kyu0.foogether.model.Food;
import com.kyu0.foogether.model.Restaurant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Transactional(readOnly = true)
    public Optional<Restaurant> findById(Integer id) {
        return restaurantRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Food> findFoods(Integer restaurantId) {
        // 해당 ID를 가진 가게가 없을 경우 빈 리스트를 반환한다.
        return findById(restaurantId).map(restaurant -> restaurant.getFoods()).orElse(new ArrayList<>());
    }
}
