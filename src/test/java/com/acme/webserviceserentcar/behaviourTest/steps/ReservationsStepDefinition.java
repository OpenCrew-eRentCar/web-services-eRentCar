package com.acme.webserviceserentcar.behaviourTest.steps;

import com.acme.webserviceserentcar.client.domain.model.entity.Plan;
import com.acme.webserviceserentcar.client.domain.model.enums.PlanName;
import com.acme.webserviceserentcar.rent.domain.model.entity.Rent;
import com.acme.webserviceserentcar.rent.domain.persistence.RentRepository;
import com.acme.webserviceserentcar.rent.domain.service.RentService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ReservationsStepDefinition {

    @InjectMocks
    @Spy
    RentService rentService;

    @Mock
    RentRepository rentRepository;

    private List<Rent> rents;

    private List<Rent> results;

    @And("Exist the following Reservation in the repository")
    public void existTheFollowingReservationInTheRepository(DataTable table) {
        List<List<String>> rows = table.cells().stream().skip(1).toList();

    }

    @When("I get all Reservations")
    public void iGetAllReservations() {
        when(rentRepository.findAll()).thenReturn(rents);
        results = rentService.getAll();
    }

    @Then("I should get a list of Reservations with length {int}")
    public void iShouldGetAListOfReservationsWithLength(int length) {
        assertEquals(length, results.size());
        assertEquals(rents, results);
    }
}
