package com.acme.webserviceserentcar;

import com.stripe.Stripe;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

@EnableJpaAuditing
@SpringBootApplication
public class WebServicesErentcarApplication extends SpringBootServletInitializer {
    @PostConstruct
    public void setup() {
        //Stripe.apiKey = "pk_test_51LxdH4I2LYtxNEDrHbqCWt1xkA17y0sWMiL8qZH5ftucNieV6vW2GLUMHzfKe0hwnrKzhILBDmzxWF78Z31O6ENV002PdpT2Jd";
        Stripe.apiKey = "sk_test_51LxdH4I2LYtxNEDrxTv1VSqYRanRe9cJKTHjacKjU9epaXEX9IqDZQ0vF2oPH5NlCebv3ViRjFB2QlIxJs5AY9Wi00y5xzWKup";
    }

    public static void main(String[] args) {
        SpringApplication.run(WebServicesErentcarApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/")
                        .allowedOrigins("http://localhost:4200");
            }
        };
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WebServicesErentcarApplication.class);
    }
}
