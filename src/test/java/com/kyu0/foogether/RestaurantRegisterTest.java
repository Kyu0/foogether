package com.kyu0.foogether;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.kyu0.foogether.controller.RestaurantApiController.RestaurantResponse;
import com.kyu0.foogether.controller.RestaurantApiController.RestaurantSaveRequest;
import com.kyu0.foogether.support.RestaurantType;
import com.kyu0.foogether.utility.api.ApiResult;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@WebAppConfiguration
public class RestaurantRegisterTest {

    private final String API_ADDRESS = "http://localhost:8080/api/v1/restaurant";
    private TestRestTemplate restTemplate = new TestRestTemplate();

    public ResponseEntity<ApiResult<RestaurantResponse>> getResult(RestaurantSaveRequest test) {
        HttpEntity<RestaurantSaveRequest> entity = getHttpEntity(test);

        return restTemplate.exchange(API_ADDRESS, HttpMethod.POST, entity, new ParameterizedTypeReference<ApiResult<RestaurantResponse>>(){});
    }

    private HttpEntity<RestaurantSaveRequest> getHttpEntity(RestaurantSaveRequest test) {
        return new HttpEntity<>(test, new HttpHeaders());
    }

    @DisplayName("성공, 일반적인 상황")
    @Test
    public void 유효한_데이터_1() {
        RestaurantSaveRequest request = RestaurantSaveRequest.builder()
                                            .type(RestaurantType.KOREAN_FOOD)
                                            .name("열정품은닭발")
                                            .memberId("test1")
                                            .businessNumber("019-28-33273")
                                            .postNumber("01828")
                                            .address("경기도 용인시 처인구 마평동 123-4 5층(마평동)")
                                            .description("넘나 맛있는 집⭐️⭐️")
                                        .build();

        ResponseEntity<ApiResult<RestaurantResponse>> result = getResult(request);

        assertEquals(true, result.getBody().isSuccess());
    }

    @DisplayName("실패, 점주의 ID가 등록되지 않은 경우")
    @Test
    public void 유효하지_않은_데이터_1() {
        RestaurantSaveRequest request = RestaurantSaveRequest.builder()
                                            .type(RestaurantType.KOREAN_FOOD)
                                            .name("열정품은닭발")
                                            .memberId("1test1")
                                            .businessNumber("019-28-33273")
                                            .postNumber("01828")
                                            .address("경기도 용인시 처인구 마평동 123-4 5층(마평동)")
                                            .description("넘나 맛있는 집⭐️⭐️")
                                        .build();

        ResponseEntity<ApiResult<RestaurantResponse>> result = getResult(request);

        assertEquals(false, result.getBody().isSuccess());
    }

    @DisplayName("실패, 하이픈이 없는 사업자 등록 번호")
    @Test
    public void 유효하지_않은_데이터_2() {
        RestaurantSaveRequest request = RestaurantSaveRequest.builder()
                                            .type(RestaurantType.KOREAN_FOOD)
                                            .name("열정품은닭발")
                                            .memberId("test1")
                                            .businessNumber("0192833273")
                                            .postNumber("01828")
                                            .address("경기도 용인시 처인구 마평동 123-4 5층(마평동)")
                                            .description("넘나 맛있는 집⭐️⭐️")
                                        .build();

        ResponseEntity<ApiResult<RestaurantResponse>> result = getResult(request);

        assertEquals(false, result.getBody().isSuccess());
    }

    @DisplayName("실패, 자릿수가 맞지 않는 사업자 등록 번호")
    @Test
    public void 유효하지_않은_데이터_3() {
        RestaurantSaveRequest request = RestaurantSaveRequest.builder()
                                            .type(RestaurantType.KOREAN_FOOD)
                                            .name("열정품은닭발")
                                            .memberId("test1")
                                            .businessNumber("09-28-33273")
                                            .postNumber("01828")
                                            .address("경기도 용인시 처인구 마평동 123-4 5층(마평동)")
                                            .description("넘나 맛있는 집⭐️⭐️")
                                        .build();

        ResponseEntity<ApiResult<RestaurantResponse>> result = getResult(request);

        assertEquals(false, result.getBody().isSuccess());
    }

