package cells.service;

import cells.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import cells.exception.AppException;
import cells.model.User;
import cells.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers(){
        return repository.findAll();
    }

    public User getUserById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    public User addUser(User user) {
        Optional<User> studentOptional =
                repository.findByEmail(user.getEmail());
        if (studentOptional.isPresent()){
            throw new IllegalStateException("email already taken");
        }
        return repository.save(user);
    }

    public void deleteUser(Long id) {
        boolean exist = repository.existsById(id);
        if (!exist) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        repository.deleteById(id);
    }

    public User updateUser(User user) {
        return repository.save(user);
    }
}