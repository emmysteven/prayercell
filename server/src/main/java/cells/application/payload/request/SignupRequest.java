package cells.application.payload.request;

import cells.application.validation.annotation.NullOrNotBlank;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@ApiModel(value = "Registration Request", description = "The registration request payload")
public class SignupRequest {

    @NullOrNotBlank(message = "firstname can be null but not blank")
    @ApiModelProperty(value = "A valid first", allowableValues = "NonEmpty String")
    private String firstname;

    @NullOrNotBlank(message = "Lastname can be null but not blank")
    @ApiModelProperty(value = "A valid lastname", allowableValues = "NonEmpty String")
    private String lastname;

    @NullOrNotBlank(message = "username can be null but not blank")
    @ApiModelProperty(value = "A valid username", allowableValues = "NonEmpty String")
    private String username;

    @NullOrNotBlank(message = "Registration email can be null but not blank")
    @ApiModelProperty(value = "A valid email", required = true, allowableValues = "NonEmpty String")
    private String email;

    @NotNull(message = "Registration password cannot be null")
    @ApiModelProperty(value = "A valid password string", required = true, allowableValues = "NonEmpty String")
    private String password;

    @NotNull(message = "Specify whether the user has to be registered as an admin or not")
    @ApiModelProperty(required = true, dataType = "boolean", allowableValues = "true, false",
            value = "Flag denoting whether the user is an admin or not")
    private Boolean registerAsAdmin;

    public SignupRequest(
            String firstname,
            String lastname,
            String username,
            String email,
            String password,
            Boolean registerAsAdmin
    ) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.registerAsAdmin = registerAsAdmin;
    }
}
