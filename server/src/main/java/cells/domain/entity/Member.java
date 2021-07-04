package cells.domain.entity;

import cells.domain.entity.common.EntityAudit;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Member extends EntityAudit<String> implements Serializable {
    private static final long serialVersionUID = 5L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @NotBlank
    @Size(max = 11)
    private String telephone;

    @NotNull
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate dob;

    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate marriageDate;

    @Transient
    private  Integer age;

    // This allows for unit testing
    public Member(
            Long id,
            String firstname,
            String lastname,
            String email,
            String telephone,
            LocalDate dob,
            LocalDate marriageDate
    ) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.telephone = telephone;
        this.dob = dob;
        this.marriageDate = marriageDate;
    }

    public Integer getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }
}


