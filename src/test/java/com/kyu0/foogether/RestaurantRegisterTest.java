package com.kyu0.foogether;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.kyu0.foogether.controller.RestaurantApiController;
import com.kyu0.foogether.controller.RestaurantApiController.RestauRantSaveRequest;
import com.kyu0.foogether.support.RestaurantType;
import com.kyu0.foogether.utility.api.ApiResult;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@WebAppConfiguration
public class RestaurantRegisterTest {
    
    @Autowired
    private RestaurantApiController restaurantApiController;

    @DisplayName("성공, 일반적인 상황")
    @Test
    public void 유효한_데이터_1() {
        RestauRantSaveRequest request = RestauRantSaveRequest.builder()
                                            .type(RestaurantType.KOREAN_FOOD)
                                            .name("열정품은닭발")
                                            .memberId("test1")
                                            .businessNumber("019283273")
                                            .postNumber("0182831")
                                            .address("경기도 용인시 처인구 마평동 123-4 5층(마평동)")
                                            .description("넘나 맛있는 집⭐️⭐️")
                                            .build();

        ApiResult<?> result = restaurantApiController.save(request);

        assertEquals(true, result.isSuccess());
    }
}
