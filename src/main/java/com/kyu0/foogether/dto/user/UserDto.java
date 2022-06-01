package com.kyu0.foogether.dto.user;

import java.time.LocalDate;

import javax.validation.constraints.Pattern;

import com.kyu0.foogether.model.User;
import static com.kyu0.foogether.utility.RegExpPattern.PASSWORD_PATTERN;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDto {
    private String id;

    @Pattern(regexp = PASSWORD_PATTERN, message = "비밀번호는 8자 이상, 32자 이하, 영어 + 숫자 조합으로 입력해주세요.")
    private String password;
    private String name;
    private String role;
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String phoneNumber;
    private Boolean isUse;

    @Builder
    public UserDto(String id, String password, String name, String role, String email, LocalDate birthday, String phoneNumber) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.role = role;
        this.email = email;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.isUse = true;
    }

    public User toEntity() {
        return User.builder()
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
