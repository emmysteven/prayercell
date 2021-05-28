package cells.domain.listener;

import cells.application.exception.MailSendException;
import cells.domain.entity.User;
import cells.domain.event.RegistrationEvent;
import cells.infrastructure.service.EmailVerificationService;
import cells.infrastructure.service.MailService;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;

@Slf4j
@Component
public class RegistrationListener implements ApplicationListener<RegistrationEvent> {

    private final EmailVerificationService emailVerificationService;
    private final MailService mailService;

    public RegistrationListener(
            EmailVerificationService emailVerificationService,
            MailService mailService
    ) {
        this.emailVerificationService = emailVerificationService;
        this.mailService = mailService;
    }

    /**
     * As soon as a registration event is complete, invoke the email verification
     * asynchronously in an another thread pool
     */
    @Override
    @Async
    public void onApplicationEvent(RegistrationEvent registrationEvent) {
        sendEmailVerification(registrationEvent);
    }

    /**
     * Send email verification to the user and persist the token in the database.
     */
    private void sendEmailVerification(RegistrationEvent event) {
        User user = event.getUser();
        String token = emailVerificationService.generateNewToken();
        emailVerificationService.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String emailConfirmationUrl = event.getRedirectUrl().queryParam("token", token).toUriString();

        try {
            log.info("Email Verification Link: " + emailConfirmationUrl);
            mailService.sendEmailVerification(emailConfirmationUrl, recipientAddress);
        } catch (IOException | TemplateException | MessagingException e) {
            log.error(String.valueOf(e));
            throw new MailSendException(recipientAddress, "Email Verification");
        }
    }
}
