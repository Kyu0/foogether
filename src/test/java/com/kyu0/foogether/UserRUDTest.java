package com.kyu0.foogether;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.kyu0.foogether.model.Member;
import com.kyu0.foogether.utility.api.ApiResult;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@WebAppConfiguration
public class UserRUDTest {
    
    private final String API_ADDRESS = "http://localhost:8080/api/v1/member";
    private TestRestTemplate restTemplate = new TestRestTemplate();

    public ResponseEntity<ApiResult<Member>> getResult(String id) {
        HttpEntity<String> entity = new HttpEntity<>(id);

        return restTemplate.exchange(API_ADDRESS, HttpMethod.DELETE, entity, new ParameterizedTypeReference<ApiResult<Member>>(){});
    }

    @Test
    public void test() {
        String id = "test1";

        ResponseEntity<ApiResult<Member>> result = getResult(id);

        assertEquals(true, result.getBody().isSuccess());
    }
}
