package com.kyu0.foogether.config.provider;

import com.kyu0.foogether.service.MemberService;

import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final MemberService userService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider (MemberService userService, PasswordEncoder passwordEncoder) {
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
        else if (!storedUser.isEnabled()){ // 사용 정지된 계정일 경우
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
