package com.acme.webserviceserentcar.behaviourTest.steps;

import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.domain.model.entity.Plan;
import com.acme.webserviceserentcar.client.domain.model.enums.PlanName;
import com.acme.webserviceserentcar.rent.domain.model.entity.Rent;
import com.acme.webserviceserentcar.rent.domain.persistence.RentRepository;
import com.acme.webserviceserentcar.rent.domain.service.RentService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ReservationsStepDefinition {

    @InjectMocks
    @Spy
    RentService rentService;

    @Mock
    RentRepository rentRepository;
    private Client client;
    private List<Rent> results;
    private List<Rent> reservations;

    /*@And("Exist the following Reservation in the repository")
    public void existTheFollowingReservationInTheRepository(DataTable table) {
        List<List<String>> rows = table.cells().stream().skip(1).toList();
        reservations = new ArrayList<>();
        for (List<String> row: rows){
            Rent reservation = new Rent();
            reservation.setId(Long.parseLong(row.get(0)));
            reservation.setStartDate(new Date());
            reservation.setFinishDate(new Date());
            reservation.setAmount(Integer.parseInt(row.get(4)));
            reservation.setRate(2.0);
            reservation.setClient(client);
        }
    }

    @When("I get all Reservations")
    public void iGetAllReservations() {
        when(rentRepository.findAll()).thenReturn(reservations);
        results = rentService.getAll();
    }

    @Then("I should get a list of Reservations with length {int}")
    public void iShouldGetAListOfReservationsWithLength(int length) {
        assertEquals(length, results.size());
        assertEquals(reservations, results);
    }

    @Given("I am a User \\(Reservation)")
    public void iAmAUserReservation() {
        client = new Client();
        client.setId(1L);
    }*/
}
