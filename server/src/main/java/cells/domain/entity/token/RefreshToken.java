package cells.domain.entity.token;

import cells.domain.entity.User;
import cells.domain.entity.common.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@Entity
public class RefreshToken extends BaseEntity {

    @Column(nullable = false, unique = true)
    @NaturalId(mutable = true)
    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Long refreshCount;

    @Column(nullable = false)
    private Instant expiryDate;

    public RefreshToken(Long id, String token, User user, Long refreshCount, Instant expiryDate) {
        this.id = id;
        this.user = user;
        this.token = token;
        this.expiryDate = expiryDate;
        this.refreshCount = refreshCount;
    }

    public void incrementRefreshCount() {
        refreshCount = refreshCount + 1;
    }

}