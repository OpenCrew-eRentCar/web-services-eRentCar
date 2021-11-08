package com.acme.webserviceserentcar.test.cucumber;

import com.acme.webserviceserentcar.WebServicesErentcarApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(
        classes = WebServicesErentcarApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(
        classes = WebServicesErentcarApplication.class,
        loader = SpringBootContextLoader.class)
public class CucumberSpringConfiguration {
}
