package com.kyu0.foogether.controller;

import javax.validation.Valid;

import com.kyu0.foogether.dto.member.MemberDto;
import com.kyu0.foogether.service.MemberService;
import com.kyu0.foogether.utility.api.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}