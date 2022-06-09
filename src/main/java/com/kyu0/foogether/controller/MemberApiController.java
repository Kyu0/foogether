package com.kyu0.foogether.controller;

import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.kyu0.foogether.dto.member.MemberDto;
import com.kyu0.foogether.service.MemberService;
import com.kyu0.foogether.utility.api.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class MemberApiController {
    private static final Logger logger = LoggerFactory.getLogger(MemberApiController.class);

    private MemberService memberService;

    @Autowired
    public MemberApiController (MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/api/v1/member")
    public ApiResult<?> save(@Valid @RequestBody MemberDto memberDto) {
        logger.info("received params : {}", memberDto);
    
        return ApiUtils.success(memberService.save(memberDto));
    }

    @GetMapping("/api/v1/member/{id}")
    public ApiResult<?> findById(@RequestParam String id) {
        return ApiUtils.success(memberService.findById(id)
                                    .orElseThrow(() -> new NoSuchElementException("해당 ID를 가진 유저를 찾을 수 없습니다."))
                                );
    }

    @PutMapping("/api/v1/member/password")
    public ApiResult<?> updatePassword() {
        // TODO : 비밀번호 수정 로직 추가
        return null;
    }

    @PutMapping("/api/v1/member/location")
    public ApiResult<?> updateLocation() {
        // TODO : 현재 위치 수정 로직 추가
        return null;
    }

    @PutMapping("/api/v1/member/name")
    public ApiResult<?> updateName() {
        // TODO : 이름 수정 로직 추가
        return null;
    }
    
    @DeleteMapping("/api/v1/member")
    public ApiResult<?> delete(@RequestBody String id) {
        memberService.delete(id);
        return ApiUtils.success(null);
    }

    @GetMapping("/logout")
    public void logout(HttpSession session) {
        // TODO : 로그아웃 로직 추가
    }
    
}