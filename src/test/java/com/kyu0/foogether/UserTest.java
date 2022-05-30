package com.kyu0.foogether;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyu0.foogether.dao.UserRepository;

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
        ObjectMapper mapper = new ObjectMapper();
        RequestBuilder request = MockMvcRequestBuilders
                                .post("/api/v1/user")
                                .content("{\"id\":\"iasdj\""
                                + ",\"password\":\"1234\""
                                + ",\"email\": \"sev@naver.com\""
                                + ",\"phoneNumber\": \"12931293912\""
                                + ",\"birthday\": \"1996-07-07\""
                                + ",\"name\" : \"kyu0\""
                                + ",\"role\" : \"OWNER\"}")
                                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
            .andDo(print());
    }
}
