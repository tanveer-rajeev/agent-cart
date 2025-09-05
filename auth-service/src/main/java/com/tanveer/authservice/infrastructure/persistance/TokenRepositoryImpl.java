package com.tanveer.authservice.infrastructure.persistance;

import com.tanveer.authservice.domain.User;
import com.tanveer.authservice.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class TokenRepositoryImpl implements TokenRepository {

    private final TokenJpaRepository repository;

    @Override
    public void revokeAllUserTokens(String email) {
        log.debug("Revoking all tokens for the email: {}", email);

        var validUserTokens = repository.findAllValidTokenByUser(email);

        if (validUserTokens.isEmpty()) {
            log.debug("No valid tokens to revoke for the email: {}", email);
            return;
        }

        validUserTokens.forEach(tokenEntity -> {
            tokenEntity.setExpired(true);
            tokenEntity.setRevoked(true);
        });

        repository.saveAll(validUserTokens);

        log.debug("Revoked all tokens for the email: {}", email);
    }

    public void saveUserToken(User user, String jwtToken) {
        log.debug("Saving token for organizer id: {}", user.getId());

        var token = TokenEntity.builder()
                .userEntity(UserMapper.domainToEntity(user))
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        repository.save(token);

        log.debug("Token saved for organizer id: {}", user.getId());
    }
}
