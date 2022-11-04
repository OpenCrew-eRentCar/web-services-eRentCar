package com.acme.webserviceserentcar.behaviourTest.steps;

import com.acme.webserviceserentcar.car.domain.model.entity.CarBrand;
import com.acme.webserviceserentcar.car.domain.persistence.CarBrandRepository;
import com.acme.webserviceserentcar.car.service.CarBrandServiceImpl;
import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.domain.service.ClientService;
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
public class CarBrandsStepDefinition {
    @Mock
    private CarBrandRepository carBrandRepository;

    @Mock
    private ClientService clientService;

    @Mock
    private Validator validator;

    @InjectMocks
    @Spy
    private CarBrandServiceImpl carBrandService;

    private List<CarBrand> carBrands;

    private CarBrand carBrand;

    private Client client;

    private List<CarBrand> results;

    private CarBrand result;

    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        carBrand = new CarBrand();
        carBrand.setId(1L);
    }

    @Given("I am Manager User")
    public void iAmManager() {
        client = new Client();
        client.setId(1L);
    }

    @And("Exist the following Car Brands in the repository")
    public void existTheFollowingCarBrandsInTheRepository(DataTable table){
        List<List<String>> rows = table.cells().stream().skip(1).toList();

        carBrands = new ArrayList<>();
        for (List<String> row : rows){
            CarBrand carBrand = new CarBrand();
            carBrand.setId(Long.parseLong(row.get(0)));
            carBrand.setName(row.get(1));
            carBrands.add(carBrand);
        }
    }

    @When("I get all Car Brands")
    public void iGetAllCarBrands(){
        when(carBrandRepository.findAll()).thenReturn(carBrands);
        results = carBrandService.getAll();
    }

    @Then("I should get a list of Car Brands with length {int}")
    public void iShouldGetAListOfCarBrandWithLength(int length){
        assertEquals(length,results.size());
        assertEquals(carBrands, results);
    }

    @When("I get Car Brand with id {long}")
    public void iGetCarBrandWithId(Long carBrandId){
        when(carBrandRepository.findById(carBrandId))
                .thenReturn(Optional.of(carBrands.get(carBrandId.intValue() - 1)));
        result = carBrandService.getById(carBrandId);
    }

    @Then("I should get a Car Brand with id {int}")
    public void iShouldGetACarBrandWithID(int carBrandId){
        assertEquals(carBrandId, result.getId());
    }

    @When("I complete my Car Brand info in the system with data")
    public void iCompleteMyCarBrandInfoInTheSystemWithDate(DataTable table){
        List<List<String>> rows = table.cells().stream().skip(1).toList();
        carBrand = new CarBrand();
        carBrand.setId(Long.parseLong(rows.get(0).get(0)));
        carBrand.setName(rows.get(0).get(1));
        carBrand.setImagePath(rows.get(0).get(2));

        when(carBrandRepository.save(carBrand)).thenReturn(carBrand);
        result = carBrandService.create(carBrand);
    }

    @When("I update my Car Brand info with")
    public void iUpdateMyCarBrandInfoWith(DataTable table){
        List<List<String>> rows = table.cells().stream().skip(1).toList();

        CarBrand expected = carBrand;
        expected.setId(Long.parseLong(rows.get(0).get(0)));
        expected.setName(rows.get(0).get(1));

        when(carBrandRepository.save(expected)).thenReturn(expected);
        when(carBrandRepository.findById(carBrand.getId())).thenReturn(Optional.of(carBrand));
        when(carBrandRepository.save(expected)).thenReturn(expected);

        result = carBrandService.update(expected.getId(), expected);
    }

    @Then("I should get a Car Brand with id {long} and name {string}")
    public void IShouldGetACarBrandWithIdAndNamesKia(Long carBrandId, String name){
        assertEquals(carBrandId, result.getId());
        assertEquals(name, result.getName());
    }

    @When("I delete my Car Brand")
    public void iDeleteMyCarModel(){
        carBrands.remove(carBrand);
        when(carBrandRepository.findById(carBrand.getId())).thenReturn(Optional.of(carBrand));
        clientService.delete(carBrand.getId());
        carBrands.remove(client.getId().intValue() - 1);
        results = carBrands;
    }

    @Then("I should not get a Car Brand with id {long}")
    public void iShouldNotGetACarModelWithId(Long carBrandId){
        boolean existsCarBrand = false;
        for (CarBrand carBrand1 : results) {
            if (Objects.equals(carBrand1.getId(), carBrandId)) {
                existsCarBrand = true;
                break;
            }
        }
        assertFalse(existsCarBrand);
    }
}
