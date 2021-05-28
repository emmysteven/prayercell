package cells.infrastructure.service;


import cells.application.exception.*;
import cells.application.payload.request.*;
import cells.domain.entity.CustomUserDetails;
import cells.domain.entity.PasswordResetToken;
import cells.domain.entity.User;
import cells.domain.entity.token.EmailVerificationToken;
import cells.domain.entity.token.RefreshToken;
import cells.infrastructure.security.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final ResetPasswordService resetPasswordService;
    private final AuthenticationManager authenticationManager;
    private final EmailVerificationService emailVerificationService;

    public AuthService(
            JwtUtil jwtUtil,
            UserService userService,
            PasswordEncoder passwordEncoder,
            RefreshTokenService refreshTokenService,
            ResetPasswordService resetPasswordService,
            AuthenticationManager authenticationManager,
            EmailVerificationService emailVerificationService
    ) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
        this.resetPasswordService = resetPasswordService;
        this.authenticationManager = authenticationManager;
        this.emailVerificationService = emailVerificationService;
    }

    /**
     * Registers a new user in the database by performing a series of quick checks.
     *
     * @return A user object if successfully created
     */
    public Optional<User> registerUser(SignupRequest signupRequest) {
        String email = signupRequest.getEmail();
        if (emailAlreadyExists(email)) {
            log.error("Email already exists: " + email);
            throw new ResourceAlreadyInUseException("Email", "Address", email);
        }
        log.info("Trying to register new user [" + email + "]");
        User newUser = userService.createUser(signupRequest);
        User registeredNewUser = userService.save(newUser);
        return Optional.ofNullable(registeredNewUser);
    }

    /**
     * Checks if the given email already exists in the database repository or not
     * @return true if the email exists else false
     */
    public Boolean emailAlreadyExists(String email) {
        return userService.existsByEmail(email);
    }

    /**
     * Checks if the given email already exists in the database repository or not
     * @return true if the email exists else false
     */
    public Boolean usernameAlreadyExists(String username) {
        return userService.existsByUsername(username);
    }

    /**
     * Authenticate user and log them in given a loginRequest
     */
    public Optional<Authentication> authenticateUser(LoginRequest loginRequest) {
        return Optional.ofNullable(
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsernameOrEmail(),
                                loginRequest.getPassword()
                        )
                )
        );
    }

    /**
     * Confirms the user verification based on the token expiry and mark the user as active.
     * If user is already verified, save the unnecessary database calls.
     */
    public Optional<User> confirmEmailRegistration(String emailToken) {
        EmailVerificationToken emailVerificationToken = emailVerificationService.findByToken(emailToken)
                .orElseThrow(() -> new ResourceNotFoundException("Token", "Email verification", emailToken));

        User registeredUser = emailVerificationToken.getUser();
        if (registeredUser.getEmailVerified()) {
            log.info("User [" + emailToken + "] already registered.");
            return Optional.of(registeredUser);
        }

        emailVerificationService.verifyExpiration(emailVerificationToken);
        emailVerificationToken.setConfirmedStatus();
        emailVerificationService.save(emailVerificationToken);

        registeredUser.markVerificationConfirmed();
        userService.save(registeredUser);
        return Optional.of(registeredUser);
    }

    /**
     * Attempt to regenerate a new email verification token given a valid
     * previous expired token. If the previous token is valid, increase its expiry
     * else update the token value and add a new expiration.
     */
    public Optional<EmailVerificationToken> recreateRegistrationToken(String existingToken) {
        EmailVerificationToken emailVerificationToken = emailVerificationService.findByToken(existingToken)
                .orElseThrow(() -> new ResourceNotFoundException("Token", "Existing email verification", existingToken));

        if (emailVerificationToken.getUser().getEmailVerified()) {
            return Optional.empty();
        }
        return Optional.ofNullable(emailVerificationService.updateExistingTokenWithNameAndExpiry(emailVerificationToken));
    }

    /**
     * Validates the password of the current logged in user with the given password
     */
    private Boolean currentPasswordMatches(User currentUser, String password) {
        return passwordEncoder.matches(password, currentUser.getPassword());
    }

    /**
     * Updates the password of the current logged in user
     */
    public Optional<User> updatePassword(
            CustomUserDetails customUserDetails,
            UpdatePasswordRequest updatePasswordRequest
    ) {
        String email = customUserDetails.getEmail();
        User currentUser = userService.findByEmail(email)
                .orElseThrow(() -> new UpdatePasswordException(email, "No matching user found"));

        if (!currentPasswordMatches(currentUser, updatePasswordRequest.getOldPassword())) {
            log.info("Current password is invalid for [" + currentUser.getPassword() + "]");
            throw new UpdatePasswordException(currentUser.getEmail(), "Invalid current password");
        }
        String newPassword = passwordEncoder.encode(updatePasswordRequest.getNewPassword());
        currentUser.setPassword(newPassword);
        userService.save(currentUser);
        return Optional.of(currentUser);
    }

    /**
     * Generates a JWT token for the validated client
     */
    public String generateToken(CustomUserDetails customUserDetails) {
        return jwtUtil.generateToken(customUserDetails);
    }

    /**
     * Generates a JWT token for the validated client by userId
     */
    private String generateTokenFromUserId(Long userId) {
        return jwtUtil.generateTokenFromUserId(userId);
    }

    /**
     * Creates and persists the refresh token for the user device. If device exists
     * already, we don't care. Unused devices with expired tokens should be cleaned
     * with a cron job. The generated token would be encapsulated within the jwt.
     * Remove the existing refresh token as the old one should not remain valid.
     */
    public Optional<RefreshToken> createAndPersistRefreshToken(Authentication authentication, LoginRequest loginRequest) {
        User currentUser = (User) authentication.getPrincipal();
        //if successful login -> remove old refresh token
//        userDeviceService.findByUserId(currentUser.getId())
//                .map(UserDevice::getRefreshToken)
//                .map(RefreshToken::getId)
//                .ifPresent(refreshTokenService::deleteById);

        //Save new refreshToken
        RefreshToken refreshToken = refreshTokenService.createRefreshToken();
        refreshToken = refreshTokenService.save(refreshToken);
        return Optional.ofNullable(refreshToken);
    }

    /**
     * Refresh the expired jwt token using a refresh token and device info. The
     * * refresh token is mapped to a specific device and if it is unexpired, can help
     * * generate a new jwt. If the refresh token is inactive for a device or it is expired,
     * * throw appropriate errors.
     */
