package com.cps3230assignment.models;

import java.util.ArrayList;
import java.util.List;

public class SystemState {
    private String userId;
    private boolean loggedIn;
    List<Alert> alerts = new ArrayList<Alert>();


    // Getter Methods

    public String getUserId() {
        return userId;
    }

    public boolean getLoggedIn() {
        return loggedIn;
    }

    // Setter Methods

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
