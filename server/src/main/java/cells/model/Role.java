package cells.model;

import cells.model.common.BaseModel;
import cells.model.common.Roles;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "roles")
public class Role extends BaseModel {

    @NaturalId
    @Column(length = 60)
    @Enumerated(EnumType.STRING)
    private Roles name;

    public Role(Roles name) {
        this.name = name;
    }

}

