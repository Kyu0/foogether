package com.kyu0.foogether.service;

import java.util.Optional;

import com.kyu0.foogether.config.web.WebSecurityConfig;
import com.kyu0.foogether.dao.UserRepository;
import com.kyu0.foogether.dto.user.UserAuth;
import com.kyu0.foogether.dto.user.UserDto;
import com.kyu0.foogether.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Service
public class UserService implements UserDetailsService {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = WebSecurityConfig.getPasswordEncoder();
    }

    @Transactional(rollbackFor = Exception.class)
    public User save(UserDto userDto) {
        setEncodedPassword(userDto);

        // TODO : 사용 여부 설정
        
        return userRepository.save(userDto.toEntity());
    }

    private void setEncodedPassword(UserDto userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setEncodedPassword(encodedPassword);
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    /**
     * UserDetailsService 인터페이스의 필수 구현 메소드
     * 
     * @see com.kyu0.foogether.config.web.WebSecurityConfig
     * @see com.kyu0.foogether.config.provider.CustomAuthenticationProvider
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserAuth(findById(username)
            .orElseThrow(() -> new UsernameNotFoundException("해당 ID를 가진 유저를 찾을 수 없습니다." + " : " + username)));
    }
}
