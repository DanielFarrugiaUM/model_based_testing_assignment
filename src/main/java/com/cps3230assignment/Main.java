package com.cps3230assignment;

import com.cps3230assignment.models.*;
import com.cps3230assignment.utils.MarketAlertClient;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        MarketAlertUmAPI sys = new MarketAlertUmAPI(new MarketAlertClient());

        Alert alert = new Alert();
        alert.setAlertType(AlertType.ELECTRONICS.value());
        alert.setHeading("Jumper Windows 11 Laptop");
        alert.setDescription("Jumper Windows 11 Laptop 1080P Display,12GB RAM 256GB SSD");
        alert.setUrl("https://www.amazon.co.uk/Windows-Display-Ultrabook-Processor-Bluetooth");
        alert.setImageUrl("https://m.media-amazon.com/images/I/712Xf2LtbJL._AC_SX679_.jpg");
        alert.setPostedBy(Constants.USER_ID.value());
        alert.setPriceInCents(24999);

        UploadAlertResponse response = sys.postAlert(alert);

        System.out.println();
//        ArrayList<MarketAlertEvent> log = sys.getEventsLog();
////        boolean result = sys.purgeAlerts();
//        MarketAlertEvent event = log.get(0);
//        SystemState state = event.getSystemState();
//        System.out.println(event.getId());
//        System.out.println(state.getLoggedIn());

//        System.setProperty("webdriver.chrome.driver", Constants.WEBDRIVER_PATH.value());
//        WebDriver webDriver = new ChromeDriver();
//        MarketAlertUmWebsite nav = new MarketAlertUmWebsite(webDriver);
//        nav.home();
//        nav.loginPage();
//        nav.login(Constants.USER_ID.value());
//        nav.logout();
    }
}
