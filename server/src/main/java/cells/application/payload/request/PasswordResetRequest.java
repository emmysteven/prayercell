package cells.application.payload.request;

import cells.application.validation.annotation.MatchPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@MatchPassword
@Schema(name = "Password reset Request", description = "The password reset request payload")
public class PasswordResetRequest {

    @NotBlank(message = "Password cannot be blank")
    @Schema(description = "New user password", required = true, allowableValues = "NonEmpty String")
    private String password;

    @NotBlank(message = "Confirm Password cannot be blank")
    @Schema(description = "Must match the new user password. Else exception will be thrown", required = true,
            allowableValues = "NonEmpty String matching the password")
    private String confirmPassword;

    @NotBlank(message = "Token has to be supplied along with a password reset request")
    @Schema(description = "Reset token received in mail", required = true, allowableValues = "NonEmpty String")
    private String token;

    public PasswordResetRequest(String password, String confirmPassword, String token) {
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.token = token;
    }
}
