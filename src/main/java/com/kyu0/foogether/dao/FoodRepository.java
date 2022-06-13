package com.kyu0.foogether.dao;

import com.kyu0.foogether.model.Food;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Integer> {
    
}
