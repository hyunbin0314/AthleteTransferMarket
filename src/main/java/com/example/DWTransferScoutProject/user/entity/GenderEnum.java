package com.example.DWTransferScoutProject.user.entity;

public enum GenderEnum {
    MALE("남자"),
    FEMALE("여자");

    private final String displayName;

    GenderEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
