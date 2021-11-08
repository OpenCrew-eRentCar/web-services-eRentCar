package com.acme.webserviceserentcar.shared.mapping;

import com.acme.webserviceserentcar.car.mapping.CarMapper;
import com.acme.webserviceserentcar.car.mapping.CategoryMapper;
import com.acme.webserviceserentcar.client.mapping.ClientMapper;
import com.acme.webserviceserentcar.client.mapping.PlanMapper;
import com.acme.webserviceserentcar.rent.mapping.RentMapper;
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

    @Bean
    public CategoryMapper categoryMapper() { return new CategoryMapper(); }

    @Bean
    public RentMapper rentMapper() { return new RentMapper(); }

    @Bean
    public  CarMapper carMapper() {return new CarMapper(); }
}
