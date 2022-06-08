package com.kyu0.foogether.support;

import java.util.Arrays;

import org.springframework.security.core.GrantedAuthority;

public enum MemberRole implements GrantedAuthority {
    OWNER("ROLE_OWNER")
    , CUSTOMER("ROLE_CUSTOMER")
    , RIDER("ROLE_RIDER")
    , ADMIN("ROLE_ADMIN");

    private String authority;

    private MemberRole(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public static boolean isValidateString (String str) {
        return Arrays.stream(MemberRole.values())
                .map(role -> role.getAuthority())
                .anyMatch(authority -> authority.equals(str.trim()));
    }
}