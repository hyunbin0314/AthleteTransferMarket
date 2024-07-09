package com.example.DWTransferScoutProject.auth.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ApplicationRoleEnum {

    SUPER_ADMIN(Authority.SUPER_ADMIN),
    ADMIN(Authority.ADMIN),
    MANAGER(Authority.MANAGER),
    USER(Authority.USER),
    SCOUTER(Authority.SCOUTER),
    PLAYER(Authority.PLAYER);

    private final String authority;

    ApplicationRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String SUPER_ADMIN = "ROLE_SUPER_ADMIN";
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String MANAGER = "ROLE_MANAGER";
        public static final String USER = "ROLE_USER";
        public static final String SCOUTER = "ROLE_SCOUTER";
        public static final String PLAYER = "ROLE_PLAYER";
    }

    @JsonCreator
    public static ApplicationRoleEnum fromString(String userType) {
        for (ApplicationRoleEnum role : ApplicationRoleEnum.values()) {
            if (role.name().equalsIgnoreCase(userType)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + userType);
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
