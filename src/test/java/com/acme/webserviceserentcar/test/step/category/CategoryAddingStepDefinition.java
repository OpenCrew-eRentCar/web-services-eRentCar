package com.acme.webserviceserentcar.test.step.category;

import com.acme.webserviceserentcar.car.resource.CategoryResource;
import com.acme.webserviceserentcar.car.resource.CreateCategoryResource;
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

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryAddingStepDefinition {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int randomServerPort;

    private String endPointPath;
    private ResponseEntity<String> responseEntity;

    @Given("The Endpoint {string} is available for categories")
    public void theEndpointIsAvailableForCategories(String endPointPath) {
        this.endPointPath = String.format("http://localhost:%d/api/v1/categories", randomServerPort);
    }

    @When("A Category Request is sent with values {string}")
    public void aCategoryRequestIsSentWithValues(String name) {
        CreateCategoryResource resource = new CreateCategoryResource()
                .withName(name);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateCategoryResource> request = new HttpEntity<>(resource, headers);
        responseEntity = testRestTemplate.postForEntity(endPointPath, request, String.class);
    }


    @Then("A Response with Status {int} is received for the category")
    public void aResponseWithStatusIsReceivedForTheCategory(int expectedStatusCode) {
        int actualStatusCode = responseEntity.getStatusCodeValue();
        assertThat(expectedStatusCode).isEqualTo(actualStatusCode);
    }


    @And("A Category Resource with values {string}")
    public void aPostResourceWithValues(String name) {
        CategoryResource expectedResource = new CategoryResource()
                .withName(name);
        String value = responseEntity.getBody();
        ObjectMapper mapper = new ObjectMapper();
        CategoryResource actualResource;
        try {
            actualResource = mapper.readValue(value, CategoryResource.class);
        } catch (JsonProcessingException | NullPointerException e) {
            actualResource = new CategoryResource();
        }
        expectedResource.setId(actualResource.getId());
        assertThat(expectedResource).usingRecursiveComparison()
                .isEqualTo(actualResource);
    }
}
