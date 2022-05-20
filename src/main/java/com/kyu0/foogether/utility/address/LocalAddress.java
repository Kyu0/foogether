package com.kyu0.foogether.utility.address;

// TODO : 클래스 관계 설정 (상속, 구현 등)
public class LocalAddress {
    
    private String location;
    private final String delimiter = " ";
    private String detailLocation;

    public LocalAddress(String location, String detailLocation) {
        this.location = location;
        this.detailLocation = detailLocation;
    }

    public LocalAddress(String fullAddress) {
        if (!hasDelimiter(fullAddress)) {
            throw new IllegalArgumentException(String.format("구분자 {0} 가 없습니다.", delimiter));
        }
        else if (!isTwoParts(fullAddress)) {
            throw new IllegalArgumentException("주소가 두 부분으로 이루어져 있지 않습니다.");
        }

        String[] splited = fullAddress.split(delimiter);
        this.location = splited[0];
        this.detailLocation = splited[1];
    }

    public String getFullAddress() {
        return location + delimiter + detailLocation;
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
