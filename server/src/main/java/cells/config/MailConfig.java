package cells.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("app.mail")
public class MailConfig {
    public String emailFrom;
    public String smtpHost;
    public int smtpPort;
    public String smtpUser;
    public String smtpPass;
    public String displayName;
}
