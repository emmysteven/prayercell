package cells.interfaces;

import cells.model.User;
import cells.payload.request.LoginRequest;
import cells.payload.request.SignupRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {
    User getById(long id);
    List<User> getAll();
    ResponseEntity<?> register(SignupRequest signUpRequest);
    ResponseEntity<?> authenticate(LoginRequest loginRequest);
    User update(User user);
    void delete(long id);
}
