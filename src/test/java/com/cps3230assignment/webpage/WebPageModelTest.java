package com.cps3230assignment.webpage;

import com.cps3230assignment.Constants;
import com.cps3230assignment.MarketAlertUmWebsite;
import com.cps3230assignment.webpage.enums.WebStates;
import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import nz.ac.waikato.modeljunit.GreedyTester;
import nz.ac.waikato.modeljunit.StopOnFailureListener;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Random;

public class WebPageModelTest implements FsmModel {

    private WebDriver webDriver = new ChromeDriver();
    private MarketAlertUmWebsite sut = new MarketAlertUmWebsite(webDriver);
    private WebStates currentState = WebStates.HOME_PAGE;
    private boolean loggedIn = false;
    private final String correctUserId = Constants.USER_ID.value();

    @Override
    public Object getState() {
        return currentState;
    }

    @Override
    public void reset(boolean b) {
//        webDriver.close();
//        webDriver = new ChromeDriver();
        if(b) sut = new MarketAlertUmWebsite(webDriver);
        currentState = WebStates.HOME_PAGE;
//        sut.home();
        loggedIn = false;
    }

    // This can be called from any state. No guard required.
    public @Action void gotoMyAlerts(){
        switch (currentState){
            case HOME_PAGE -> {
                if(loggedIn){
                    currentState = WebStates.MY_ALERTS_PAGE;
                    sut.alerts();
                    // Check that the actual URL is that for alerts
                    Assert.assertEquals(sut.getAlertsUrl(), webDriver.getCurrentUrl());
                }else {
                    currentState = WebStates.LOGIN_PAGE;
                    sut.alerts();
                    // Check that the actual URL is that for login
                    Assert.assertFalse(loggedIn);
                    Assert.assertEquals(sut.getLoginPageUrl(), webDriver.getCurrentUrl());
                }
            }
            case LOGIN_PAGE -> {
                sut.alerts();
                // Check that the actual URL is that for login
                Assert.assertFalse(loggedIn);
                Assert.assertEquals(sut.getLoginPageUrl(), webDriver.getCurrentUrl());
            }
            case MY_ALERTS_PAGE -> {
                sut.alerts();
                // Check that the actual URL is that for alerts
                Assert.assertTrue(loggedIn);
                Assert.assertEquals(sut.getAlertsUrl(), webDriver.getCurrentUrl());
            }
        }
    }

    // This can be called from any state. No guard required.
    public @Action void gotoHome(){
        if (currentState != WebStates.HOME_PAGE) {
            currentState = WebStates.HOME_PAGE;
        }
        sut.home();
        Assert.assertEquals(sut.getBaseUrl() + '/', webDriver.getCurrentUrl());
    }

    // The login action has to be split in two
    // because there are two different parameters to try.
    // This is with a valid user ID.
    public boolean loginGuard(){ return currentState == WebStates.LOGIN_PAGE && !loggedIn; }
    public @Action void login(){
        sut.login(correctUserId);
        currentState = WebStates.MY_ALERTS_PAGE;
        Assert.assertEquals(sut.getAlertsUrl(), webDriver.getCurrentUrl());
    }
    //This is with an invalid user ID.
    public boolean invalidLoginGuard(){ return currentState == WebStates.LOGIN_PAGE && !loggedIn; }
    public @Action void invalidLogin(){
        String invalidId = "invalidValue";
        sut.login(invalidId);
        Assert.assertEquals(sut.getLoginPageUrl(), webDriver.getCurrentUrl());
    }

    public boolean logoutGuard(){ return currentState != WebStates.LOGIN_PAGE && loggedIn; }
    public @Action void logout(){
        sut.logout();
        loggedIn = false;
        // Here we check redirection
        Assert.assertEquals(sut.getBaseUrl(), webDriver.getCurrentUrl());
        // If the api functioned properly and api call would get return the actual value
        // that reflects whether the user is really logged in.
        // Then it could be checked with:
        // Assert.assertFalse(actualValue);
        // In this case the model will end up throwing errors at a later stage
        // because the model is logged_out = false but this is untrue for the sut!
    }

    public boolean gotoLoginGuard(){ return currentState == WebStates.HOME_PAGE && !loggedIn; }
    public @Action void gotoLogin(){
        sut.loginPage();
        currentState = WebStates.LOGIN_PAGE;
        Assert.assertEquals(sut.getLoginPageUrl(), webDriver.getCurrentUrl());
    }
    @Test
    public void ModelRunner(){
        System.setProperty("webdriver.chrome.driver", Constants.WEBDRIVER_PATH.value());
        final GreedyTester tester = new GreedyTester(new WebPageModelTest());
        tester.setRandom(new Random()); //Allows for a random path each time the model is run.
        tester.buildGraph(); //Builds a model of our FSM to ensure that the coverage metrics are correct.
//        tester.addListener(new StopOnFailureListener()); //This listener forces the test class to stop running as soon as a failure is encountered in the model.
        tester.addListener("verbose"); //This gives you printed statements of the transitions being performed along with the source and destination states.
        tester.addCoverageMetric(new TransitionPairCoverage()); //Records the transition pair coverage i.e. the number of paired transitions traversed during the execution of the test.
        tester.addCoverageMetric(new StateCoverage()); //Records the state coverage i.e. the number of states which have been visited during the execution of the test.
        tester.addCoverageMetric(new ActionCoverage()); //Records the number of @Action methods which have ben executed during the execution of the test.
        tester.generate(10); //Generates n transitions
        tester.printCoverage(); //Prints the coverage metrics specified above.
    }
}
