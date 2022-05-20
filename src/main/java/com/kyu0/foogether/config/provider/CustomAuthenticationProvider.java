package com.kyu0.foogether.config.provider;

import com.kyu0.foogether.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public CustomAuthenticationProvider (UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 사용자가 입력한 ID, PW
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        UserDetails storedUser = userService.loadUserByUsername(username);

        
        if (storedUser == null || !isMatched(password, storedUser.getPassword())) { // 아이디 없음 혹은 비밀번호 오류일 경우
            throw new UsernameNotFoundException("아이디 혹은 비밀번호가 일치하지 않습니다.");
        }
        // TODO : SQLite Boolean 형식 없음으로 인해 isEnabled 메소드 사용할 수 있도록 해결 필요
        else if (!true){ // 사용 정지된 계정일 경우
            throw new DisabledException("사용할 수 없는 계정입니다.");
        }
        
        return new UsernamePasswordAuthenticationToken(username, password, storedUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
    
    private boolean isMatched(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
