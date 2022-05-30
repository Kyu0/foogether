package com.kyu0.foogether.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.*;

/**
 * 유저 정보를 담고 있는 클래스
 * 
 * @param id 로그인 ID
 * @param password 로그인 비밀번호
 * @param role 역할 (owner, customer 등의 문자열)
 * @param email 이메일 주소
 * @param birthday 생년월일
 * @param phoneNumber 연락처
 * @param isUse 사용 여부
 */
@NoArgsConstructor
@Getter
@Entity
@ToString
public class User {

    @Id
    @NotBlank(message = "아이디")
    @Column(name="id")
    private String id;
    
    @NotBlank(message = "비밀번호")
    @Column(name="password")
    private String password;
    
    @NotBlank(message = "이름")
    @Column(name="name")
    private String name;
    
    @NotBlank(message = "역할")
    @Column(name="role")
    private String role;

    @NotBlank(message = "이메일")
    @Column(name="email")
    private String email;
    
    @NotBlank(message = "생년월일")
    @Column(name="birthday")
    private String birthday;
    
    @NotBlank(message = "연락처")
    @Column(name="phone_number")
    private String phoneNumber;
    
    @Column(name="use")
    private Boolean isUse;
    
    @Builder
    public User (String id, String password, String name, String role, String email,
                 String birthday, String phoneNumber, Boolean isUse) {
        
        this.id = id;
        this.password = password;
        this.name = name;
        this.role = role;
        this.email = email;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.isUse = isUse;
    }
}
