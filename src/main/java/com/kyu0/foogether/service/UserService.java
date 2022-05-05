package com.kyu0.foogether.service;

import java.util.Optional;

import com.kyu0.foogether.dao.UserRepository;
import com.kyu0.foogether.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Optional<User> findByIdAndPassword(String id, String password) {
        return userRepository.findByIdAndPassword(id, password);
    }
}
