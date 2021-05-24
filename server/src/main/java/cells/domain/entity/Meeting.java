package cells.domain.entity;

import cells.domain.entity.common.BaseEntity;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Meeting extends BaseEntity {
    private String prayerPoints;
    private boolean isOnline;
}
