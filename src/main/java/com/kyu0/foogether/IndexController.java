package com.kyu0.foogether;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    
    @GetMapping("/") // 로그인 페이지
    public String index() {
        return "index";
    }

    @GetMapping("/register") // 회원가입 페이지
    public String register() {
        return "register";
    }

    @GetMapping("/forgot") // 비밀번호 찾기 페이지
    public String forgot() {
        return "forgot";
    }
}
