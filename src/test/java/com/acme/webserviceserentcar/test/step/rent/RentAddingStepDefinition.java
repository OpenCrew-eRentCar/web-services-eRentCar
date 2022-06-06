package com.acme.webserviceserentcar.test.step.rent;

import com.acme.webserviceserentcar.rent.resource.create.CreateRentResource;
import com.acme.webserviceserentcar.rent.resource.RentResource;
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

import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class RentAddingStepDefinition {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int randomServerPort;

    private String endPointPath;
    private ResponseEntity<String> responseEntity;

    @Given("The Endpoint {string} is available for rents")
    public void theEndpointIsAvailableForRents(String endPointPath) {
        this.endPointPath = String.format("http://localhost:%d/api/v1/rents", randomServerPort);
    }

    @When("A Rent Request is sent with values {string}, {string}, {int}, {int}")
    public void aRentRequestIsSentWithValues(String startDate, String finishDate, int paymentAmount, int rate) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2022);
        cal.set(Calendar.MONTH, Calendar.JUNE);
        cal.set(Calendar.DAY_OF_MONTH, 2);

        CreateRentResource resource = new CreateRentResource()
                .withStartDate(cal.getTime())
                .withFinishDate(cal.getTime())
                .withAmount(paymentAmount)
                .withRate(rate);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateRentResource> request = new HttpEntity<>(resource, headers);
        responseEntity = testRestTemplate.postForEntity(endPointPath, request, String.class);
    }

    @Then("A Response with Status {int} is received for the rent")
    public void aResponseIsReceivedWithStatus(int expectedStatusCode) {
        int actualStatusCode = responseEntity.getStatusCodeValue();
        assertThat(expectedStatusCode).isEqualTo(actualStatusCode);
    }

    @And("A Rent Resource with values {string}, {string}, {int}, {int}")
    public void aRentResourceWithValues(String startDate, String finishDate, int paymentAmount, int rate) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2022);
        cal.set(Calendar.MONTH, Calendar.JUNE);
        cal.set(Calendar.DAY_OF_MONTH, 2);

        RentResource expectedResource = new RentResource()
                .withStartDate(cal.getTime())
                .withFinishDate(cal.getTime())
                .withAmount(paymentAmount)
                .withRate(rate);
        String value = responseEntity.getBody();
        ObjectMapper mapper = new ObjectMapper();
        RentResource actualResource;
        try {
            actualResource = mapper.readValue(value, RentResource.class);
        } catch (JsonProcessingException | NullPointerException e) {
            actualResource = new RentResource();
        }
        expectedResource.setId(actualResource.getId());
        assertThat(expectedResource).usingRecursiveComparison()
                .isEqualTo(actualResource);
    }

}
