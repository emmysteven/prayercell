package cells.domain.listener;

import cells.application.exception.MailSendException;
import cells.domain.event.AccountChangeEvent;
import cells.domain.entity.User;
import cells.infrastructure.service.MailService;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;

@Slf4j
@Component
public class AccountChangeListener implements ApplicationListener<AccountChangeEvent> {

    private final MailService mailService;

    public AccountChangeListener(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * As soon as a registration event is complete, invoke the email verification
     * asynchronously in an another thread pool
     */
    @Override
    @Async
    public void onApplicationEvent(AccountChangeEvent accountChangeEvent) {
        sendAccountChangeEmail(accountChangeEvent);
    }

    /**
     * Send email verification to the user and persist the token in the database.
     */
    private void sendAccountChangeEmail(AccountChangeEvent event) {
        User user = event.getUser();
        String action = event.getAction();
        String actionStatus = event.getActionStatus();
        String recipientAddress = user.getEmail();

        try {
            mailService.sendAccountChangeEmail(action, actionStatus, recipientAddress);
        } catch (IOException | TemplateException | MessagingException e) {
            log.error(String.valueOf(e));
            throw new MailSendException(recipientAddress, "Account Change Mail");
        }
    }
}
