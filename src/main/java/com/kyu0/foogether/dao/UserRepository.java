package com.kyu0.foogether.dao;

import java.util.Optional;

import com.kyu0.foogether.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findById(String id);

    Optional<User> findByIdAndPassword(String id, String password);
}
