package cells.application.payload.request;

import cells.domain.enums.Roles;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Roles role;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    @NotNull(message = "Specify whether the user has to be registered as an admin or not")
    @ApiModelProperty(
            required = true,
            dataType = "boolean",
            allowableValues = "true, false",
            value = "Flag denoting whether the user is an admin or not"
    )
    private Boolean registerAsAdmin;
}

