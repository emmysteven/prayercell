package cells.domain.entity;

import cells.domain.entity.audit.DateAudit;
import cells.domain.entity.token.RefreshToken;
import cells.domain.enums.DeviceType;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserDevice extends DateAudit {

    @Id
    @Column(name = "user_device_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_device_seq")
    @SequenceGenerator(name = "user_device_seq", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(value = EnumType.STRING)
    private DeviceType deviceType;

    private String notificationToken;

    @Column(nullable = false)
    private String deviceId;

    @OneToOne(optional = false, mappedBy = "userDevice")
    private RefreshToken refreshToken;

    private Boolean isRefreshActive;

    public UserDevice() {
    }

    public UserDevice(
            Long id,
            User user,
            DeviceType deviceType,
            String notificationToken,
            String deviceId,
            RefreshToken refreshToken,
            Boolean isRefreshActive
    ) {
        this.id = id;
        this.user = user;
        this.deviceType = deviceType;
        this.notificationToken = notificationToken;
        this.deviceId = deviceId;
        this.refreshToken = refreshToken;
        this.isRefreshActive = isRefreshActive;
    }

    public Boolean getRefreshActive() {
        return isRefreshActive;
    }

    public void setRefreshActive(Boolean refreshActive) {
        isRefreshActive = refreshActive;
    }
}