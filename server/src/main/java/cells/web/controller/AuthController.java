package cells.web.controller;

import cells.application.exception.*;
import cells.application.payload.request.*;
import cells.application.payload.response.ApiResponse;
import cells.domain.event.ResetLinkEvent;
import cells.domain.event.EmailVerificationEvent;
import cells.domain.event.AccountChangeEvent;
import cells.domain.event.RegistrationEvent;
import cells.domain.entity.CustomUserDetails;
import cells.domain.entity.token.EmailVerificationToken;
import cells.domain.entity.token.RefreshToken;
import cells.infrastructure.security.JwtUtil;
import cells.infrastructure.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/auth")
@Tag(name = "Authorization Rest API", description = "Defines endpoints that can be hit only when the user is not logged in. It's not secured by default.")
public class AuthController {
    private final JwtUtil jwtUtil;
    private final AuthService authService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public AuthController(
            JwtUtil jwtUtil,
            AuthService authService,
            ApplicationEventPublisher applicationEventPublisher
    ) {
        this.jwtUtil = jwtUtil;
        this.authService = authService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Checks is a given email is in use or not.
     */
    @Operation(summary = "Checks if the given email is in use")
    @GetMapping("/check_email")
    public ResponseEntity checkEmailInUse(
            @Parameter(description = "Email id to check against")
            @RequestParam("email") String email
    ) {
        Boolean emailExists = authService.emailAlreadyExists(email);
        return ResponseEntity.ok(new ApiResponse(emailExists.toString(), true));
    }

    /**
     * Checks is a given username is in use or not.
     */
    @Operation(summary = "Checks if the given username is in use")
    @GetMapping("/check_username")
    public ResponseEntity checkUsernameInUse(
            @Parameter(description = "Username to check against")
            @RequestParam("username") String username
    ) {
        Boolean usernameExists = authService.usernameAlreadyExists(username);
        return ResponseEntity.ok(new ApiResponse(usernameExists.toString(), true));
    }


    /**
     * Entry point for the user log in. Return the jwt auth token and the refresh token
     */
    @PostMapping("/login")
    @Operation(summary = "Logs the user in to the system and return the auth tokens")
    public ResponseEntity<?> authenticateUser(
            @Parameter(description = "The LoginRequest payload")
            @Valid @RequestBody LoginRequest loginRequest
    ) {
        return authService.authenticateUser(loginRequest);
    }

    /**
     * Entry point for the user registration process. On successful registration,
     * publish an event to generate email verification token
     */
    @PostMapping("/signup")
    @Operation(summary = "Registers the user and publishes an event to generate the email verification")
    public ResponseEntity registerUser(
            @Parameter(description = "The RegistrationRequest payload")
            @Valid @RequestBody SignupRequest signupRequest
    ) {
        return authService.registerUser(signupRequest)
                .map(user -> {
                    UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/auth/verify_email");

                    RegistrationEvent registrationEvent = new RegistrationEvent(user, urlBuilder);
                    applicationEventPublisher.publishEvent(registrationEvent);
                    log.info("Registered User returned [API[: " + user);
                    return ResponseEntity.ok(new ApiResponse(
                            "User registered successfully. Check your email for verification",
                            true)
                    );
                })
                .orElseThrow(() -> new SignupException(signupRequest.getEmail(), "Missing user object in database"));
    }

    /**
     * Receives the reset link request and publishes an event to send email id containing
     * the reset link if the request is valid. In future the deeplink should open within
     * the app itself.
     */
    @PostMapping("/password/resetlink")
    @Operation(summary = "Receive the reset link request and publish event to send mail containing the password " +
            "reset link")
    public ResponseEntity resetLink(
            @Parameter(description = "The PasswordResetLinkRequest payload")
            @Valid @RequestBody PasswordResetLinkRequest passwordResetLinkRequest
    ) {
        return authService.generatePasswordResetToken(passwordResetLinkRequest)
                .map(passwordResetToken -> {
                    UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/password/reset");
                    ResetLinkEvent generateResetLinkMailEvent = new ResetLinkEvent(passwordResetToken,
                            urlBuilder);
                    applicationEventPublisher.publishEvent(generateResetLinkMailEvent);
                    return ResponseEntity.ok(new ApiResponse("Password reset link sent successfully", true));
                })
                .orElseThrow(() -> new PasswordResetLinkException(
                        passwordResetLinkRequest.getEmail(),
                        "Couldn't create a valid token")
                );
    }

    /**
     * Receives a new passwordResetRequest and sends the acknowledgement after
     * changing the password to the user's mail through the event.
     */

    @PostMapping("/password/reset")
    @Operation(summary = "Reset the password after verification and publish an event to send the acknowledgement " +
            "email")
    public ResponseEntity resetPassword(
            @Parameter(description = "The PasswordResetRequest payload")
            @Valid @RequestBody PasswordResetRequest passwordResetRequest
    ) {
        return authService.resetPassword(passwordResetRequest)
                .map(changedUser -> {
                    AccountChangeEvent onPasswordChangeEvent = new AccountChangeEvent(changedUser, "Reset Password",
                            "Changed Successfully");
                    applicationEventPublisher.publishEvent(onPasswordChangeEvent);
                    return ResponseEntity.ok(new ApiResponse("Password changed successfully", true));
                })
                .orElseThrow(() -> new PasswordResetException(
                        passwordResetRequest.getToken(),
                        "Error in resetting password")
                );
    }

    /**
     * Confirm the email verification token generated for the user during
     * registration. If token is invalid or token is expired, report error.
     */
    @GetMapping("/verify_email")
    @Operation(summary = "Confirms the email verification token that has been generated for the user during registration")
    public ResponseEntity confirmRegistration(
            @Parameter(description = "the token that was sent to the user email")
            @RequestParam("token") String token
    ) {
        return authService.confirmEmailRegistration(token)
                .map(user -> ResponseEntity.ok(new ApiResponse("User verified successfully", true)))
                .orElseThrow(() -> new InvalidTokenRequestException(
                        "Email Verification Token",
                        token,
                        "Failed to confirm. Please generate a new email verification request")
                );
    }

    /**
     * Resend the email registration mail with an updated token expiry. Safe to
     * assume that the user would always click on the last re-verification email and
     * any attempts at generating new token from past (possibly archived/deleted)
     * tokens should fail and report an exception.
     */
    @GetMapping("/resendRegistrationToken")
    @Operation(summary = "Resend the email registration with an updated token expiry. Safe to " +
            "assume that the user would always click on the last re-verification email and " +
            "any attempts at generating new token from past (possibly archived/deleted)" +
            "tokens should fail and report an exception. ")
    public ResponseEntity resendRegistrationToken(
            @Parameter(description = "the initial token that was sent to the u ser email after registration")
            @RequestParam("token") String existingToken
    ) {
        EmailVerificationToken newEmailToken = authService.recreateRegistrationToken(existingToken)
                .orElseThrow(() -> new InvalidTokenRequestException(
                        "Email Verification Token",
                        existingToken,
                        "User is already registered. No need to re-generate token")
                );

        return Optional.ofNullable(newEmailToken.getUser())
                .map(registeredUser -> {
                    UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/auth/verify_email");

                    EmailVerificationEvent emailVerificationEvent = new EmailVerificationEvent(
                            registeredUser,
                            newEmailToken,
                            urlBuilder
                    );
                    applicationEventPublisher.publishEvent(emailVerificationEvent);
                    return ResponseEntity.ok(new ApiResponse("Email verification resent successfully", true));
                })
                .orElseThrow(() -> new InvalidTokenRequestException(
                        "Email Verification Token",
                        existingToken,
                        "No user associated with this request. Re-verification denied")
                );
    }

    /**
     * Refresh the expired jwt token using a refresh token for the specific device
     * and return a new token to the caller
     */
    @PostMapping("/refresh")
    @Operation(summary = "Refresh the expired jwt authentication by issuing a token refresh request and returns the" +
            "updated response tokens")
    public ResponseEntity refreshJwtToken(
            @Parameter(description = "The TokenRefreshRequest payload")
            @Valid @RequestBody TokenRefreshRequest tokenRefreshRequest
    ) {
        return authService.refreshJwtToken(tokenRefreshRequest)
                .map(updatedToken -> {
                    String refreshToken = tokenRefreshRequest.getRefreshToken();
                    log.info("Created new Jwt Auth token: " + updatedToken);
                    return ResponseEntity.ok(new JwtAuthenticationResponse(
                            updatedToken,
                            refreshToken,
                            jwtUtil.getExpiryDuration())
                    );
                })
                .orElseThrow(() -> new TokenRefreshException(
                        tokenRefreshRequest.getRefreshToken(),
                        "Unexpected error during token refresh. Please logout and login again.")
                );
    }
}

