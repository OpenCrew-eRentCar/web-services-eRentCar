package com.acme.webserviceserentcar.behaviourTest.steps;

import com.acme.webserviceserentcar.client.domain.model.entity.Plan;
import com.acme.webserviceserentcar.client.domain.model.enums.PlanName;
import com.acme.webserviceserentcar.client.domain.persistence.PlanRepository;
import com.acme.webserviceserentcar.client.domain.service.PlanService;
import com.acme.webserviceserentcar.client.service.PlanServiceImpl;
import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.domain.service.ClientService;
import com.acme.webserviceserentcar.client.resource.update.UpdatePlanResource;
import com.acme.webserviceserentcar.client.mapping.PlanMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.*;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

public class PlansStepDefinition {
    @Mock
    private PlanRepository planRepository;

    @Mock
    private ClientService clientService;

    @Mock
    private PlanMapper planMapper;

    @Mock
    private Validator validator;

    @InjectMocks
    @Spy
    private PlanServiceImpl planService;

    private List<Plan> plans;

    private Plan plan;

    private Client client;

    private List<Plan> results;

    private Plan result;

    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        plan = new Plan();
        plan.setId(1L);
    }

    @Given("I'm User Administrator")
    public void iUserAdministrator() {
        client = new Client();
        client.setId(1L);
    }

    @And("the following plans exist in the repository")
    public void existTheFollowingPlansInTheRepository(DataTable table) {
        List<List<String>> rows = table.cells().stream().skip(1).toList();

        plans = new ArrayList<>();
        for (List<String> row : rows) {
            Plan plan = new Plan();
            plan.setId(Long.parseLong(row.get(0)));
            plan.setName(PlanName.valueOf(row.get(1)));
            plans.add(plan);
        }
    }

    @When("I get all Plans")
    public void iGetAllPlans(){
        when(planRepository.findAll()).thenReturn(plans);
        results = planService.getAll();
    }

    @Then("I should get a list of Plans with length {int}")
    public void iShouldGetAListOfPlanWithLength(int length){
        assertEquals(length,results.size());
        assertEquals(plans, results);
    }

    @When("I get a Plan with id {long}")
    public void iGetPlanWithId(Long planId){
        when(planRepository.findById(planId))
                .thenReturn(Optional.of(plans.get(planId.intValue()-1)));
        result = planService.getById(planId);
    }

    @Then("I should get a Plan with id {int}")
    public void iShouldGetAPlanWithID(int planId){

        assertEquals(planId, result.getId());
    }

    @When("I update my Plan info with")
    public void iUpdatePlanInfoWith(DataTable table) {
        List<List<String>> rows = table.cells().stream().skip(1).toList();

        UpdatePlanResource updatePlanResource = new UpdatePlanResource();
        updatePlanResource.setPrice(Integer.parseInt(rows.get(0).get(2)));

        Plan planUpdate = new Plan();
        planUpdate.setId(plan.getId());
        planUpdate.setPrice(updatePlanResource.getPrice());

        when(planRepository.findById(planUpdate.getId())).thenReturn(Optional.of(planUpdate));
        when(planRepository.save(planUpdate)).thenReturn(planUpdate);

        result = planService.update(planUpdate.getId(), updatePlanResource);
    }

    @Then("I should get a Plan with id {long} and price {int}")
    public void IShouldGetAPlanWithIdAndPrice(Long planId, Integer price){
        assertEquals(planId, result.getId());
        assertEquals(price, result.getPrice());
    }

    @When("I delete my Plan")
    public void iDeleteMyPlan(){
        plans.remove(plan);
        when(planRepository.findById(plan.getId())).thenReturn(Optional.of(plan));
        clientService.delete(plan.getId());
        plans.remove(client.getId().intValue() - 1);
        results = plans;
    }

    @Then("I should not get a Plan with id {int}")
    public void iShouldNotGetAPlanWithId(int planId){
        boolean existsPlan = false;
        for (Plan plan : results) {
            if (Objects.equals(plan.getId(), planId)) {
                existsPlan = true;
                break;
            }
        }
        assertFalse(existsPlan);
    }
}
