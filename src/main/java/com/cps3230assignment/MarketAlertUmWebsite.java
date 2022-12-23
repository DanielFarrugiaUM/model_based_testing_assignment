package com.cps3230assignment;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MarketAlertUmWebsite {

    private final WebDriver webDriver;
    private final String baseUrl = Constants.WEBSITE_BASE_URL.value();
    private final String loginPageUrl;
    private final String logoutUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getLoginPageUrl() {
        return loginPageUrl;
    }

    public String getAlertsUrl() {
        return alertsUrl;
    }

    private final String alertsUrl;

    public MarketAlertUmWebsite(WebDriver wd){
        this.webDriver = wd;
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(8));
        this.loginPageUrl = String.format("%s/%s", baseUrl, "Alerts/Login");
        this.logoutUrl = String.format("%s/%s", baseUrl, "Home/Logout");
        this.alertsUrl = String.format("%s/%s", baseUrl, "Alerts/List");
        webDriver.get(baseUrl);
    }

    public void loginPage(){
        webDriver.get(loginPageUrl);

    }

    public void login(String userId){
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("UserId"))).sendKeys(userId);
        WebElement submitBtn = webDriver.findElement(By.xpath("//input[@type='submit']"));
        submitBtn.click();
    }

    public void logout(){
        webDriver.get(logoutUrl);
    }

    public void alerts(){
        webDriver.get(alertsUrl);
    }

    public void home(){
        webDriver.get(baseUrl);
    }
}
