package cells.payload.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignupRequest {
    @NotBlank
    @Size(min = 4, max = 40)
    private String name;

    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @Email
    @NotBlank
    @Size(max = 40)
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
}

