package cells.domain.entity;

import cells.domain.entity.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Cell extends BaseEntity {

    private String name;
    private String address;
    private String meetingDay;
    private LocalDate time;

    // This allows for unit testing
    public Cell(String name, String address, String meetingDay, LocalDate time) {
        this.name = name;
        this.address = address;
        this.meetingDay = meetingDay;
        this.time = time;
    }
}
