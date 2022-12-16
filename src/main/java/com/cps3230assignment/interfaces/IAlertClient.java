package com.cps3230assignment.interfaces;

import com.cps3230assignment.models.Alert;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;

public interface IAlertClient {
    public HttpResponse<JsonNode> postAlert(Alert alert);
    public HttpResponse<JsonNode> purgeAlerts();
    public HttpResponse<JsonNode> getEventsLog();
}
