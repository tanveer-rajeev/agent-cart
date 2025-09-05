package com.tanveer.authservice.infrastructure.persistance;

import com.tanveer.authservice.domain.User;

public interface TokenRepository {
    void revokeAllUserTokens(String email);
    void saveUserToken(User user, String token);
}
