package com.kyu0.foogether.controller;

import java.util.Date;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import com.kyu0.foogether.config.web.WebSecurityConfig;
import com.kyu0.foogether.model.Member;
import com.kyu0.foogether.service.MemberService;
import com.kyu0.foogether.support.MemberRole;
import com.kyu0.foogether.utility.api.*;

import static com.kyu0.foogether.utility.RegExpPattern.*;

import org.slf4j.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import lombok.*;


@RestController
public class MemberApiController {
    private static final Logger logger = LoggerFactory.getLogger(MemberApiController.class);

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    public MemberApiController (MemberService memberService) {
        this.memberService = memberService;
        this.passwordEncoder = WebSecurityConfig.getPasswordEncoder();
    }

    @PostMapping("/api/v1/member")
    public ApiResult<?> save(@Valid @RequestBody MemberSaveRequest request) {
        logger.info("received params : {}", request);

        request.password = passwordEncoder.encode(request.getPassword());

        return ApiUtils.success(new MemberResponse(memberService.save(request.toEntity())));
    }

    @GetMapping("/api/v1/member/{id}")
    public ApiResult<?> findById(@PathVariable String id) {
        return ApiUtils.success(new MemberResponse(memberService.findById(id)
                                    .orElseThrow(() -> new NoSuchElementException("해당 ID를 가진 유저를 찾을 수 없습니다."))
                                ));
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

    // DTO 선언
    
    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class MemberSaveRequest {
        private String id;

        @Pattern(regexp = PASSWORD_PATTERN, message = "비밀번호는 8자 이상, 32자 이하, 영어 + 숫자 조합으로 입력해주세요.")
        private String password;
        private String name;
        private MemberRole role;
        private String email;
        private Date birthday;
        private String phoneNumber;
    
        @Builder
        public MemberSaveRequest(String id, String password, String name, MemberRole role, String email, Date birthday, String phoneNumber) {
            this.id = id;
            this.password = password;
            this.name = name;
            this.role = role;
            this.email = email;
            this.birthday = birthday;
            this.phoneNumber = phoneNumber;
        }
    
        public @Valid Member toEntity() {
            return Member.builder()
                        .id(this.id)
                        .password(this.password)
                        .name(this.name)
                        .role(this.role)
                        .email(this.email)
                        .birthday(this.birthday)
                        .phoneNumber(this.phoneNumber)
                        .isUse(true)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @ToString
    public static class MemberResponse {
        private String id;
        private String name;
        private MemberRole role;
        private String email;
        private Date birthday;
        private String phoneNumber;

        public MemberResponse(Member entity) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.role = entity.getRole();
            this.email = entity.getEmail();
            this.birthday = entity.getBirthday();
            this.phoneNumber = entity.getPhoneNumber();
        }
    }
}