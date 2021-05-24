package cells.domain.entity;

import cells.domain.entity.common.BaseEntity;
import cells.domain.enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Role. Defines the role and the list of users who are associated with that role
 */
@Data
@Entity
public class Role extends BaseEntity {

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    @NaturalId
    private Roles role;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> userList = new HashSet<>();

    public boolean isAdminRole() {
        return null != this && this.role.equals(Roles.ADMIN);
    }

}
