package cells.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor

public class Cell extends BaseModel {
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
