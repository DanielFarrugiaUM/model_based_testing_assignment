package com.cps3230assignment.alertsAPI;

import com.cps3230assignment.Constants;
import com.cps3230assignment.MarketAlertUmAPI;
import com.cps3230assignment.alertsAPI.enums.ApiStates;
import com.cps3230assignment.models.*;
import com.cps3230assignment.utils.MarketAlertClient;
import nz.ac.waikato.modeljunit.*;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ApiModelTest implements FsmModel {

    private final MarketAlertClient client = new MarketAlertClient();
    private MarketAlertUmAPI sut = new MarketAlertUmAPI(client);
    private final List<String> alerts = new ArrayList<>();
    private ApiStates currentState = ApiStates.ACCEPTING;

    public ApiModelTest(){
        // We are assuming 0 alerts on model start, therefore
        // sync with server.
        boolean isSuccessful = sut.purgeAlerts();
        if(!isSuccessful) throw new RuntimeException("Unable to purge alert on start");
        // Clear the events log on server, in case there are
        // some manually created events. This is because each
        // event will be treated individually in these tests.
        sut.getEventsLog();

    }

    @Override
    public Object getState() {
        return currentState;
    }

    @Override
    public void reset(boolean b) {
        if (b) {
            sut = new MarketAlertUmAPI(client);

            boolean isSuccessful = sut.purgeAlerts();
            if(!isSuccessful) throw new RuntimeException("Unable to purge alert on restart");
            alerts.clear();
            sut.getEventsLog();

            currentState = ApiStates.ACCEPTING;
        }
    }

    public @Action void uploadAlert(){
        Alert alert = new Alert();
        alert.setAlertType(AlertType.ELECTRONICS.value());
        alert.setHeading("Jumper Windows 11 Laptop");
        alert.setDescription("Jumper Windows 11 Laptop 1080P Display,12GB RAM 256GB SSD");
        alert.setUrl("https://www.amazon.co.uk/Windows-Display-Ultrabook-Processor-Bluetooth");
        alert.setImageUrl("https://m.media-amazon.com/images/I/712Xf2LtbJL._AC_SX679_.jpg");
        alert.setPostedBy(Constants.USER_ID.value());
        alert.setPriceInCents(24999);

        UploadAlertResponse uploadResponse = sut.postAlert(alert);
        ArrayList<MarketAlertEvent> log = sut.getEventsLog();
        MarketAlertEvent eventData = log.get(0);
        int eventLogType = eventData.getEventLogType();
        List<UploadAlertResponse> alertsOnServer
                = eventData.getSystemState().getAlerts();
        List<String> alertIdsOnServer
                = alertsOnServer.stream().map(UploadAlertResponse::getId).toList();

        if (currentState == ApiStates.ACCEPTING){
            if(alerts.size() == 4){
                currentState = ApiStates.REPLACING;
            }
            alerts.add(0, uploadResponse.getId());

        } else if (currentState == ApiStates.REPLACING) {
            alerts.remove(4);
            alerts.add(0, uploadResponse.getId());
        }
        Assert.assertEquals(EventLogType.ALERT_CREATED.value(), eventLogType);
        // It is better to match the ids rather than check
        // for list size. Because the latter does not cover correct
        // replacement.
        Assert.assertEquals(alerts, alertIdsOnServer);
    }

    public @Action void purgeAlerts(){
        boolean isSuccessful = sut.purgeAlerts();
        if (!isSuccessful) throw new RuntimeException("purgeAlerts call unsuccessful");

        ArrayList<MarketAlertEvent> log = sut.getEventsLog();
        MarketAlertEvent eventData = log.get(0);
        int eventLogType = eventData.getEventLogType();
        List<UploadAlertResponse> alertsOnServer
                = eventData.getSystemState().getAlerts();

        if (currentState == ApiStates.REPLACING) currentState = ApiStates.ACCEPTING;
        alerts.clear();

        Assert.assertEquals(EventLogType.ALERTS_DELETED.value(), eventLogType);
        Assert.assertTrue(alertsOnServer.isEmpty());
    }

    @Test
    public void modelRunner(){
        final RandomTester tester = new RandomTester(new ApiModelTest());
        tester.setRandom(new Random()); //Allows for a random path each time the model is run.
        tester.buildGraph(); //Builds a model of our FSM to ensure that the coverage metrics are correct.
        //tester.addListener(new StopOnFailureListener()); //This listener forces the test class to stop running as soon as a failure is encountered in the model.
        tester.addListener("verbose"); //This gives you printed statements of the transitions being performed along with the source and destination states.
        tester.addCoverageMetric(new TransitionPairCoverage()); //Records the transition pair coverage i.e. the number of paired transitions traversed during the execution of the test.
        tester.addCoverageMetric(new StateCoverage()); //Records the state coverage i.e. the number of states which have been visited during the execution of the test.
        tester.addCoverageMetric(new ActionCoverage()); //Records the number of @Action methods which have been executed during the execution of the test.
        tester.generate(100); //Generates n transitions
        tester.printCoverage(); //Prints the coverage metrics specified above.
    }
}
