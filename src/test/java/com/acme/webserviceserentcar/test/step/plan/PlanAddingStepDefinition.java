package com.acme.webserviceserentcar.test.step.plan;

import com.acme.webserviceserentcar.client.resource.create.CreatePlanResource;
import com.acme.webserviceserentcar.client.resource.PlanResource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PlanAddingStepDefinition {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int randomServerPort;

    private String endPointPath;
    private ResponseEntity<String> responseEntity;

    @Given("The Endpoint {string} is available for plans")
    public void theEndpointIsAvailableForPlans(String endPointPath) {
        //this.endPointPath = String.format("http://localhost:%d/api/v1/plans", randomServerPort);
        this.endPointPath = endPointPath;
    }

    @When("A Plan Request is sent with values {string}, {string}, {int}")
    public void aPlanRequestIsSentWithValues(String name, String benefit, int price) {
        List<String> benefits = new ArrayList<>();
        benefits.add(benefit);

        CreatePlanResource resource = new CreatePlanResource()
                .withName(name)
                .withBenefits(benefits)
                .withPrice(price);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreatePlanResource> request = new HttpEntity<>(resource, headers);
        responseEntity = testRestTemplate.postForEntity(endPointPath, request, String.class);
    }

    @Then("A Response with Status {int} is received for the plan")
    public void aResponseWithStatusIsReceivedForThePlan(int expectedStatusCode) {
        int actualStatusCode = responseEntity.getStatusCodeValue();
        assertThat(expectedStatusCode).isEqualTo(actualStatusCode);
    }

    @And("A Plan Resource with values {string}, {string}, {int}")
    public void aPlanResourceWithValues(String name, String benefit, int price) {
        List<String> benefits = new ArrayList<>();
        benefits.add(benefit);

        PlanResource expectedResource = new PlanResource()
                .withName(name)
                .withBenefits(benefits)
                .withPrice(price);
        String value = responseEntity.getBody();
        ObjectMapper mapper = new ObjectMapper();
        PlanResource actualResource;
        try {
            actualResource = mapper.readValue(value, PlanResource.class);
        } catch (JsonProcessingException | NullPointerException e) {
            actualResource = new PlanResource();
        }
        expectedResource.setId(actualResource.getId());
        assertThat(expectedResource).usingRecursiveComparison()
                .isEqualTo(actualResource);
    }
}
