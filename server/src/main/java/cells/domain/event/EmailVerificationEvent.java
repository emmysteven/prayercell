package cells.domain.event;

import cells.domain.entity.User;
import cells.domain.entity.token.EmailVerificationToken;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

public class EmailVerificationEvent extends ApplicationEvent {

    private User user;
    private transient EmailVerificationToken token;
    private transient UriComponentsBuilder redirectUrl;

    public EmailVerificationEvent(
            User user,
            EmailVerificationToken token,
            UriComponentsBuilder redirectUrl
    ) {
        super(user);
        this.user = user;
        this.token = token;
        this.redirectUrl = redirectUrl;
    }

    public UriComponentsBuilder getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(UriComponentsBuilder redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EmailVerificationToken getToken() {
        return token;
    }

    public void setToken(EmailVerificationToken token) {
        this.token = token;
    }
}
