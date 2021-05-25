package cells.application.config;

import cells.domain.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class EntityAuditor implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        String userName = ((User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getUsername();
        return Optional.of(userName);
    }
}