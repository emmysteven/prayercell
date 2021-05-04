package cells.model;

import cells.model.common.BaseModel;
import cells.model.common.Roles;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseModel {

    @Column(unique = true, nullable = false, length = 40)
    private String name;

    @Column(unique = true, nullable = false, length = 40)
    private String username;

    @Email
    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Roles role;

    @Size(min = 8, max = 120)
    @Column(nullable = false)
    private String password;

    public User(String name, String username, String email, Roles role, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.role = role;
        this.password = password;
    }
}
