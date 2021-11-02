package com.acme.webserviceserentcar.mapping;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("erentCarModelMapperConfiguration")
public class MappingConfiguration {
    @Bean
    public EnhancedModelMapper modelMapper() {
        return new EnhancedModelMapper();
    }

    @Bean
    public ClientMapper clientMapper() { return new ClientMapper(); }

    @Bean
    public PlanMapper planMapper() { return new PlanMapper(); }
}
