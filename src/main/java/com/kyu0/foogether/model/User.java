package com.kyu0.foogether.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 유저 정보를 담고 있는 모델
 */
@NoArgsConstructor
@Getter
@Entity
@ToString
public class User {

    @Id
    @Column(name="id")
    private String id;
    
    @Column(name="password")
    private String password;
    
    @Column(name="name")
    private String name;
    
    @Column(name="role")
    private String role;
    
    @Column(name="email")
    private String email;
    
    @Column(name="birthday")
    private String birthday;
    
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
