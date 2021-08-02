package cells.web.controller;

import cells.application.exception.UpdatePasswordException;
import cells.application.payload.request.UpdatePasswordRequest;
import cells.application.payload.response.ApiResponse;
import cells.domain.entity.Member;
import cells.domain.entity.User;
import cells.domain.event.AccountChangeEvent;
import cells.domain.entity.CustomUserDetails;
import cells.infrastructure.security.CurrentUser;
import cells.infrastructure.service.AuthService;
import cells.infrastructure.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Rest API", description = "Defines endpoints for the logged in user. It's secured by default")

@Slf4j
public class UserController {
    private final AuthService authService;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    public UserController(
            AuthService authService,
            UserService userService,
            ApplicationEventPublisher eventPublisher
    ) {
        this.authService = authService;
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Gets the current user profile of the logged in user
     */
    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Returns the current user profile")
    public ResponseEntity getUserProfile(@CurrentUser CustomUserDetails currentUser) {
        log.info(currentUser.getEmail() + " has role: " + currentUser.getRoles());
        return ResponseEntity.ok("Hello. This is about me");
    }

    /**
     * Returns all admins in the system. Requires Admin access
     */
    @GetMapping("/admins")
//    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Returns the list of configured admins. Requires ADMIN Access")
    public ResponseEntity<List<User>> getAllAdmins() {
        log.info("Inside secured resource with admin");
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
//        return ResponseEntity.ok("Hello. This is about admins");
    }

    /**
     * Updates the password of the current logged in user
     */
    @PostMapping("/password/update")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Allows the user to change his password once logged in by supplying the correct current " +
            "password")
    public ResponseEntity updateUserPassword(
            @CurrentUser CustomUserDetails customUserDetails,
            @Parameter(description = "The UpdatePasswordRequest payload")
            @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest
    ) {
        return authService.updatePassword(customUserDetails, updatePasswordRequest)
                .map(updatedUser -> {
                    AccountChangeEvent accountChangeEvent = new AccountChangeEvent(
                            updatedUser,
                            "Update Password",
                            "Change successful"
                    );
                    eventPublisher.publishEvent(accountChangeEvent);
                    return ResponseEntity.ok(new ApiResponse("Password changed successfully", true));
                })
                .orElseThrow(() -> new UpdatePasswordException("--Empty--", "No such user present."));
    }

    /**
     * Log the user out from the app/device. Release the refresh token associated with the
     * user device.
     */
    @PostMapping("/logout")
    @Operation(summary = "Logs the specified user device and clears the refresh tokens associated with it")
    // TODO: Work on this later
    public ResponseEntity logoutUser(@CurrentUser CustomUserDetails customUserDetails) {
        return ResponseEntity.ok(new ApiResponse("Log out successful", true));
    }
}
