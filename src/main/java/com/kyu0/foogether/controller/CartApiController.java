package com.kyu0.foogether.controller;

import java.util.NoSuchElementException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kyu0.foogether.model.Cart;
import com.kyu0.foogether.model.Member;
import com.kyu0.foogether.model.Restaurant;
import com.kyu0.foogether.service.CartService;
import com.kyu0.foogether.service.MemberService;
import com.kyu0.foogether.service.RestaurantService;
import com.kyu0.foogether.utility.api.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RestController
public class CartApiController {
    
    private final CartService cartService;
    private final MemberService memberService;
    private final RestaurantService restaurantService;

    public CartApiController(CartService cartService, MemberService memberService, RestaurantService restaurantService) {
        this.cartService = cartService;
        this.memberService = memberService;
        this.restaurantService = restaurantService;
    }

    @PostMapping("/cart")
    public ApiResult<?> save(@RequestBody CartSaveRequest request) {
        request.setMember(memberService.findById(request.getMemberId()).orElseThrow(() -> new NoSuchElementException("해당 아이디를 가진 회원이 없습니다.")));
        request.setRestaurant(restaurantService.findById(request.restaurantId).orElseThrow(() -> new NoSuchElementException("해당 식별 번호를 가진 가게가 없습니다.")));

        return ApiUtils.success(cartService.save(request.toEntity()));
    }

    // DTO 선언
    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class CartSaveRequest {
        private String memberId;
        private Integer restaurantId;
        private Member member;
        private Restaurant restaurant;

        @Builder
        public CartSaveRequest(String memberId, Integer restaurantId) {
            this.memberId = memberId;
            this.restaurantId = restaurantId;
        }

        public Cart toEntity() {
            return Cart.builder()
                    .member(member)
                    .restaurant(restaurant)
                    .build();
        }
    }
}
