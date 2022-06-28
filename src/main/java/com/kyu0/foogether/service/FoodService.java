package com.kyu0.foogether.service;

import com.kyu0.foogether.dao.FoodRepository;
import com.kyu0.foogether.model.Food;

import java.util.NoSuchElementException;
import java.util.Optional;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public Optional<Food> findById(Integer id) {
        return foodRepository.findById(id);
    }

    @Transactional
    public void delete(Integer id) throws NoSuchElementException {
        foodRepository.delete(findById(id).orElseThrow(() -> new NoSuchElementException("해당 음식을 삭제할 수 없습니다.")));
    }
}
