package cells.infrastructure.service;


import cells.application.exception.InvalidTokenRequestException;
import cells.domain.entity.User;
import cells.domain.entity.token.EmailVerificationToken;
import cells.domain.enums.TokenStatus;
import cells.infrastructure.repository.EmailVerificationTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class EmailVerificationService {

    private final EmailVerificationTokenRepository repository;

    @Value("${app.token.email.verification.duration}")
    private Long emailVerificationTokenExpiryDuration;

    public EmailVerificationService(EmailVerificationTokenRepository repository) {
        this.repository = repository;
    }

    /**
     * Create an email verification token and persist it in the database which will be
     * verified by the user
     */
    public void createVerificationToken(User user, String token) {
        EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
        emailVerificationToken.setToken(token);
        emailVerificationToken.setTokenStatus(TokenStatus.PENDING);
        emailVerificationToken.setUser(user);
        emailVerificationToken.setExpiryDate(Instant.now().plusMillis(emailVerificationTokenExpiryDuration));
        log.info("Generated Email verification token [" + emailVerificationToken + "]");
        repository.save(emailVerificationToken);
    }

    /**
     * Updates an existing token in the database with a new expiration
     */
    public EmailVerificationToken updateExistingTokenWithNameAndExpiry(EmailVerificationToken existingToken) {
        existingToken.setTokenStatus(TokenStatus.PENDING);
        existingToken.setExpiryDate(Instant.now().plusMillis(emailVerificationTokenExpiryDuration));
        log.info("Updated Email verification token [" + existingToken + "]");
        return save(existingToken);
    }

    /**
     * Finds an email verification token by the @NaturalId token
     */
    public Optional<EmailVerificationToken> findByToken(String token) {
        return repository.findByToken(token);
    }

    /**
     * Saves an email verification token in the repository
     */
    public EmailVerificationToken save(EmailVerificationToken emailVerificationToken) {
        return repository.save(emailVerificationToken);
    }

    /**
     * Generates a new random UUID to be used as the token for email verification
     */
    public String generateNewToken() {
        return UUID.randomUUID().toString();
    }

    /**
     * Verify whether the token provided has expired or not on the basis of the current
     * server time and/or throw error otherwise
     */
    public void verifyExpiration(EmailVerificationToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            throw new InvalidTokenRequestException(
                    "Email Verification Token",
                    token.getToken(),
                    "Expired token. Please issue a new request"
            );
        }
    }

}
