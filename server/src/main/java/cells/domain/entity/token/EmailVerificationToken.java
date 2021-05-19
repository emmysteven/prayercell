package cells.domain.entity.token;

import cells.domain.entity.User;
import cells.domain.entity.audit.DateAudit;
import cells.domain.enums.TokenStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity(name = "EMAIL_VERIFICATION_TOKEN")
public class EmailVerificationToken extends DateAudit {

    @Id
    @Column(name="token_id")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="email_token_seq")
    @SequenceGenerator(name="email_token_seq", allocationSize=1)
    private Long id;

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
