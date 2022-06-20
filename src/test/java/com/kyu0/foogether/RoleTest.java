package com.kyu0.foogether;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import com.kyu0.foogether.support.MemberRole;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoleTest {

    private boolean isValidateString(String str) {
        return Arrays.stream(MemberRole.values())
              .map(role -> role.getAuthority())
              .anyMatch(role -> role.equals(str.strip()));
    }
    
    @DisplayName("일반적인 상황 - 1, 올바른 문자열을 입력했을 때")
    @Test
    void 유효한_문자열 () {
        String[] testStrings = {"ROLE_OWNER", "ROLE_RIDER", "ROLE_CUSTOMER", "ROLE_ADMIN"};
        boolean expected = true;

        for (String testString : testStrings) {
            boolean result = isValidateString(testString);    
            assertEquals(expected, result);
        }
    }

    @DisplayName("일반적인 상황 - 2, 항목에 없는 문자열을 입력했을 때")
    @Test
    void 틀린_문자열 () { 
        String testString = "ROLE_CAT";
        boolean expected = false;

        boolean result = isValidateString(testString);
        assertEquals(expected, result);
    }

    @DisplayName("특수적인 상황 - 1, 올바른 문자열에 빈 칸이 포함된 경우")
    @Test
    void 빈칸이_포함된_문자열 () {
        String[] testStrings = {" ROLE_ADMIN", "ROLE_ADMIN ", "ROLE_AD MIN"};
        boolean[] expected = {true, true, false};

        for (int i = 0 ; i < testStrings.length ; ++i) {
            boolean result = isValidateString(testStrings[i]);
            assertEquals(expected[i], result);
        }
    }

    @DisplayName("특수적인 상황 - 2, 인수의 형식이 다른 경우")
    @Test
    void 인수_형식이_다른_경우 () {
        Object[] testArguments = {1, 2, 0.23, "sss"};
        boolean expected = false;

        for (Object testArgument : testArguments) {
            boolean result = isValidateString(testArgument.toString());
            assertEquals(expected, result);
        }
    }
}
