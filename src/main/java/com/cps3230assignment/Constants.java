package com.cps3230assignment;

public enum Constants {
    USER_ID ("d9bad528-b70f-4321-a1c5-e4977a2e2bed"),
    WEBDRIVER_PATH ("D:\\webdriver\\chromedriver.exe"),
    WEBSITE_BASE_URL("https://www.marketalertum.com"),
    API_BASE_URL("https://api.marketalertum.com");

    private final String value;

    Constants(String value){
        this.value = value;
    }

    public String value(){ return value; }
}
