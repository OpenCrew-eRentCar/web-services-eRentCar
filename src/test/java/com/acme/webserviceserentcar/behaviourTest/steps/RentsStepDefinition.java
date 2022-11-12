package com.acme.webserviceserentcar.behaviourTest.steps;

import com.acme.webserviceserentcar.car.domain.model.entity.Car;
import com.acme.webserviceserentcar.car.domain.model.entity.CarBrand;
import com.acme.webserviceserentcar.car.domain.model.enums.CategoryOfCar;
import com.acme.webserviceserentcar.car.domain.model.enums.InsuranceType;
import com.acme.webserviceserentcar.car.domain.model.enums.MechanicConditions;
import com.acme.webserviceserentcar.car.domain.persistence.CarRepository;
import com.acme.webserviceserentcar.car.resource.create.CreateCarResource;
import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.domain.service.ClientService;
import com.acme.webserviceserentcar.client.service.ClientServiceImpl;
import com.acme.webserviceserentcar.rent.domain.model.entity.Rent;
import com.acme.webserviceserentcar.rent.domain.persistence.RentRepository;
import com.acme.webserviceserentcar.rent.domain.service.RentService;
import com.acme.webserviceserentcar.rent.mapping.RentMapper;
import com.acme.webserviceserentcar.rent.resource.create.CreateRentResource;
import com.acme.webserviceserentcar.rent.resource.update.UpdateRentResource;
import com.acme.webserviceserentcar.rent.service.RentServiceImpl;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

public class RentsStepDefinition {

    @Mock
    private RentRepository rentRepository;

    @Mock
    private ClientServiceImpl clientService;

    @InjectMocks
    @Spy
    private RentServiceImpl rentService;
    private List<Rent> rents;
    private List<Rent> results;
    private Rent result;
    private Client client;
    private Car car;
    private RentMapper rentMapper;
    private CreateRentResource rent;


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Rent rent = new Rent();
        rent.setId(1L);
        rent.setStartDate(new Date());
        rent.setFinishDate(new Date());
        rent.setAmount(2000);
        rent.setRate(2.0);
        rent.setClient(client);
        rent.setCar(car);
    }

    @Given("I am registered user for get all rents")
    public void iAmRegisteredUser() {
        client = new Client();
        client.setId(1L);
    }

    @Given("Exist the following Rents in the Repository")
    public void existTheFollowingRentsInTheRepository(DataTable table) {
        List<List<String>> rows = table.cells().stream().skip(1).toList();

        rents = new ArrayList<>();
        for (List<String> row : rows) {
            car = new Car();
            car.setId(Long.parseLong(row.get(2)));

            Rent rent = new Rent();
            rent.setId(Long.parseLong(row.get(0)));
            rent.setAmount(Integer.parseInt(row.get(1)));
            rent.setClient(client);
            rent.setCar(car);
            rents.add(rent);
        }
    }

    @When("I get all Rents")
    public void iGetAllRents() {
        when(clientService.getByToken()).thenReturn(client);
        when(rentRepository.findAllByClientId(client.getId())).thenReturn(rents);
        results = rentService.getAll();
    }

    @Then("I should get a list of Rents with length {int}")
    public void iShouldGetAListOfRentsWithLength(int length) {
        assertEquals(length, results.size());
        assertEquals(rents, results);
    }

    /*@When("I get Rent with id {long}")
    public void iGetRentWithId(Long rentId) {
        when(rentRepository.findById(rentId))
                .thenReturn(Optional.of(rents.get(rentId.intValue() - 1)));
        result = rentService.getById(rentId);
    }

    @Then("I should get a Rent with id {int}")
    public void iShouldGetARentWithId(int rentId) {
        assertEquals(rentId, result.getId());
    }

    @When("I complete my rent info in the system with data")
    public void iCompleteMyRentInfoInTheSystemWithData(DataTable table) {
        List<List<String>> rows = table.cells().stream().skip(1).toList();
        rent = new CreateRentResource();
        rent.setCarId(Long.parseLong(rows.get(0).get(0)));
        rent.setAmount(Integer.parseInt(rows.get(0).get(1)));
        rent.setRate(Integer.parseInt(rows.get(0).get(2)));
        Rent rentMapped = rentMapper.toModel(rent);
        when(rentRepository.save(rentMapped)).thenReturn(rentMapped);
        result = rentService.create(rent);

    }

    @Then("I should get a Rent with id {int}")
    public void iShouldGetARentWithIdAndAmount(int rentId) {
        assertEquals(rentId, result.getId());
    }

    @When("I update my Rent info with")
    public void iUpdateMyRentInfoWith(DataTable table) {
        List<List<String>> rows = table.cells().stream().skip(1).toList();

        UpdateRentResource rentResource = new UpdateRentResource();
        rentResource.setAmount(Integer.parseInt(rows.get(0).get(1)));

        Rent rentMapped = rentMapper.toModel(rent);
        when(rentRepository.save(rentMapped)).thenReturn(rentMapped);
        when(rentRepository.findById(rentMapped.getId())).thenReturn(Optional.of(rentMapped));
        when(rentRepository.save(rentMapped)).thenReturn(rentMapped);

        result = rentService.update(rentMapped.getId(), rentMapped);
    }

    @Then("I should get a Rent with id {int} and amount {int}")
    public void iShouldGetARentWithIdAndAmount(int rentId, int amount) {
        assertEquals(rentId, result.getId());
        assertEquals(amount, result.getAmount());
    }

    @When("I delete my Rent")
    public void iDeleteMyRent() {
        Rent rentMapped = rentMapper.toModel(rent);
        rents.remove(rentMapped);
        when(rentRepository.findById(rentMapped.getId())).thenReturn(Optional.of(rentMapped));
        rentService.delete(rentMapped.getId());
        rents.remove(client.getId().intValue() - 1);
        results = rents;
    }

    @Then("I should not get a Rent with id {int}")
    public void iShouldNotGetARentWithId(int rentId) {
        boolean existsRentBrand = false;
        for (Rent rent1 : results) {
            if (Objects.equals(rent1.getId(), rentId)) {
                existsRentBrand = true;
                break;
            }
        }
        assertFalse(existsRentBrand);
    }*/
}
