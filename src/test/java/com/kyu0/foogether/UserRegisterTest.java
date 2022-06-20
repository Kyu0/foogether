package com.kyu0.foogether;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    // ! 테스트 전 MemberService.save() 메소드에서 if문 주석 처리 필요 (중복 아이디 가입 못하도록 제한하는 코드)
    private final String API_ADDRESS = "http://localhost:8080/api/v1/member";
    private TestRestTemplate restTemplate = new TestRestTemplate();

    public ResponseEntity<ApiResult<Member>> getResult(Member testUser) {
        HttpEntity<Member> entity = getHttpEntity(testUser);

        return restTemplate.exchange(API_ADDRESS, HttpMethod.POST, entity, new ParameterizedTypeReference<ApiResult<Member>>(){});
    }

    private HttpEntity<Member> getHttpEntity(Member userDto) {
        return new HttpEntity<>(userDto, new HttpHeaders());
    }

    // @Test
    // void 테스트_데이터_생성() throws ParseException {
    //     String password = "test123456";
    //     String year[] = {"2001", "1998", "1975"};
    //     String month[] = {"01", "03", "04", "05", "06", "07", "09", "10", "12"};
    //     String day[] = {"03", "05", "13", "23", "26"};
    //     String email = "efftar@naver.com";
    //     String familyName[] = {"김", "김", "임", "안", "최", "성"};
    //     String givenName[] = {"하나", "하늘", "영", "은지", "예지", "민혁", "현우", "상훈", "상권", "수아"};
    //     String phoneNumber = "010-3398-5234";

    //     for (int i = 0 ; i < 100 ; ++i) {
    //         String id = "test" + i;
    //         Member testUser = Member.builder()
    //                                 .id(id)
    //                                 .password(password)
    //                                 .birthday(new SimpleDateFormat("yyyy-MM-dd").parse(year[i % 3] + "-" + month[i % 9] + "-" + day[i % 5]))
    //                                 .email(email)
    //                                 .name(familyName[i % 6] + givenName[i % 10])
    //                                 .phoneNumber(phoneNumber)
    //                                 .role(MemberRole.OWNER)
    //                           .build();

    //         getResult(testUser);
    //     }
    // }

    @DisplayName("성공, 일반적인 상황")
    @Test
    void 유효한_데이터_1() throws ParseException {
        Member testUser = Member.builder()
                            .id("test1")
                            .password("test123456")
                            .birthday(new SimpleDateFormat("yyyy-MM-dd").parse("2012-05-12"))
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
    void 유효한_데이터_2() throws ParseException {
        Member testUser = Member.builder()
                            .id("test1")
                            .password("test123456**")
                            .birthday(new SimpleDateFormat("yyyy-MM-dd").parse("2012-05-12"))
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
    void 유효한_데이터_3() throws ParseException {
        Member testUser = Member.builder()
                            .id("test1")
                            .password("test123456")
                            .birthday(new SimpleDateFormat("yyyy-MM-dd").parse("2012-05-12"))
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
    void 유효하지_않은_데이터_1() throws ParseException {
        Member testUser = Member.builder()
                            .id("t1")
                            .password("test123456**")
                            .birthday(new SimpleDateFormat("yyyy-MM-dd").parse("2012-05-12"))
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
    void 유효하지_않은_데이터_2() throws ParseException {
        Member testUser = Member.builder()
                            .id("test1")
                            .password("12382231")
                            .birthday(new SimpleDateFormat("yyyy-MM-dd").parse("2012-05-12"))
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
    void 유효하지_않은_데이터_3() throws ParseException {
        
        // Member testUser = Member.builder()
        //                         .id("test1")
        //                         .password("test123456")
        //                         .birthday("1899-01-54")
        //                         .email("test@naver.com")
        //                         .name("테스트")
        //                         .phoneNumber("010-1234-5678")
        //                         .role(MemberRole.OWNER)
        //                     .build();
        
        // ResponseEntity<ApiResult<Member>> result = getResult(testUser);
        
        // assertEquals(false, result.getBody().isSuccess());
        // System.err.println(((ApiError)result.getBody().getError()).getMessage());
    }

    @DisplayName("성공")
    @Test
    void 유효하지_않은_데이터_4() throws ParseException {
        Member testUser = Member.builder()
                            .id("test1")
                            .password("1rb122381")
                            .birthday(new SimpleDateFormat("yyyy-MM-dd").parse("1899-05-12"))
                            .email("test@naver.com")
                            .name("테스트")
                            .phoneNumber("010-1234-5678")
                            .role(MemberRole.OWNER)
                        .build();

        ResponseEntity<ApiResult<Member>> result = getResult(testUser);

        assertEquals(true, result.getBody().isSuccess());
    }

    @DisplayName("실패, 이메일 형식이 아닌 값 입력 - 1")
    @Test
    void 유효하지_않은_데이터_5() throws ParseException {
        Member testUser = Member.builder()
                            .id("test1")
                            .password("test123456")
                            .birthday(new SimpleDateFormat("yyyy-MM-dd").parse("2012-05-12"))
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
    void 유효하지_않은_데이터_6() throws ParseException {
        Member testUser = Member.builder()
                            .id("test1")
                            .password("test123456")
                            .birthday(new SimpleDateFormat("yyyy-MM-dd").parse("2012-05-12"))
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
    void 유효하지_않은_데이터_7() throws ParseException {
        Member testUser = Member.builder()
                            .id("test1")
                            .password("test123456")
                            .birthday(new SimpleDateFormat("yyyy-MM-dd").parse("2012-05-12"))
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
    void 유효하지_않은_데이터_8() throws ParseException {
        Member testUser = Member.builder()
                            .id("test1")
                            .password("test123456")
                            .birthday(new SimpleDateFormat("yyyy-MM-dd").parse("2012-05-12"))
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
    void 유효하지_않은_데이터_9() throws ParseException {
        Member testUser = Member.builder()
                            .id("test1")
                            .password("test123456")
                            .birthday(new SimpleDateFormat("yyyy-MM-dd").parse("2012-05-12"))
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
    void 유효하지_않은_데이터_10() throws ParseException {
        Member testUser = Member.builder()
                            .id("test1")
                            .password("test123456")
                            .birthday(new SimpleDateFormat("yyyy-MM-dd").parse("2012-05-12"))
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
