package com.kyu0.foogether.service;

import com.kyu0.foogether.dao.FoodRepository;
import com.kyu0.foogether.model.Food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodService {
    
    private FoodRepository foodRepository;

    @Autowired
    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public Food save(Food request) {
        return foodRepository.save(request);
    }
}
