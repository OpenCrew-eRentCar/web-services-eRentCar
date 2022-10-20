package com.acme.webserviceserentcar.unitTest.plans.service;

import com.acme.webserviceserentcar.car.domain.persistence.CarBrandRepository;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PlansServiceImplTest {
    @Mock
    private PlanRepository planRepository;
    private CarBrandRepository carBrandRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private PlanServiceImpl planService;

    private Plan plan;

    private List<Plan> plans;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        List<String> benefits = Arrays.asList("Benefit 1", "Benefit 2", "Benefit 3");

        plan = new Plan();
        plan.setId(1L);
        plan.setName(PlanName.BASIC);
        plan.setPrice(100);
        plan.setBenefits(benefits);

        Plan planOne = new Plan();
        planOne.setId(2L);
        planOne.setName(PlanName.PREMIUM);
        planOne.setPrice(100);
        planOne.setBenefits(benefits);

        Plan planTwo = new Plan();
        planTwo.setId(3L);
        planTwo.setName(PlanName.GOLD);
        planTwo.setPrice(100);
        planTwo.setBenefits(benefits);

        plans = new ArrayList<>();
        plans.add(planOne);
        plans.add(planTwo);
    }

    @Test
    void getAllPlans() {
        when(planRepository.findAll()).thenReturn(plans);

        List<Plan> result = planService.getAll();

        assertEquals(plans,result);
    }

    @Test
    void getPlanById() {
        when(planRepository.findById(plan.getId())).thenReturn(Optional.of(plan));

        Plan result = planService.getById(plan.getId());

        assertEquals(plan, result);
    }

    @Test
    void updatePlan() {
        UpdatePlanResource updatePlanResource = new UpdatePlanResource();
        updatePlanResource.setPrice(plan.getPrice());
        updatePlanResource.setBenefits(plan.getBenefits());

        Plan expected = new Plan();
        expected.setId(plan.getId());
        expected.setBenefits(plan.getBenefits());
        expected.setName(plan.getName());
        expected.setPrice(plan.getPrice());

        when(planRepository.save(expected)).thenReturn(expected);
        when(planRepository.findById(expected.getId())).thenReturn(Optional.of(expected));

        Plan result = planService.update(expected.getId(), updatePlanResource);

        assertEquals(expected, result);
    }

    @Test
    void deletePlan() {
        when(planRepository.findById(plan.getId())).thenReturn(Optional.of(plan));
        planService.delete(plan.getId());
        plans.remove(plan);

        List<Plan> result = plans;

        assertEquals(plans, result);
    }

}