//    public Optional<String> refreshJwtToken(TokenRefreshRequest tokenRefreshRequest) {
//        String requestRefreshToken = tokenRefreshRequest.getRefreshToken();
//
//        return Optional.of(refreshTokenService.findByToken(requestRefreshToken)
//                .map(refreshToken -> {
//                    refreshTokenService.verifyExpiration(refreshToken);
//                    refreshTokenService.increaseCount(refreshToken);
//                    return refreshToken;
//                });
//    }

    /**
     * Generates a password reset token from the given reset request
     */
    public Optional<PasswordResetToken> generatePasswordResetToken(PasswordResetLinkRequest passwordResetLinkRequest) {
        String email = passwordResetLinkRequest.getEmail();
        return userService.findByEmail(email)
                .map(user -> {
                    PasswordResetToken passwordResetToken = resetPasswordService.createToken();
                    passwordResetToken.setUser(user);
                    resetPasswordService.save(passwordResetToken);
                    return Optional.of(passwordResetToken);
                })
                .orElseThrow(() -> new PasswordResetLinkException(
                        email,
                        "No matching user found for the given request")
                );
    }

    /**
     * Reset a password given a reset request and return the updated user
     */
    public Optional<User> resetPassword(PasswordResetRequest passwordResetRequest) {
        String token = passwordResetRequest.getToken();
        PasswordResetToken passwordResetToken = resetPasswordService
                .findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Password Reset Token", "Token Id", token));

        resetPasswordService.verifyExpiration(passwordResetToken);
        final String encodedPassword = passwordEncoder.encode(passwordResetRequest.getPassword());

        return Optional.of(passwordResetToken)
                .map(PasswordResetToken::getUser)
                .map(user -> {
                    user.setPassword(encodedPassword);
                    userService.save(user);
                    return user;
                });
    }
}
