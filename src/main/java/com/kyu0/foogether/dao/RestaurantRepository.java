package com.kyu0.foogether.dao;

import java.util.Optional;

import com.kyu0.foogether.model.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    Optional<Restaurant> findByUserId(String userId);
    
}
