package com.kyu0.foogether;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.kyu0.foogether.dto.user.UserDto;
import com.kyu0.foogether.model.User;
import com.kyu0.foogether.utility.api.ApiResult;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@WebAppConfiguration
public class UserRegisterTest {
    
    private final String API_ADDRESS = "http://localhost:8080/api/v1/user";
    private TestRestTemplate restTemplate = new TestRestTemplate();

    @DisplayName("일반적인 상황 - 1")
    @Test
    void 유효한_데이터_1() {
        UserDto testUser = UserDto.builder()
                            .id("test1")
                            .password("test123456")
                            .birthday(LocalDate.parse("2012-05-12", DateTimeFormatter.ISO_DATE))
                            .email("test@naver.com")
                            .name("테스트")
                            .phoneNumber("010-1234-5678")
                            .role("ROLE_OWNER")
                        .build();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserDto> entity = new HttpEntity<>(testUser, headers);

        ResponseEntity<ApiResult<User>> result = restTemplate.exchange(API_ADDRESS, HttpMethod.POST, entity, new ParameterizedTypeReference<ApiResult<User>>(){});
        
        assertEquals(true, result.getBody().isSuccess());
    }
}
