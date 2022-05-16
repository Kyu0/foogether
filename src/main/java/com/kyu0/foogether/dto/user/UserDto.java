package com.kyu0.foogether.dto.user;

import com.kyu0.foogether.model.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDto {
    private String id;
    private String password;
    private String name;
    private String role;
    private String email;
    private String birthday;
    private String phoneNumber;
    private Boolean isUse;

    @Builder
    public UserDto(String id, String password, String name, String role, String email, String birthday, String phoneNumber) {
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
                    .birthday(this.birthday)
                    .phoneNumber(this.phoneNumber)
                    .isUse(this.isUse)
                .build();
    }
}
