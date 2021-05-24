package cells.domain.entity.token;

import cells.domain.entity.User;
import cells.domain.entity.common.BaseEntity;
import cells.domain.enums.TokenStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
public class EmailVerificationToken extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private TokenStatus tokenStatus;

    @Column(nullable = false)
    private Instant expiryDate;

    public EmailVerificationToken() {
    }

    public EmailVerificationToken(String token, User user, TokenStatus tokenStatus, Instant expiryDate) {
        this.token = token;
        this.user = user;
        this.tokenStatus = tokenStatus;
        this.expiryDate = expiryDate;
    }

    public void setConfirmedStatus() {
        setTokenStatus(TokenStatus.CONFIRMED);
    }
}
