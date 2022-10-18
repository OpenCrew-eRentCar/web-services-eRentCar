package com.acme.webserviceserentcar.unitTest.plan.service;

import com.acme.webserviceserentcar.car.domain.model.entity.CarBrand;
import com.acme.webserviceserentcar.car.domain.persistence.CarBrandRepository;
import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.domain.model.entity.Plan;
import com.acme.webserviceserentcar.client.domain.model.enums.PlanName;
import com.acme.webserviceserentcar.client.domain.persistence.PlanRepository;
import com.acme.webserviceserentcar.client.resource.update.UpdatePlanResource;
import com.acme.webserviceserentcar.client.service.PlanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PlanServiceTest {
    @Mock
    private PlanRepository planRepository;
    private CarBrandRepository carBrandRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private PlanServiceImpl planService;

    private Plan plan;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        List<String> benefits = Arrays.asList("Benefit 1", "Benefit 2", "Benefit 3");

        plan = new Plan();
        plan.setId(1L);
        plan.setName(PlanName.BASIC);
        plan.setPrice(100);
        plan.setBenefits(benefits);
    }

    @Test
    void getAllPlans() {
        when(planRepository.findAll()).thenReturn(Arrays.asList(plan));
        assertNotNull(planService.getAll());
    }

    @Test
    void updatePlan() {
        List<String> benefits = Arrays.asList("Benefit 1", "Benefit 2", "Benefit 3");

        Plan request = new Plan();
        request.setId(1L);
        request.setName(PlanName.BASIC);
        request.setPrice(100);
        request.setBenefits(benefits);

        UpdatePlanResource updatePlanResource = new UpdatePlanResource();
        updatePlanResource.setPrice(request.getPrice());
        updatePlanResource.setBenefits(request.getBenefits());

        when(planRepository.findById(plan.getId())).thenReturn(Optional.of(request));
        when(planRepository.save(request)).thenReturn(request);
        Plan result = planService.update(plan.getId(), updatePlanResource);
        assertEquals(request, result);
        verify(planRepository).save(request);
    }

    @Test
    void getPLanById() {
        List<String> benefits = Arrays.asList("Benefit 1", "Benefit 2", "Benefit 3");

        Plan request = new Plan();
        request.setId(1L);
        request.setName(PlanName.BASIC);
        request.setPrice(100);
        request.setBenefits(benefits);

        when(planRepository.findById(plan.getId())).thenReturn(Optional.of(request));
        Plan result = planService.getById(request.getId());
        assertEquals(request, result);
    }

    /*@Test
    void deletePlan() {
        List<String> benefits = Arrays.asList("Benefit 1", "Benefit 2", "Benefit 3");

        Plan request = new Plan();
        request.setId(1L);
        request.setName(PlanName.BASIC);
        request.setPrice(100);
        request.setBenefits(benefits);

        when(planRepository.delete(request))
    }*/

}
