package com.kyu0.foogether.service;

import java.util.Optional;

import javax.persistence.EntityExistsException;

import com.kyu0.foogether.config.web.WebSecurityConfig;
import com.kyu0.foogether.dao.MemberRepository;
import com.kyu0.foogether.dto.member.*;
import com.kyu0.foogether.model.Member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService implements UserDetailsService {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = WebSecurityConfig.getPasswordEncoder();
    }

    @Transactional
    public Member save(MemberDto memberDto) {
        setEncodedPassword(memberDto);
        memberDto.setIsUse(true);

        if (memberRepository.existsById(memberDto.getId())) {
            throw new EntityExistsException("이미 존재하는 로그인 ID 입니다.");
        }
        
        return memberRepository.save(memberDto.toEntity());
    }

    private void setEncodedPassword(MemberDto memberDto) {
        String encodedPassword = passwordEncoder.encode(memberDto.getPassword());
        memberDto.setEncodedPassword(encodedPassword);
    }

    public Optional<Member> findById(String id) {
        return memberRepository.findById(id);
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
