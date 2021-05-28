package cells.domain.listener;

import cells.application.exception.MailSendException;
import cells.domain.event.EmailVerificationEvent;
import cells.domain.entity.User;
import cells.domain.entity.token.EmailVerificationToken;
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
public class EmailVerificationListener implements ApplicationListener<EmailVerificationEvent> {

    private final MailService mailService;

    public EmailVerificationListener(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * As soon as a registration event is complete, invoke the email verification
     */
    @Override
    @Async
    public void onApplicationEvent(EmailVerificationEvent emailVerificationEvent) {
        resendEmailVerification(emailVerificationEvent);
    }

    /**
     * Send email verification to the user and persist the token in the database.
     */
    private void resendEmailVerification(EmailVerificationEvent event) {
        User user = event.getUser();
        EmailVerificationToken emailVerificationToken = event.getToken();
        String recipientAddress = user.getEmail();

        String emailConfirmationUrl =
                event.getRedirectUrl().queryParam("token", emailVerificationToken.getToken()).toUriString();
        try {

            mailService.sendEmailVerification(emailConfirmationUrl, recipientAddress);
        } catch (IOException | TemplateException | MessagingException e) {
            log.error(String.valueOf(e));
            throw new MailSendException(recipientAddress, "Email Verification");
        }
    }

}
