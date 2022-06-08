package com.kyu0.foogether.dto.member;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import com.kyu0.foogether.model.Member;
import com.kyu0.foogether.support.MemberRole;

import static com.kyu0.foogether.utility.RegExpPattern.PASSWORD_PATTERN;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberDto {
    private String id;

    @Pattern(regexp = PASSWORD_PATTERN, message = "비밀번호는 8자 이상, 32자 이하, 영어 + 숫자 조합으로 입력해주세요.")
    private String password;
    private String name;
    private MemberRole role;
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String phoneNumber;
    private Boolean isUse;

    @Builder
    public MemberDto(String id, String password, String name, MemberRole role, String email, LocalDate birthday, String phoneNumber) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.role = role;
        this.email = email;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.isUse = true;
    }

    public @Valid Member toEntity() {
        return Member.builder()
                    .id(this.id)
                    .password(this.password)
                    .name(this.name)
                    .role(this.role)
                    .email(this.email)
                    .birthday(this.birthday.toString())
                    .phoneNumber(this.phoneNumber)
                    .isUse(this.isUse)
                .build();
    }

    public void setEncodedPassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
