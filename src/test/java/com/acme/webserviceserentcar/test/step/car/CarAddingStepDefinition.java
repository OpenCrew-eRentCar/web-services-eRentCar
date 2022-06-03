package com.acme.webserviceserentcar.test.step.car;

import com.acme.webserviceserentcar.car.resource.CarResource;
import com.acme.webserviceserentcar.car.resource.create.CreateCarResource;
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

public class CarAddingStepDefinition {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int randomServerPort;

    private String endPointPath;
    private ResponseEntity<String> responseEntity;

    @Given("The Endpoint {string} is available for cars")
    public void theEndpointIsAvailableForCars(String endPointPath) {
        //this.endPointPath = String.format("http://localhost:%d/api/v1/cars", 8080);
        this.endPointPath = endPointPath;
    }

    @When("A Car Request is sent with values {string}, {string}, {int}, {string}, {int}, {int}, {int}, {string}, {int}, {int}")
    public void aPlanRequestIsSentWithValues(String address, String brand, int year, String model, int mileage, int seating, int carValueInDollars, String extraInformation, int rate, int rentAmountDay) {
        CreateCarResource resource = new CreateCarResource()
                .withAddress(address)
                .withYear(year)
                .withMileage(mileage)
                .withSeating(seating)
                .withCarValueInDollars(carValueInDollars)
                .withExtraInformation(extraInformation)
                .withRentAmountDay(rentAmountDay);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateCarResource> request = new HttpEntity<>(resource, headers);
        responseEntity = testRestTemplate.postForEntity(endPointPath, request, String.class);
    }

    @Then("A Response with Status {int} is received for the car")
    public void aResponseWithStatusIsReceivedForTheCar(int expectedStatusCode) {
        int actualStatusCode = responseEntity.getStatusCodeValue();
        assertThat(expectedStatusCode).isEqualTo(actualStatusCode);
    }

    @And("A Car Resource with values {string}, {string}, {int}, {string}, {int}, {int}, {int}, {string}, {int}, {int}")
    public void aCarResourceWithValues(String address, String brand, int year, String model, int mileage, int seating, int carValueInDollars, String extraInformation, int rate, int rentAmountDay) {
        CarResource expectedResource = new CarResource()
                .withAddress(address)
                .withYear(year)
                .withMileage(mileage)
                .withSeating(seating)
                .withCarValueInDollars(carValueInDollars)
                .withExtraInformation(extraInformation)
                .withRate(rate)
                .withRentAmountDay(rentAmountDay);
        String value = responseEntity.getBody();
        ObjectMapper mapper = new ObjectMapper();
        CarResource actualResource;
        try {
            actualResource = mapper.readValue(value, CarResource.class);
        } catch (JsonProcessingException | NullPointerException e) {
            actualResource = new CarResource();
        }
        expectedResource.setId(actualResource.getId());
        assertThat(expectedResource).usingRecursiveComparison()
                .isEqualTo(actualResource);
    }
}
