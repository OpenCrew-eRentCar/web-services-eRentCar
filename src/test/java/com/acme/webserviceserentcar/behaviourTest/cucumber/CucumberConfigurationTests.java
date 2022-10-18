package com.acme.webserviceserentcar.behaviourTest.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
                "src/test/java/com/acme/webserviceserentcar/behaviourTest/features",
                "src/test/java/com/acme/webserviceserentcar/systemTest/features"
        },
        glue = {
                "com.acme.webserviceserentcar.behaviourTest.steps",
                "com.acme.webserviceserentcar.systemTest.steps",
                "com.acme.webserviceserentcar.behaviourTest.cucumber"
        }
)
public class CucumberConfigurationTests {
}
