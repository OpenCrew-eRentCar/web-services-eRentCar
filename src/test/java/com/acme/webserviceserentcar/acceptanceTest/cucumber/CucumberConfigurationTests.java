package com.acme.webserviceserentcar.acceptanceTest.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/com/acme/webserviceserentcar/acceptanceTest/features",
        glue = {
                "com.acme.webserviceserentcar.acceptanceTest.steps",
                "com.acme.webserviceserentcar.acceptanceTest.cucumber"
        }
)
public class CucumberConfigurationTests {
}
