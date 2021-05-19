package cells.infrastructure.services;

import cells.entities.Role;
import cells.persistence.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Collection<Role> findAll() {
        return roleRepository.findAll();
    }
}
