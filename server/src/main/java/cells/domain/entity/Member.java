package cells.domain.entity;

import cells.domain.entity.common.EntityAudit;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Period;

@Data
@Entity
@NoArgsConstructor
public class Member extends EntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 20)
    private String firstname;

    @NotBlank
    @Size(min = 3, max = 20)
    private String lastname;

    @NotBlank
    @Size(max = 50)
    private String email;

    @NotNull
    private LocalDate dob;

    @Transient
    private  Integer age;

    // This allows for unit testing
    public Member(String firstname, String lastname, String email, LocalDate dob) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.dob = dob;
    }

    public Integer getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }
}

