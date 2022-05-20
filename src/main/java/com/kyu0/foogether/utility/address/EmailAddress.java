package com.kyu0.foogether.utility.address;

// TODO : 클래스 관계 설정(상속, 구현 등)
public class EmailAddress {
    
    private String id;
    private final String delimiter = "@";
    private String domain;

    public EmailAddress(String id, String domain) {
        this.id = id;
        this.domain = domain;
    }

    public EmailAddress(String fullAddress) {
        if (!hasDelimiter(fullAddress)) {
            throw new IllegalArgumentException(String.format("구분자 {0} 가 없습니다.", delimiter));
        }
        else if (!isTwoParts(fullAddress)) {
            throw new IllegalArgumentException("이메일 주소가 두 부분으로 이루어져 있지 않습니다.");
        }

        String[] splited = fullAddress.split(delimiter);
        this.id = splited[0];
        this.domain = splited[1];
    }

    public String getFullAddress() {
        return id + delimiter + domain;
    }

    private boolean hasDelimiter(String fullAddress) {
        return fullAddress.contains(delimiter);
    }

    private boolean isTwoParts(String fullAddress) {
        return fullAddress.split(delimiter).length == 2;
    }

    @Override
    public String toString() {
        return getFullAddress();
    }
}
