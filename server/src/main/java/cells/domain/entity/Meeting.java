package cells.domain.entity;

import cells.domain.entity.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Meeting extends BaseEntity {
    private String prayerPoints;
    private boolean isOnline;
}
