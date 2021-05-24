package cells.application.payload.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "Login Request", description = "The login request payload")
public class LoginRequest {

    @NotBlank(message = "Please fill in your username or email")
    @ApiModelProperty(value = "Registered username/email", allowableValues = "NonEmpty String", allowEmptyValue = false)
    private String usernameOrEmail;

    @NotNull(message = "Login password cannot be blank")
    @ApiModelProperty(value = "Valid user password", required = true, allowableValues = "NonEmpty String")
    private String password;

    public LoginRequest(String usernameOrEmail, String email, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }
}
