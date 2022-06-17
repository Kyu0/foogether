package com.kyu0.foogether.support;

public enum RestaurantType {
    KOREAN_FOOD("KOREAN_FOOD")
    , WESTERN_FOOD("WESTERN_FOOD")
    , CHINESE_FOOD("CHINESE_FOOD")
    , JAPANESE_FOOD("JAPANESE_FOOD");

    private String name;

    private RestaurantType(String name) {
        this.name = name;
    }
}
