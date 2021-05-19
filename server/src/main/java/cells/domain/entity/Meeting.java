package cells.domain.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table
public class Meeting {
    @Id
    @Column(name = "meeting_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meeting_seq")
    @SequenceGenerator(name = "meeting_seq", allocationSize = 1)
    private Long id;

    private String prayerPoints;
    private boolean isOnline;
}
