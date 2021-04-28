package cells.model;

import cells.model.common.BaseModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
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
