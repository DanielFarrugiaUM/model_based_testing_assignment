package com.cps3230assignment.models;

public class MarketAlertEvent {
    private String id;
    private String timestamp;
    private int eventLogType;
    private String userId;
    private SystemState systemState;


    // Getter Methods

    public String getId() {
        return id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getEventLogType() {
        return eventLogType;
    }

    public String getUserId() {
        return userId;
    }

    public SystemState getSystemState() {
        return systemState;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setEventLogType(int eventLogType) {
        this.eventLogType = eventLogType;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setSystemState(SystemState systemStateObject) {
        this.systemState = systemStateObject;
    }
}
