package com.kyu0.foogether.service;

import java.util.Optional;

import javax.persistence.EntityExistsException;

import com.kyu0.foogether.dao.MemberRepository;
import com.kyu0.foogether.dto.member.*;
import com.kyu0.foogether.model.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService implements UserDetailsService {
    private MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Member save(Member request) {
        if (memberRepository.existsById(request.getId())) {
            throw new EntityExistsException("이미 존재하는 로그인 ID 입니다.");
        }
        
        return memberRepository.save(request);
    }

    @Transactional(readOnly = true)
    public Optional<Member> findById(String id) {
        return memberRepository.findById(id);
    }

    public boolean delete(String id) {
        memberRepository.deleteById(id);

        return true;
    }

    /**
     * UserDetailsService 인터페이스의 필수 구현 메소드
     * 
     * @see com.kyu0.foogether.config.web.WebSecurityConfig
     * @see com.kyu0.foogether.config.provider.CustomAuthenticationProvider
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new MemberAuth(findById(username)
            .orElseThrow(() -> new UsernameNotFoundException("해당 ID를 가진 유저를 찾을 수 없습니다." + " : " + username)));
    }
}
