package com.radioteria.business.events;

import com.radioteria.data.entities.User;
import org.springframework.context.ApplicationEvent;

public class UserConfirmedEvent extends ApplicationEvent {

    private User user;

    public UserConfirmedEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
