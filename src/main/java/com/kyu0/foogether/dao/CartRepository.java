package com.kyu0.foogether.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kyu0.foogether.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    
}
