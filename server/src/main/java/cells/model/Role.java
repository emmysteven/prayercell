package cells.model;

import cells.model.common.BaseModel;
import cells.model.common.Roles;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "roles")
public class Role extends BaseModel {

    @NaturalId
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Roles name;

}