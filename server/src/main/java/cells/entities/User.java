package cells.model;

import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Table(name = "users")
public class User extends BaseModel {

    @Column(unique = true, nullable = false, length = 40)
    private String firstName;

    @Column(unique = true, nullable = false, length = 40)
    private String lastName;

    @Column(unique = true, nullable = false, length = 40)
    private String username;

    @Email
    @NaturalId
    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_authority", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "user_id")}, inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "role_id")})
    private Set<Role> roles = new HashSet<>();

    @Size(min = 8, max = 120)
    @Column(nullable = false)
    private String password;

    @Column(name = "is_active", nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private Boolean isEmailVerified;

    public User() {
        super();
    }

    public User(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.roles = user.getRoles();
        this.password = user.getPassword();
        this.active = user.getActive();
        this.isEmailVerified = user.getIsEmailVerified();
    }

    public void addRole(Role role) {
        roles.add(role);
        role.getUserList().add(this);
    }

    public void addRoles(Set<Role> roles) {
        roles.forEach(this::addRole);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.getUserList().remove(this);
    }

    public void markVerificationConfirmed() {
        setEmailVerified(true);
    }

    public Boolean getEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

}
