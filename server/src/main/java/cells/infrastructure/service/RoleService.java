package cells.infrastructure.service;

import cells.domain.entity.Role;
import cells.infrastructure.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Collection<Role> findAll() {
        return (Collection<Role>) roleRepository.findAll();
    }
}
