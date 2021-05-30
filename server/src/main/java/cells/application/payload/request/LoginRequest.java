package cells.application.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(title = "Login Request", description = "The login request payload")
public class LoginRequest {

    @NotBlank(message = "Please fill in your username or email")
    @Schema(title = "Registered username/email", allowableValues = "NonEmpty String")
    private String usernameOrEmail;

    @NotNull(message = "Login password cannot be blank")
    @Schema(description = "Valid user password", required = true, allowableValues = "NonEmpty String")
    private String password;

    public LoginRequest(String usernameOrEmail, String email, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }
}