    @DisplayName("실패, 빈 칸이 포함된 사업자 등록 번호")
    @Test
    public void 유효하지_않은_데이터_4() {
        RestaurantSaveRequest request = RestaurantSaveRequest.builder()
                                            .type(RestaurantType.KOREAN_FOOD)
                                            .name("열정품은닭발")
                                            .memberId("test1")
                                            .businessNumber(" 109-28-33273")
                                            .postNumber("01828")
                                            .address("경기도 용인시 처인구 마평동 123-4 5층(마평동)")
                                            .description("넘나 맛있는 집⭐️⭐️")
                                        .build();

        ResponseEntity<ApiResult<RestaurantResponse>> result = getResult(request);

        assertEquals(false, result.getBody().isSuccess());
    }

    @DisplayName("실패, 자릿수가 맞지 않는 우편번호")
    @Test
    public void 유효하지_않은_데이터_5() {
        RestaurantSaveRequest request = RestaurantSaveRequest.builder()
                                            .type(RestaurantType.KOREAN_FOOD)
                                            .name("열정품은닭발")
                                            .memberId("test1")
                                            .businessNumber("019-28-33273")
                                            .postNumber("0128")
                                            .address("경기도 용인시 처인구 마평동 123-4 5층(마평동)")
                                            .description("넘나 맛있는 집⭐️⭐️")
                                        .build();

        ResponseEntity<ApiResult<RestaurantResponse>> result = getResult(request);

        assertEquals(false, result.getBody().isSuccess());
    }

    @DisplayName("실패, 빈 칸이 있는 우편 번호")
    @Test
    public void 유효하지_않은_데이터_6() {
        RestaurantSaveRequest request = RestaurantSaveRequest.builder()
                                            .type(RestaurantType.KOREAN_FOOD)
                                            .name("열정품은닭발")
                                            .memberId("test1")
                                            .businessNumber("019-28-33273")
                                            .postNumber(" 01828")
                                            .address("경기도 용인시 처인구 마평동 123-4 5층(마평동)")
                                            .description("넘나 맛있는 집⭐️⭐️")
                                        .build();

        ResponseEntity<ApiResult<RestaurantResponse>> result = getResult(request);

        assertEquals(false, result.getBody().isSuccess());
    }

    @DisplayName("실패, 영어로 구성된 사업자 등록 번호")
    @Test
    public void 유효하지_않은_데이터_7() {
        RestaurantSaveRequest request = RestaurantSaveRequest.builder()
                                            .type(RestaurantType.KOREAN_FOOD)
                                            .name("열정품은닭발")
                                            .memberId("test1")
                                            .businessNumber("aaa-aa-aaaaa")
                                            .postNumber("01828")
                                            .address("경기도 용인시 처인구 마평동 123-4 5층(마평동)")
                                            .description("넘나 맛있는 집⭐️⭐️")
                                        .build();

        ResponseEntity<ApiResult<RestaurantResponse>> result = getResult(request);

        assertEquals(false, result.getBody().isSuccess());
    }

    @DisplayName("실패, 빈 칸이 있는 우편 번호")
    @Test
    public void 유효하지_않은_데이터_8() {
        RestaurantSaveRequest request = RestaurantSaveRequest.builder()
                                            .type(RestaurantType.KOREAN_FOOD)
                                            .name("열정품은닭발")
                                            .memberId("test1")
                                            .businessNumber("019-28-33273")
                                            .postNumber("aaaaa")
                                            .address("경기도 용인시 처인구 마평동 123-4 5층(마평동)")
                                            .description("넘나 맛있는 집⭐️⭐️")
                                        .build();

        ResponseEntity<ApiResult<RestaurantResponse>> result = getResult(request);

        assertEquals(false, result.getBody().isSuccess());
    }
}
