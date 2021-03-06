package com.kyu0.foogether.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.kyu0.foogether.controller.FoodApiController.FoodResponse;
import com.kyu0.foogether.controller.MemberApiController.MemberResponse;
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
    public ApiResult<?> save(@RequestBody RestaurantSaveRequest request) {
        request.setMember(memberService.findOwnerById(request.getMemberId()).orElseThrow(() -> new NoSuchElementException("해당 아이디를 가진 사장님이 없습니다.")));
        
        return ApiUtils.success(new RestaurantSaveResponse(restaurantService.save(request.toEntity())));
    }

    @GetMapping("/api/v1/restaurant")
    public ApiResult<?> findAll() {
        return ApiUtils.success(restaurantService.findAll().stream()
                                    .map(RestaurantListResponse::new)
                                    .collect(Collectors.toList()));
    }

    @GetMapping("/api/v1/restaurant/{id}")
    public ApiResult<?> findById(@PathVariable(name = "id") Integer id) {
        return ApiUtils.success(new RestaurantResponse(restaurantService.findById(id)
                                    .orElseThrow(() -> new NoSuchElementException("해당 ID를 가진 가게를 찾을 수 없습니다."))
                               ));
    }

    @GetMapping("/api/v1/restaurant/{restaurantId}/foods")
    public ApiResult<?> findFoods(@PathVariable(name = "restaurantId") Integer restaurantId) {
        return ApiUtils.success(restaurantService.findFoods(restaurantId));
    }

    // DTO 선언

    @Getter
    @Setter
    @ToString(exclude = "member")
    @NoArgsConstructor
    public static class RestaurantSaveRequest {
        private String name;
        private RestaurantType type;
        private String businessNumber;
        private String address;
        private String postNumber;
        private String description;
        private String memberId;
        private Member member;

        @Builder
        public RestaurantSaveRequest(String name, RestaurantType type, String businessNumber, String address, String postNumber, String description, String memberId) {
            this.name = name;
            this.type = type;
            this.businessNumber = businessNumber;
            this.address = address;
            this.postNumber = postNumber;
            this.description = description;
            this.memberId = memberId;
        }

        public @Valid Restaurant toEntity() {
            return Restaurant.builder()
                        .name(name)
                        .type(type)
                        .businessNumber(businessNumber)
                        .address(address)
                        .postNumber(postNumber)
                        .description(description)
                        .isUse(true)
                        .member(member)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @ToString
    public static class RestaurantSaveResponse {
        private Integer id;
        private String name;
        private RestaurantType type;
        private String businessNumber;
        private String address;
        private String postNumber;
        private String description;
        private Boolean isUse;
        private String memberId;

        public RestaurantSaveResponse(Restaurant entity) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.type = entity.getType();
            this.businessNumber = entity.getBusinessNumber();
            this.address = entity.getAddress();
            this.postNumber = entity.getPostNumber();
            this.description = entity.getDescription();
            this.isUse = entity.getIsUse();
            this.memberId = entity.getMember().getId();
        }
    }

    @Getter
    @NoArgsConstructor
    @ToString
    public static class RestaurantResponse {
        private Integer id;
        private String name;
        private RestaurantType type;
        private String businessNumber;
        private String address;
        private String postNumber;
        private String description;
        private Boolean isUse;
        private MemberResponse member;
        private List<FoodResponse> foods;

        public RestaurantResponse(Restaurant entity) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.type = entity.getType();
            this.businessNumber = entity.getBusinessNumber();
            this.address = entity.getAddress();
            this.postNumber = entity.getPostNumber();
            this.description = entity.getDescription();
            this.isUse = entity.getIsUse();
            this.member = new MemberResponse(entity.getMember());
            this.foods = entity.getFoods().stream()
                                .map(FoodResponse::new)
                                .collect(Collectors.toList());
        }
    }

    @Getter
    @NoArgsConstructor
    @ToString
    public static class RestaurantListResponse {
        private Integer id;
        private String name;
        private RestaurantType type;
        private String description;
        private Boolean isUse;

        public RestaurantListResponse(Restaurant entity) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.type = entity.getType();
            this.description = entity.getDescription();
            this.isUse = entity.getIsUse();
        }
    }
}