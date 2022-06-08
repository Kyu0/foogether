package com.kyu0.foogether.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    
    @GetMapping("/login") // 로그인 페이지
    public String index() {
        return "login";
    }

    @GetMapping("/register/member") // 회원가입 페이지
    public String register() {
        return "register_member";
    }

    @GetMapping("/forgot") // 비밀번호 찾기 페이지
    public String forgot() {
        return "forgot";
    }

    @GetMapping("/") // 메인 페이지
    public String main() {
        return "main";
    }
}
