package com.coffeeshop.headoffice.enums;

import java.util.stream.Stream;

public enum Category {
    SMALL("small"),
    MEDIUM("medium"),
    BIG("big");

    private final String value;

    Category(String value) {
        this.value = value;
    }

    public static Category of(String value) {
        return Stream.of(Category.values())
                .filter(it -> it.getValue().equals(value))
                .findFirst().orElseThrow();
    }

    public String getValue() {
        return value;
    }
}
