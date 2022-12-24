package com.cps3230assignment.utils;

import com.cps3230assignment.Constants;
import com.cps3230assignment.interfaces.IAlertClient;
import com.cps3230assignment.models.Alert;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class MarketAlertClient implements IAlertClient {

    private final String baseURL = Constants.API_BASE_URL.value();
    //userId should be parsed from an encrypted config file for security,
    //but that is beyond the scope of this assignment.
    private final String userId = Constants.USER_ID.value();
    @Override
    public HttpResponse<JsonNode> postAlert(Alert alert) {
        Unirest.config().defaultBaseUrl(baseURL);
        return Unirest.post("/Alert")
                .header("Content-Type", "application/json")
                .body(alert)
                .asJson();
    }

    @Override
    public HttpResponse<JsonNode> purgeAlerts() {
        Unirest.config().defaultBaseUrl(baseURL);
        return Unirest.delete("/Alert?userId={userId}")
                .routeParam("userId", userId).asJson();
    }

    @Override
    public HttpResponse<JsonNode> getEventsLog() {
        Unirest.config().defaultBaseUrl(baseURL);
        String endpoint = String.format("/EventsLog/%s", Constants.USER_ID.value());
        return Unirest.get(endpoint)
                .asJson();
    }
}
