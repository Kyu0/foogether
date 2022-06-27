package com.kyu0.foogether.dao;

import com.kyu0.foogether.model.Restaurant;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @EntityGraph("Restaurant.withAll")
    Optional<Restaurant> findById(Integer id);
}
