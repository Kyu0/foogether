package com.kyu0.foogether;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.kyu0.foogether.dto.member.MemberDto;
import com.kyu0.foogether.model.Member;
import com.kyu0.foogether.support.MemberRole;
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
    // ! 테스트 전 MemberService.save() 메소드에서 38~40 줄 주석 처리 필요 (중복 아이디 가입 못하도록 제한하는 코드)
    private final String API_ADDRESS = "http://localhost:8080/api/v1/member";
    private TestRestTemplate restTemplate = new TestRestTemplate();

    public ResponseEntity<ApiResult<Member>> getResult(MemberDto testUser) {
        HttpEntity<MemberDto> entity = getHttpEntity(testUser);

        return restTemplate.exchange(API_ADDRESS, HttpMethod.POST, entity, new ParameterizedTypeReference<ApiResult<Member>>(){});
    }

    private HttpEntity<MemberDto> getHttpEntity(MemberDto userDto) {
        return new HttpEntity<>(userDto, new HttpHeaders());
    }

    @DisplayName("성공, 일반적인 상황")
    @Test
    void 유효한_데이터_1() {
        MemberDto testUser = MemberDto.builder()
                            .id("test1")
                            .password("test123456")
                            .birthday(LocalDate.parse("2012-05-12", DateTimeFormatter.ISO_DATE))
                            .email("test@naver.com")
                            .name("테스트")
                            .phoneNumber("010-1234-5678")
                            .role(MemberRole.OWNER)
                        .build();

        ResponseEntity<ApiResult<Member>> result = getResult(testUser);
        
        assertEquals(true, result.getBody().isSuccess());
    }

    @DisplayName("성공, 비밀번호에 특수문자 입력")
    @Test
    void 유효한_데이터_2() {
        MemberDto testUser = MemberDto.builder()
                            .id("test1")
                            .password("test123456**")
                            .birthday(LocalDate.parse("2012-05-12", DateTimeFormatter.ISO_DATE))
                            .email("test@naver.com")
                            .name("테스트")
                            .phoneNumber("010-1234-5678")
                            .role(MemberRole.OWNER)
                        .build();

        ResponseEntity<ApiResult<Member>> result = getResult(testUser);

        assertEquals(true, result.getBody().isSuccess());
    }

    @DisplayName("성공, 전화번호 : 지역 전화번호")
    @Test
    void 유효한_데이터_3() {
        MemberDto testUser = MemberDto.builder()
                            .id("test1")
                            .password("test123456")
                            .birthday(LocalDate.parse("2012-05-12", DateTimeFormatter.ISO_DATE))
                            .email("test@naver.com")
                            .name("테스트")
                            .phoneNumber("02-123-5678")
                            .role(MemberRole.OWNER)
                        .build();

        ResponseEntity<ApiResult<Member>> result = getResult(testUser);

        assertEquals(true, result.getBody().isSuccess());
    }

    @DisplayName("실패, 아이디 2글자")
    @Test
    void 유효하지_않은_데이터_1() {
        MemberDto testUser = MemberDto.builder()
                            .id("t1")
                            .password("test123456**")
                            .birthday(LocalDate.parse("2012-05-12", DateTimeFormatter.ISO_DATE))
                            .email("test@naver.com")
                            .name("테스트")
                            .phoneNumber("010-1234-5678")
                            .role(MemberRole.OWNER)
                        .build();

        ResponseEntity<ApiResult<Member>> result = getResult(testUser);

        assertEquals(false, result.getBody().isSuccess());
        System.err.println(((ApiError)result.getBody().getError()).getMessage());
    }

    @DisplayName("실패, 비밀번호 숫자만 입력")
    @Test
    void 유효하지_않은_데이터_2() {
        MemberDto testUser = MemberDto.builder()
                            .id("test1")
                            .password("12382231")
                            .birthday(LocalDate.parse("2012-05-12", DateTimeFormatter.ISO_DATE))
                            .email("test@naver.com")
                            .name("테스트")
                            .phoneNumber("010-1234-5678")
                            .role(MemberRole.OWNER)
                        .build();

        ResponseEntity<ApiResult<Member>> result = getResult(testUser);

        assertEquals(false, result.getBody().isSuccess());
        System.err.println(((ApiError)result.getBody().getError()).getMessage());
    }
    
    @DisplayName("실패, 올바르지 않은 생년월일 입력 1")
    @Test
    void 유효하지_않은_데이터_3() {
        
        // MemberDto testUser = MemberDto.builder()
        //                         .id("test1")
        //                         .password("test123456")
        //                         .birthday(LocalDate.parse("1899-01-54", DateTimeFormatter.ISO_DATE))
        //                         .email("test@naver.com")
        //                         .name("테스트")
        //                         .phoneNumber("010-1234-5678")
        //                         .role(MemberRole.OWNER)
        //                     .build();
        
        // ResponseEntity<ApiResult<Member>> result = getResult(testUser);
        
        // assertEquals(false, result.getBody().isSuccess());
        // System.err.println(((ApiError)result.getBody().getError()).getMessage());
    }

    @DisplayName("실패, 올바르지 않은 생년월일 입력 2")
    @Test
    void 유효하지_않은_데이터_4() {
        MemberDto testUser = MemberDto.builder()
                            .id("test1")
                            .password("1rb122381")
                            .birthday(LocalDate.parse("1899-05-12", DateTimeFormatter.ISO_DATE))
                            .email("test@naver.com")
                            .name("테스트")
                            .phoneNumber("010-1234-5678")
                            .role(MemberRole.OWNER)
                        .build();

        ResponseEntity<ApiResult<Member>> result = getResult(testUser);

        assertEquals(false, result.getBody().isSuccess());
        System.err.println(((ApiError)result.getBody().getError()).getMessage());
    }

    @DisplayName("실패, 이메일 형식이 아닌 값 입력 - 1")
    @Test
    void 유효하지_않은_데이터_5() {
        MemberDto testUser = MemberDto.builder()
                            .id("test1")
                            .password("test123456")
                            .birthday(LocalDate.parse("2012-05-12", DateTimeFormatter.ISO_DATE))
                            .email("test@.")
                            .name("테스트")
                            .phoneNumber("010-1234-5678")
                            .role(MemberRole.OWNER)
                        .build();

        ResponseEntity<ApiResult<Member>> result = getResult(testUser);

        assertEquals(false, result.getBody().isSuccess());
        System.err.println(((ApiError)result.getBody().getError()).getMessage());
    }

    @DisplayName("성공, 이메일 형식이 아닌 값 입력 - 2 (최상위 도메인은 없어도 가능)")
    @Test
    void 유효하지_않은_데이터_6() {
        MemberDto testUser = MemberDto.builder()
                            .id("test1")
                            .password("test123456")
                            .birthday(LocalDate.parse("2012-05-12", DateTimeFormatter.ISO_DATE))
                            .email("test@naver")
                            .name("테스트")
                            .phoneNumber("010-1234-5678")
                            .role(MemberRole.OWNER)
                        .build();

        ResponseEntity<ApiResult<Member>> result = getResult(testUser);

        assertEquals(true, result.getBody().isSuccess());
    }

    @DisplayName("실패, 이메일 형식이 아닌 값 입력 - 3")
    @Test
    void 유효하지_않은_데이터_7() {
        MemberDto testUser = MemberDto.builder()
                            .id("test1")
                            .password("test123456")
                            .birthday(LocalDate.parse("2012-05-12", DateTimeFormatter.ISO_DATE))
                            .email("testnaver.com")
                            .name("테스트")
                            .phoneNumber("010-1234-5678")
                            .role(MemberRole.OWNER)
                        .build();

        ResponseEntity<ApiResult<Member>> result = getResult(testUser);

        assertEquals(false, result.getBody().isSuccess());
        System.err.println(((ApiError)result.getBody().getError()).getMessage());
    }

    @DisplayName("실패, 올바르지 않은 휴대폰 번호 입력 - 1")
    @Test
    void 유효하지_않은_데이터_8() {
        MemberDto testUser = MemberDto.builder()
                            .id("test1")
                            .password("test123456")
                            .birthday(LocalDate.parse("2012-05-12", DateTimeFormatter.ISO_DATE))
                            .email("test@naver.com")
                            .name("테스트")
                            .phoneNumber("010-12345-5678")
                            .role(MemberRole.OWNER)
                        .build();

        ResponseEntity<ApiResult<Member>> result = getResult(testUser);

        assertEquals(false, result.getBody().isSuccess());
        System.err.println(((ApiError)result.getBody().getError()).getMessage());
    }

    @DisplayName("실패, 올바르지 않은 휴대폰 번호 입력 - 2")
    @Test
    void 유효하지_않은_데이터_9() {
        MemberDto testUser = MemberDto.builder()
                            .id("test1")
                            .password("test123456")
                            .birthday(LocalDate.parse("2012-05-12", DateTimeFormatter.ISO_DATE))
                            .email("test@naver.com")
                            .name("테스트")
                            .phoneNumber("010125678")
                            .role(MemberRole.OWNER)
                        .build();

        ResponseEntity<ApiResult<Member>> result = getResult(testUser);

        assertEquals(false, result.getBody().isSuccess());
        System.err.println(((ApiError)result.getBody().getError()).getMessage());
    }

    @DisplayName("실패, 올바르지 않은 휴대폰 번호 입력 - 3")
    @Test
    void 유효하지_않은_데이터_10() {
        MemberDto testUser = MemberDto.builder()
                            .id("test1")
                            .password("test123456")
                            .birthday(LocalDate.parse("2012-05-12", DateTimeFormatter.ISO_DATE))
                            .email("test@naver.com")
                            .name("테스트")
                            .phoneNumber("110-1234-5678")
                            .role(MemberRole.OWNER)
                        .build();

        ResponseEntity<ApiResult<Member>> result = getResult(testUser);

        assertEquals(false, result.getBody().isSuccess());
        System.err.println(((ApiError)result.getBody().getError()).getMessage());
    }
}
