package com.kyu0.foogether.model;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.kyu0.foogether.support.MemberRole;

import static com.kyu0.foogether.utility.RegExpPattern.*;

import java.util.Date;
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
@Entity
public class Member {

    @Id
    @Size(min = 5, max = 16, message = "아이디는 5자 이상 16자 이하로 입력해주세요.")
    @Column(name="id", length = 16, unique = true)
    private String id;
    
    @NotBlank(message = "비밀번호")
    @Column(name="password", nullable = false)
    private String password;
    
    @NotBlank(message = "이름")
    @Size(min = 2, message = "이름은 성을 포함해 입력해주세요.")
    @Column(name="name", nullable = false, length = 16)
    private String name;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="role", nullable = false)
    private MemberRole role;

    @NotBlank(message = "빈 칸은 허용되지 않습니다.")
    @Email(message = "이메일 형식이 맞지 않습니다.")
    @Column(name="email", nullable = false)
    private String email;

    @Column(name="location")
    private String location;
    
    @Temporal(TemporalType.DATE)
    @Column(name="birthday", nullable = false)
    private Date birthday;
    
    @NotBlank(message = "연락처")
    @Pattern(regexp = PHONE_NUMBER_PATTERN, message = "연락처는 000-000-0000 또는 000-0000-0000, 00-000-0000 형식으로 입력해주세요.")
    @Column(name="phone_number", nullable = false)
    private String phoneNumber;
    
    @Column(name="use", nullable = false)
    private Boolean isUse;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Restaurant> restaurants;
    
    @Builder
    public Member (String id, String password, String name, MemberRole role, String email,
                 String location, Date birthday, String phoneNumber, Boolean isUse) {
        
        this.id = id;
        this.password = password;
        this.name = name;
        this.role = role;
        this.email = email;
        this.location = location;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.isUse = isUse;
    }

    @Override
    public String toString() {
        return "Member [birthday=" + birthday + ", email=" + email + ", id=" + id + ", isUse=" + isUse + ", location="
                + location + ", name=" + name + ", password=" + password + ", phoneNumber=" + phoneNumber
                + ", role=" + role + "]";
    }
}
