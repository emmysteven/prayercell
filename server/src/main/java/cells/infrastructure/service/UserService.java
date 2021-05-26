package cells.infrastructure.service;

import cells.application.payload.request.SignupRequest;
import cells.domain.entity.CustomUserDetails;
import cells.domain.entity.Role;
import cells.domain.entity.User;
import cells.infrastructure.repository.UserRepository;
import cells.infrastructure.security.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final RefreshTokenService refreshTokenService;

    public UserService(
            PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            RoleService roleService,
            RefreshTokenService refreshTokenService
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.refreshTokenService = refreshTokenService;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long Id) {
        return userRepository.findById(Id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public User createUser(SignupRequest signupRequest) {
        User newUser = new User();
        newUser.setIsActive(true);
        newUser.setEmailVerified(false);
        newUser.setFirstname(signupRequest.getFirstname());
        newUser.setLastname(signupRequest.getLastname());
        newUser.setUsername(signupRequest.getUsername());
        newUser.setEmail(signupRequest.getEmail());
        Boolean isNewUserAsAdmin = signupRequest.getRegisterAsAdmin();
        newUser.addRoles(getRolesForNewUser(isNewUserAsAdmin));
        newUser.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        return newUser;
    }

    /**
     * Performs a quick check to see what roles the new user could be assigned to.
     *
     * @return list of roles for the new user
     */
    private Set<Role> getRolesForNewUser(Boolean isToBeMadeAdmin) {
        Set<Role> newUserRoles = new HashSet<>(roleService.findAll());
        if (!isToBeMadeAdmin) {
            newUserRoles.removeIf(Role::isAdminRole);
        }
        log.info("Setting user roles: " + newUserRoles);
        return newUserRoles;
    }

    /**
     * Log the given user out and delete the refresh token associated with it. If no device
     * id is found matching the database for the given user, throw a log out exception.
     */
    public void logoutUser(@CurrentUser CustomUserDetails currentUser) {

        log.info("Removing refresh token");
       // refreshTokenService.deleteById(userDevice.getRefreshToken().getId());
    }
}