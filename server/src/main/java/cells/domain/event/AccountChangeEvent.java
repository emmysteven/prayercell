package cells.domain.event;


import cells.domain.entity.User;
import org.springframework.context.ApplicationEvent;

public class AccountChangeEvent extends ApplicationEvent {

    private User user;
    private String action;
    private String actionStatus;

    public AccountChangeEvent(User user, String action, String actionStatus) {
        super(user);
        this.user = user;
        this.action = action;
        this.actionStatus = actionStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(String actionStatus) {
        this.actionStatus = actionStatus;
    }
}
