package com.acme.webserviceserentcar.behaviourTest.steps;

import com.acme.webserviceserentcar.car.domain.model.entity.CarBrand;
import com.acme.webserviceserentcar.car.domain.persistence.CarBrandRepository;
import com.acme.webserviceserentcar.car.service.CarBrandServiceImpl;
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
    private Validator validator;

    @InjectMocks
    @Spy
    private CarBrandServiceImpl carBrandService;

    private List<CarBrand> carBrands;

    private CarBrand carBrand;

    private List<CarBrand> results;

    private CarBrand result;

    @Before
    public void setUp(){MockitoAnnotations.openMocks(this);}

    @Given("I am Admin Register User")
    public void iAmAClient() {}

    @And("Exist the following Car Brands in the repository")
    public void existTheFollowingCarBrandInTheRepository(DataTable table){
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



}
