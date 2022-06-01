package com.kyu0.foogether;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.kyu0.foogether.dto.user.UserDto;
import com.kyu0.foogether.model.User;
import com.kyu0.foogether.utility.api.*;

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

    public ResponseEntity<ApiResult<User>> getResult(UserDto testUser) {
        HttpEntity<UserDto> entity = getHttpEntity(testUser);

        return restTemplate.exchange(API_ADDRESS, HttpMethod.POST, entity, new ParameterizedTypeReference<ApiResult<User>>(){});
    }

    private HttpEntity<UserDto> getHttpEntity(UserDto userDto) {
        return new HttpEntity<>(userDto, new HttpHeaders());
    }

    @DisplayName("성공, 일반적인 상황")
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

        ResponseEntity<ApiResult<User>> result = getResult(testUser);
        
        assertEquals(true, result.getBody().isSuccess());
    }

    @DisplayName("성공, 비밀번호에 특수문자 입력")
    @Test
    void 유효한_데이터_2() {
        UserDto testUser = UserDto.builder()
                            .id("test1")
                            .password("test123456**")
                            .birthday(LocalDate.parse("2012-05-12", DateTimeFormatter.ISO_DATE))
                            .email("test@naver.com")
                            .name("테스트")
                            .phoneNumber("010-1234-5678")
                            .role("ROLE_OWNER")
                        .build();

        ResponseEntity<ApiResult<User>> result = getResult(testUser);

        assertEquals(true, result.getBody().isSuccess());
    }

    @DisplayName("성공, 전화번호 : 지역 전화번호")
    @Test
    void 유효한_데이터_3() {
        UserDto testUser = UserDto.builder()
                            .id("test1")
                            .password("test123456**")
                            .birthday(LocalDate.parse("2012-05-12", DateTimeFormatter.ISO_DATE))
                            .email("test@naver.com")
                            .name("테스트")
                            .phoneNumber("02-123-5678")
                            .role("ROLE_OWNER")
                        .build();

        ResponseEntity<ApiResult<User>> result = getResult(testUser);

        assertEquals(true, result.getBody().isSuccess());
    }

    @DisplayName("실패, 아이디 2글자")
    @Test
    void 유효하지_않은_데이터_1() {
        UserDto testUser = UserDto.builder()
                            .id("t1")
                            .password("test123456**")
                            .birthday(LocalDate.parse("2012-05-12", DateTimeFormatter.ISO_DATE))
                            .email("test@naver.com")
                            .name("테스트")
                            .phoneNumber("010-1234-5678")
                            .role("ROLE_OWNER")
                        .build();

        ResponseEntity<ApiResult<User>> result = getResult(testUser);

        assertEquals(false, result.getBody().isSuccess());
        System.err.println(((ApiError)result.getBody().getError()).getMessage());
    }

    @DisplayName("실패, 비밀번호 숫자만 입력")
    @Test
    void 유효하지_않은_데이터_2() {
        UserDto testUser = UserDto.builder()
                            .id("test1")
                            .password("123812938")
                            .birthday(LocalDate.parse("2012-05-12", DateTimeFormatter.ISO_DATE))
                            .email("test@naver.com")
                            .name("테스트")
                            .phoneNumber("010-1234-5678")
                            .role("ROLE_OWNER")
                        .build();

        ResponseEntity<ApiResult<User>> result = getResult(testUser);

        assertNotEquals(false, result.getBody().isSuccess());
        System.err.println(((ApiError)result.getBody().getError()).getMessage());
    }
}
