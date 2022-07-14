package com.kyu0.foogether.service;

import org.springframework.stereotype.Service;

import com.kyu0.foogether.dao.CartRepository;
import com.kyu0.foogether.model.Cart;

@Service
public class CartService {
    
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart save(Cart request) {
        return cartRepository.save(request);
    }
}
