package com.kyu0.foogether.utility;

public class RegExpPattern {
    public static final String PASSWORD_PATTERN = "[A-Za-z0-9]{8,32}";
    public static final String BIRTHDAY_PATTERN = "(19|20)[0-9]{2}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])";
    public static final String PHONE_NUMBER_PATTERN = "0[0-9]{1,2}-[0-9]{3,4}-[0-9]{4}";

    private RegExpPattern() {
        // 인스턴스 생성 방지
    }
}
