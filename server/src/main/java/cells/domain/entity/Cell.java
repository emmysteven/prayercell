package cells.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity

public class Cell {
    @Id
    @Column(name = "cell_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cell_seq")
    @SequenceGenerator(name = "cell_seq", allocationSize = 1)
    private Long id;

    private String name;
    private String address;
    private String meetingDay;
    private LocalDate time;

    // This allows for unit testing
    public Cell(Long id, String name, String address, String meetingDay, LocalDate time) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.meetingDay = meetingDay;
        this.time = time;
    }
}
