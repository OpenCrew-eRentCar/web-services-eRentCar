package com.acme.webserviceserentcar.systemTest.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseStepDefinition {
    protected WebDriver driver;

    @Before(order = 1)
    public void initializeDriver() {
        System.setProperty("webdriver.chrome.driver", "./src/test/java/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:3000/");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
