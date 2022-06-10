package com.kyu0.foogether.service;

import java.util.Optional;

import com.kyu0.foogether.dao.RestaurantRepository;
import com.kyu0.foogether.model.Restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestaurantService {

    private RestaurantRepository restaurantRepository;

    @Autowired
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
}
