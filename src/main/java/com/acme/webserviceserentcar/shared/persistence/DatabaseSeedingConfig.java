package com.acme.webserviceserentcar.shared.persistence;

import com.acme.webserviceserentcar.car.domain.service.CarBrandService;
import com.acme.webserviceserentcar.car.domain.service.CarModelService;
import com.acme.webserviceserentcar.client.domain.service.PlanService;
import com.acme.webserviceserentcar.security.domain.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class DatabaseSeedingConfig {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeedingConfig.class);

    private final RoleService roleService;
    private final CarBrandService carBrandService;
    private final CarModelService carModelService;
    private final PlanService planService;

    public DatabaseSeedingConfig(RoleService roleService, CarBrandService carBrandService,
                                 CarModelService carModelService, PlanService planService) {
        this.roleService = roleService;
        this.carBrandService = carBrandService;
        this.carModelService = carModelService;
        this.planService = planService;
    }

    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) {
        String name = event.getApplicationContext().getId();
        logger.info("Started Database Seeding Process for {} at {}", name, new Timestamp(System.currentTimeMillis()));
        roleService.seed();
        carBrandService.seed();
        carModelService.seed();
        planService.seed();
        logger.info("Finished Database Seeding Process for {} at {}", name, new Timestamp(System.currentTimeMillis()));
    }
}
