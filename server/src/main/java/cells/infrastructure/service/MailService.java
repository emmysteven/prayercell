package cells.infrastructure.service;

import cells.application.payload.request.MailRequest;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    private final Configuration templateConfiguration;

    @Value("${app.velocity.templates.location}")
    private String basePackagePath;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Value("${app.token.password.reset.duration}")
    private Long expiration;


    public MailService(JavaMailSender mailSender, Configuration templateConfiguration) {
        this.mailSender = mailSender;
        this.templateConfiguration = templateConfiguration;
    }

    public void sendEmailVerification(String emailVerificationUrl, String to)
            throws IOException, TemplateException, MessagingException {
        MailRequest mailRequest = new MailRequest();
        mailRequest.setSubject("Email Verification [RCNLagos]");
        mailRequest.setTo(to);
        mailRequest.setFrom(mailFrom);
        mailRequest.getModel().put("userName", to);
        mailRequest.getModel().put("emailVerificationUrl", emailVerificationUrl);

        templateConfiguration.setClassForTemplateLoading(getClass(), basePackagePath);
        Template template = templateConfiguration.getTemplate("email-verification.ftl");
        String mailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailRequest.getModel());
        mailRequest.setContent(mailContent);
        send(mailRequest);
    }

    /**
     * Setting the mail parameters.Send the reset link to the respective user's mail
     */
    public void sendResetLink(String resetPasswordLink, String to)
            throws IOException, TemplateException, MessagingException {
        Long expirationInMinutes = TimeUnit.MILLISECONDS.toMinutes(expiration);
        String expirationInMinutesString = expirationInMinutes.toString();
        MailRequest mailRequest = new MailRequest();
        mailRequest.setSubject("Password Reset Link [RCNLagos]");
        mailRequest.setTo(to);
        mailRequest.setFrom(mailFrom);
        mailRequest.getModel().put("userName", to);
        mailRequest.getModel().put("resetPasswordLink", resetPasswordLink);
        mailRequest.getModel().put("expirationTime", expirationInMinutesString);

        templateConfiguration.setClassForTemplateLoading(getClass(), basePackagePath);
        Template template = templateConfiguration.getTemplate("reset-link.ftl");
        String mailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailRequest.getModel());
        mailRequest.setContent(mailContent);
        send(mailRequest);
    }

    /**
     * Send an email to the user indicating an account change event with the correct
     * status
     */
    public void sendAccountChangeEmail(String action, String actionStatus, String to)
            throws IOException, TemplateException, MessagingException {
        MailRequest mailRequest = new MailRequest();
        mailRequest.setSubject("Account Status Change [RCNLagos]");
        mailRequest.setTo(to);
        mailRequest.setFrom(mailFrom);
        mailRequest.getModel().put("userName", to);
        mailRequest.getModel().put("action", action);
        mailRequest.getModel().put("actionStatus", actionStatus);

        templateConfiguration.setClassForTemplateLoading(getClass(), basePackagePath);
        Template template = templateConfiguration.getTemplate("account-activity-change.ftl");
        String mailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailRequest.getModel());
        mailRequest.setContent(mailContent);
        send(mailRequest);
    }

    /**
     * Sends a simple mail as a MIME Multipart message
     */
    public void send(MailRequest mailRequest) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        helper.setTo(mailRequest.getTo());
        helper.setText(mailRequest.getContent(), true);
        helper.setSubject(mailRequest.getSubject());
        helper.setFrom(mailRequest.getFrom());
        mailSender.send(message);
    }

}
