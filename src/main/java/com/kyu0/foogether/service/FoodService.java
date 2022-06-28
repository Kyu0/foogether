package com.kyu0.foogether.service;

import com.kyu0.foogether.dao.FoodRepository;
import com.kyu0.foogether.model.Food;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
public class FoodService {
    
    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Transactional
    public Food save(Food request) {
        return foodRepository.save(request);
    }
}
