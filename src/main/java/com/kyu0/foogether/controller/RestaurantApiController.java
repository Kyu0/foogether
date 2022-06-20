package com.kyu0.foogether.controller;

import java.util.NoSuchElementException;

import javax.validation.Valid;

import com.kyu0.foogether.model.Member;
import com.kyu0.foogether.model.Restaurant;
import com.kyu0.foogether.service.MemberService;
import com.kyu0.foogether.service.RestaurantService;
import com.kyu0.foogether.support.RestaurantType;
import com.kyu0.foogether.utility.api.*;

import org.springframework.web.bind.annotation.*;

import lombok.*;

@RestController
public class RestaurantApiController {
    
    private final RestaurantService restaurantService;
    private final MemberService memberService;

    public RestaurantApiController(RestaurantService restaurantService, MemberService memberService) {
        this.restaurantService = restaurantService;
        this.memberService = memberService;
    }

    @PostMapping("/api/v1/restaurant")
    public ApiResult<?> save(RestauRantSaveRequest request) {
        request.setMember(memberService.findOwnerById(request.getMemberId()).orElseThrow(() -> new NoSuchElementException("해당 아이디를 가진 사장님이 없습니다.")));
        
        return ApiUtils.success(restaurantService.save(request.toEntity()));
    }

    @GetMapping("/api/v1/restaurant/{id}")
    public ApiResult<?> findById(@PathVariable(name = "id") Integer id) {
        return ApiUtils.success(restaurantService.findById(id)
                                    .orElseThrow(() -> new NoSuchElementException("해당 ID를 가진 가게를 찾을 수 없습니다."))
                               );
    }

    @GetMapping("/api/v1/restaurant/foods/{restaurantId}")
    public ApiResult<?> findFoods(@PathVariable(name = "restaurantId") Integer restaurantId) {
        return ApiUtils.success(restaurantService.findFoods(restaurantId));
    }

    // DTO 선언

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class RestauRantSaveRequest {
        private String name;
        private RestaurantType type;
        private String businessNumber;
        private String address;
        private String postNumber;
        private String description;
        private Boolean isUse = true;
        private String memberId;
        private Member member;

        @Builder
        public RestauRantSaveRequest(String name, RestaurantType type, String businessNumber, String address, String postNumber, String description, String memberId) {
            this.name = name;
            this.type = type;
            this.businessNumber = businessNumber;
            this.address = address;
            this.postNumber = postNumber;
            this.description = description;
            this.memberId = memberId;
            this.isUse = true;
        }

        public @Valid Restaurant toEntity() {
            return Restaurant.builder()
                        .name(name)
                        .type(type)
                        .businessNumber(businessNumber)
                        .address(address)
                        .postNumber(postNumber)
                        .description(description)
                        .member(member)
                    .build();
        }
    }
}