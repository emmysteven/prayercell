package cells.application.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

@Schema(name = "Password reset link request", description = "The password reset link payload")
public class PasswordResetLinkRequest {

    @NotBlank(message = "Email cannot be blank")
    @Schema(description = "User registered email", required = true, allowableValues = "NonEmpty String")
    private String email;

    public PasswordResetLinkRequest(String email) {
        this.email = email;
    }

    public PasswordResetLinkRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
