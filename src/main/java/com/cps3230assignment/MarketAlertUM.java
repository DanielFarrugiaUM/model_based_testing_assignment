package com.cps3230assignment;

import com.cps3230assignment.interfaces.IAlertClient;
import com.cps3230assignment.models.Alert;
import com.cps3230assignment.models.MarketAlertEvent;
import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.openqa.selenium.json.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MarketAlertUM {
    private IAlertClient client;

    public IAlertClient getClient() {
        return client;
    }

    public void setClient(IAlertClient client) {
        this.client = client;
    }

    public boolean postAlert(Alert alert){
        HttpResponse<JsonNode> response = client.postAlert(alert);
        return response.isSuccess();
    }

    public boolean purgeAlerts(){
        HttpResponse<JsonNode> response = client.purgeAlerts();
        return response.isSuccess();
    }

    public ArrayList<MarketAlertEvent> getEventsLog(){
        HttpResponse<JsonNode> response = client.getEventsLog();

        Gson parser = new Gson();
        Type eventsListType = new TypeToken<ArrayList<MarketAlertEvent>>(){}.getType();
        String jsonString = response.getBody().toString();
        ArrayList<MarketAlertEvent> events = parser.fromJson(jsonString, eventsListType);
        return events;
    }
}
