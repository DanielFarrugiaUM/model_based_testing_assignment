package com.cps3230assignment;

public enum Constants {
    USER_ID ("d9bad528-b70f-4321-a1c5-e4977a2e2bed"),
    WEBDRIVER_PATH ("D:\\webdriver\\chromedriver.exe"),
    MARKET_ALERT_BASE_URL ("https://api.marketalertum.com"),
    WEB_PAGE_ADDRESS ("https://www.maltapark.com/");

    private final String value;

    Constants(String value){
        this.value = value;
    }

    public String value(){ return value; }
}
