package cells.domain.entity;

import cells.application.validation.annotation.NullOrNotBlank;
import cells.domain.entity.common.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity(name = "users")
public class User extends BaseEntity {

    @NaturalId
    @Email
    @Column(unique = true)
    @NotBlank(message = "User email cannot be null")
    private String email;

    @Column(unique = true)
    @NullOrNotBlank(message = "Username can not be blank")
    private String username;

    @NotNull(message = "Password cannot be null")
    private String password;

    @NullOrNotBlank(message = "First name can not be blank")
    private String firstName;

    @NullOrNotBlank(message = "Last name can not be blank")
    private String lastName;

    @Column(nullable = false)
    private Boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_authority", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles = new HashSet<>();

    @Column(nullable = false)
    private Boolean isEmailVerified;

    public User() {
        super();
    }

    public User(User user) {
        username = user.getUsername();
        password = user.getPassword();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();
        isActive = user.getIsActive();
        roles = user.getRoles();
        isEmailVerified = user.getEmailVerified();
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