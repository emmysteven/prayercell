package cells.payload.request;

import lombok.*;
import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;
}
