package com.kyu0.foogether.dao;

import java.util.Optional;

import com.kyu0.foogether.model.Member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findById(String id);

    Optional<Member> findByIdAndPassword(String id, String password);
}
