package com.kyu0.foogether.model;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.kyu0.foogether.support.MemberRole;

import static com.kyu0.foogether.utility.RegExpPattern.*;

import java.util.List;

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
@ToString
@Entity
public class Member {

    @Id
    @NotBlank(message = "아이디")
    @Size(min = 5, max = 16, message = "아이디는 5자 이상 16자 이하로 입력해주세요.")
    @Column(name="id")
    private String id;
    
    @NotBlank(message = "비밀번호")
    @Column(name="password")
    private String password;
    
    @NotBlank(message = "이름")
    @Size(min = 2, message = "이름은 성을 포함해 입력해주세요.")
    @Column(name="name")
    private String name;
    
    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private MemberRole role;

    @NotBlank(message = "빈 칸은 허용되지 않습니다.")
    @Email(message = "이메일 형식이 맞지 않습니다.")
    @Column(name="email")
    private String email;
    
    @NotBlank(message = "생년월일")
    @Pattern(regexp = BIRTHDAY_PATTERN, message = "생년월일은 yyyy-MM-dd (y:생년, M:생월, d:생일) 의 형식으로 입력해주세요.")
    @Column(name="birthday")
    private String birthday;
    
    @NotBlank(message = "연락처")
    @Pattern(regexp = PHONE_NUMBER_PATTERN, message = "연락처는 000-000-0000 또는 000-0000-0000, 00-000-0000 형식으로 입력해주세요.")
    @Column(name="phone_number")
    private String phoneNumber;
    
    @Column(name="use")
    private Boolean isUse;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Restaurant> restaurants;
    
    @Builder
    public Member (String id, String password, String name, MemberRole role, String email,
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
