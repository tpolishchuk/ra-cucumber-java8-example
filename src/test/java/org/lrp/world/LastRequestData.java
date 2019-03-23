package org.lrp.world;

import demo_app.domain.User;

public class LastRequestData {

    private Object requestEntity;
    private User lastCreatedUser;

    public Object getRequestEntity() {
        return requestEntity;
    }

    public void setRequestEntity(Object requestEntity) {
        this.requestEntity = requestEntity;
    }

    public User getLastCreatedUser() {
        return lastCreatedUser;
    }

    public void setLastCreatedUser(User lastCreatedUser) {
        this.lastCreatedUser = lastCreatedUser;
    }
}
