package cells.security;

import cells.exception.ResourceNotFoundException;
import cells.entities.CustomUserDetails;
import cells.entities.User;
import cells.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by emmysteven on 02/08/17.
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    //Map userDatabase object to SpringSecurity DetailService object (by email==username !)
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Optional<User> dbUser = repository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        return dbUser.map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail));
    }

    //Map userDatabase object to SpringSecurity DetailService object (by Id)
    @Transactional
    public UserDetails loadUserById(Long id) {
        Optional<User> dbUser = repository.findById(id);
        return dbUser.map(CustomUserDetails::new)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }
}
