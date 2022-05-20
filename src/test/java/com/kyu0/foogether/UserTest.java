package com.kyu0.foogether;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.time.LocalDate;
import java.time.Month;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;

import com.kyu0.foogether.dao.UserRepository;
import com.kyu0.foogether.dto.user.UserDto;
import com.kyu0.foogether.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ContextConfiguration
@WebAppConfiguration
public class UserTest {
    
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private UserRepository userRepository;
    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        User user = User.builder()
                        .id("seventhseven")
                        .password("1234")
                        .email("efftar@naver.com")
                        .birthday(LocalDate.of(1996, Month.JULY, 7))
                        .name("Kyu0")
                        .phoneNumber("283812938")
                        .role("OWNER")
                        .isUse(true)
                        .build();

        userRepository.save(user);
    }

    @DisplayName("로그인 테스트")
    @Test
    @Transactional
    void 로그인테스트() throws Exception {
        // given
        String username = "seventhseven";
        String password = "1234";
        String body = String.format("{\"username\" : \"%s\", \"password\" : \"%s\"}", username, password);
        
        RequestBuilder request = MockMvcRequestBuilders
                        .post("/api/v1/login")
                        // .param("username", username)
                        // .param("password", password)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON);

        // when
        mvc.perform(request)
            .andDo(print())
        // then
            .andExpect(authenticated());
            // .andExpect(forwardedUrl("/"));
    }

    @DisplayName("회원가입 테스트")
    @Test
    void 회원가입테스트() throws Exception {

    }
}
