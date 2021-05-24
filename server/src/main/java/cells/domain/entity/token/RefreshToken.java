package cells.domain.entity.token;

import cells.domain.entity.common.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.Instant;

@Data
@NoArgsConstructor
@Entity
public class RefreshToken extends BaseEntity {

    @Column(nullable = false, unique = true)
    @NaturalId(mutable = true)
    private String token;

    private Long refreshCount;

    @Column(nullable = false)
    private Instant expiryDate;

    public RefreshToken(String token, Long refreshCount, Instant expiryDate) {
        this.token = token;
        this.refreshCount = refreshCount;
        this.expiryDate = expiryDate;
    }

    public void incrementRefreshCount() {
        refreshCount = refreshCount + 1;
    }

}