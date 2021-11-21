package com.acme.webserviceserentcar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WebServicesErentcarApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebServicesErentcarApplication.class, args);
    }

}
