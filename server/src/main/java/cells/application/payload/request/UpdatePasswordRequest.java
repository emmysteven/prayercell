package cells.application.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

@Schema(name = "Update password Request", description = "The update password request payload")
public class UpdatePasswordRequest {

    @NotBlank(message = "Old password must not be blank")
    @Schema(description = "Valid current user password", required = true, allowableValues = "NonEmpty String")
    private String oldPassword;

    @NotBlank(message = "New password must not be blank")
    @Schema(description = "Valid new password string", required = true, allowableValues = "NonEmpty String")
    private String newPassword;

    public UpdatePasswordRequest(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public UpdatePasswordRequest() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
