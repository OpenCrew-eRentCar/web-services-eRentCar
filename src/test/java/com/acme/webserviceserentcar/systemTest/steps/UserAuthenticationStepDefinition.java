package com.acme.webserviceserentcar.systemTest.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserAuthenticationStepDefinition {
    private WebDriver driver;
    private final String BASE_URL = "http://localhost:3000/";

    @Before("@system")
    public void initializeDriver() {
        System.setProperty("webdriver.chrome.driver", "./src/test/java/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @After("@system")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I am on the home page")
    public void iAmOnTheHomePage() {}

    @And("I click on the Login link")
    public void iClickOnTheLoginLink() {
        driver.findElement(By.cssSelector(".\\!ml-auto > .p-button-label")).click();
    }

    @And("I enter my username and password")
    public void iEnterMyUsernameAndPassword(DataTable table) {
        List<List<String>> row = table.cells().stream().skip(1).toList();
        String username = row.get(0).get(0);
        String password = row.get(0).get(1);

        driver.findElement(By.id("username")).click();
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).sendKeys(password);
    }

    @When("I submit the login form")
    public void iSubmitTheLoginForm() {
        driver.findElement(By.cssSelector(".\\!mt-6 > .p-button-label")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Then("I should see my names {string} on the page")
    public void iShouldSeeMyNamesOnThePage(String names) {
        WebElement userMenu = driver.findElement(By.id("popup_user_menu"));
        assertEquals(names, userMenu.getAttribute("aria-label"));
    }

    @Then("I stay on the login page")
    public void iStayOnTheLoginPage() {
        assertEquals(BASE_URL, driver.getCurrentUrl());
    }
}
