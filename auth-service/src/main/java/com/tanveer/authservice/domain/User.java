package com.tanveer.authservice.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public final class User {
    private final String id;
    private final String email;
    private final String password;
    private final String role;

    public static User create(String id, String email, String password, String role) {
        return new User(id, email, password, role);
    }
}
