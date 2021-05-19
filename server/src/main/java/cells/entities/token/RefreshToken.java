package cells.model.token;

import cells.model.UserDevice;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@Entity
public class RefreshToken extends BaseModel {

    @Column(nullable = false, unique = true)
    @NaturalId(mutable = true)
    private String token;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_device_id", unique = true)
    private UserDevice userDevice;

    private Long refreshCount;

    @Column(nullable = false)
    private Instant expiryDate;

    public RefreshToken(String token, UserDevice userDevice, Long refreshCount, Instant expiryDate) {
        this.token = token;
        this.userDevice = userDevice;
        this.refreshCount = refreshCount;
        this.expiryDate = expiryDate;
    }

    public void incrementRefreshCount() {
        refreshCount = refreshCount + 1;
    }

}