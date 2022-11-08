package com.acme.webserviceserentcar.behaviourTest.steps;

import com.acme.webserviceserentcar.car.domain.model.entity.CarBrand;
import com.acme.webserviceserentcar.car.domain.persistence.CarBrandRepository;
import com.acme.webserviceserentcar.car.domain.model.entity.CarModel;
import com.acme.webserviceserentcar.car.domain.persistence.CarModelRepository;
import com.acme.webserviceserentcar.car.service.CarModelServiceImpl;
import com.acme.webserviceserentcar.car.resource.create.CreateCarModelResource;
import com.acme.webserviceserentcar.car.resource.update.UpdateCarModelResource;
import com.acme.webserviceserentcar.car.mapping.CarModelMapper;
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

public class CarModelsStepDefinition {
    @Mock
    private CarModelRepository carModelRepository;

    @Mock
    private CarBrandRepository carBrandRepository;

    @Mock
    private CarModelMapper carModelMapper;

    @Mock
    private ClientService clientService;

    @Mock
    private Validator validator;

    @InjectMocks
    @Spy
    private CarModelServiceImpl carModelService;

    private List<CarModel> carModels;

    private CarModel carModel;

    private List<CarModel> results;

    private CarModel result;

    private Client client;

    private CarBrand carBrand;

    private CreateCarModelResource createCarModelResource;

    private UpdateCarModelResource updateCarModelResource;

    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        carModel = new CarModel();
        carModel.setId(1L);
        carBrand = new CarBrand();
        carBrand.setId(2L);

    }

    @Given("I'm Administrator User")
    public void iAmAdministrator(){
        client = new Client();
        client.setId(1L);
    }

    @And("Exist the following Car Models in the repository")
    public void existTheFollowingCarModelsInTheRepository(DataTable table){
        List<List<String>> rows = table.cells().stream().skip(1).toList();

        carModels = new ArrayList<>();
        for (List<String> row: rows){
            CarModel carModel = new CarModel();
            carModel.setId(Long.parseLong(row.get(0)));
            carModel.setName(row.get(1));
            carModels.add(carModel);
        }
    }

    @When("I get all Car Models")
    public void iGetAllCarModels(){
        when(carModelRepository.findAll()).thenReturn(carModels);
        results = carModelService.getAll();
    }

    @Then("I should get a list of Car Models with length {int}")
    public void iShouldGetAListOfCarModelsWithLength(int length){
        assertEquals(length,results.size());
        assertEquals(carModels, results);
    }

    @When("I get Car Model with id {long}")
    public void iGetCarModelWithId(Long carModelId){
        when(carModelRepository.findById(carModelId)).
                thenReturn(Optional.of(carModels.get(carModelId.intValue()-1)));
        result=carModelService.getById(carModelId);
    }

    @Then("I should get a Car Model with id {int}")
    public void iShouldGetACarModelWithID(int carModelId){
        assertEquals(carModelId, result.getId());
    }

    @When("I complete my Car Model info in the system with data")
    public void iCompleteMyCarModelInfoInTheSystemWithDate(DataTable table){
        List<List<String>> rows = table.cells().stream().skip(1).toList();


        CreateCarModelResource createCarModelResource = new CreateCarModelResource();
        createCarModelResource.setCardBrandId(Long.parseLong(rows.get(0).get(0)));
        createCarModelResource.setName(rows.get(0).get(1));

        carModel = new CarModel();
        carModel.setId(Long.parseLong(rows.get(0).get(0)));
        carModel.setName(rows.get(0).get(1));
        carModel.setCarBrand(carBrand);

        when(carBrandRepository.findById(createCarModelResource.getCardBrandId())).thenReturn(Optional.of(carBrand));
        when(carModelMapper.toModel(createCarModelResource)).thenReturn(carModel);
        when(carModelRepository.save(carModel)).thenReturn(carModel);

        result = carModelService.create(createCarModelResource);

    }

    @When("I update my Car Model info with")
    public void iUpdateMyCarModelInfoWith(DataTable table){
        List<List<String>> rows = table.cells().stream().skip(1).toList();

        UpdateCarModelResource updateCarModelResource = new UpdateCarModelResource();
        updateCarModelResource.setCardBrandId(Long.parseLong(rows.get(0).get(0)));

        CarModel carModelUpdate = new CarModel();
        carModelUpdate.setId(carModel.getId());
        carModelUpdate.setName(carModel.getName());
        carModelUpdate.setCarBrand(carBrand);

        when(carBrandRepository.findById(updateCarModelResource.getCardBrandId())).thenReturn(Optional.of(carBrand));
        when(carModelRepository.save(carModelUpdate)).thenReturn(carModelUpdate);
        when(carModelRepository.findById(carModelUpdate.getId())).thenReturn(Optional.of(carModelUpdate));

        result = carModelService.update(carModelUpdate.getId(), updateCarModelResource);
    }

    @When("I delete my Car Model")
    public void iDeleteMyCarModel(){
        carModels.remove(carModel);
        when(carModelRepository.findById(carModel.getId())).thenReturn(Optional.of(carModel));
        clientService.delete(carModel.getId());
        carModels.remove(client.getId().intValue() - 1);
        results = carModels;
    }

    @Then("I should not get a Car Model with id {int}")
    public void iShouldNotGetACarModelWithId(int carModelId){
        boolean existsCarModel = false;
        for (CarModel car : results) {
            if (Objects.equals(car.getId(), carModelId)) {
                existsCarModel = true;
                break;
            }
        }
        assertFalse(existsCarModel);
    }
}
