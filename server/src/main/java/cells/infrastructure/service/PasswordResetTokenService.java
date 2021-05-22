package cells.infrastructure.service;


import cells.application.exception.InvalidTokenRequestException;
import cells.application.util.Util;
import cells.domain.entity.PasswordResetToken;
import cells.infrastructure.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository repository;

    @Value("${app.token.password.reset.duration}")
    private Long expiration;

    public PasswordResetTokenService(PasswordResetTokenRepository repository) {
        this.repository = repository;
    }

    /**
     * Saves the given password reset token
     */
    public PasswordResetToken save(PasswordResetToken passwordResetToken) {
        return repository.save(passwordResetToken);
    }

    /**
     * Finds a token in the database given its naturalId
     */
    public Optional<PasswordResetToken> findByToken(String token) {
        return repository.findByToken(token);
    }

    /**
     * Creates and returns a new password token to which a user must be associated
     */
    public PasswordResetToken createToken() {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        String token = Util.generateRandomUuid();
        passwordResetToken.setToken(token);
        passwordResetToken.setExpiryDate(Instant.now().plusMillis(expiration));
        return passwordResetToken;
    }

    /**
     * Verify whether the token provided has expired or not on the basis of the current
     * server time and/or throw error otherwise
     */
    public void verifyExpiration(PasswordResetToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            throw new InvalidTokenRequestException("Password Reset Token", token.getToken(),
                    "Expired token. Please issue a new request");
        }
    }
}
