package com.cps3230assignment.models;

public enum EventLogType {
    ALERT_CREATED (0),
    ALERTS_DELETED (1),
    USER_VALID_LOGIN(5),
    USER_LOGGED_OUT(6),
    USER_VIEWED_ALERTS(7);

    private final int value;

    EventLogType(int value){
        this.value = value;
    }

    public int value() { return value; }
}
