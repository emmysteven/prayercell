package cells.model;

import cells.model.common.BaseModel;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table
public class Meeting extends BaseModel {
    private String prayerPoints;
    private boolean isOnline;
}
