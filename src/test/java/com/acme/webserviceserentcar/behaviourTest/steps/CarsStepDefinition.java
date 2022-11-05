package com.acme.webserviceserentcar.behaviourTest.steps;

import com.acme.webserviceserentcar.car.domain.model.entity.Car;
import com.acme.webserviceserentcar.car.domain.model.entity.CarModel;
import com.acme.webserviceserentcar.car.domain.model.enums.CategoryOfCar;
import com.acme.webserviceserentcar.car.domain.model.enums.InsuranceType;
import com.acme.webserviceserentcar.car.domain.model.enums.MechanicConditions;
import com.acme.webserviceserentcar.car.domain.persistence.CarModelRepository;
import com.acme.webserviceserentcar.car.domain.persistence.CarRepository;
import com.acme.webserviceserentcar.car.mapping.CarMapper;
import com.acme.webserviceserentcar.car.persistence.CarRepositoryCustom;
import com.acme.webserviceserentcar.car.resource.create.CreateCarResource;
import com.acme.webserviceserentcar.car.resource.update.UpdateCarResource;
import com.acme.webserviceserentcar.car.service.CarServiceImpl;
import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.domain.service.ClientService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

public class CarsStepDefinition {
    @Mock
    private CarRepository carRepository;

    @Mock
    private CarRepositoryCustom carRepositoryCustom;

    @Mock
    private ClientService clientService;

    @Mock
    private CarModelRepository carModelRepository;

    @Mock
    private CarMapper carMapper;

    @Mock
    private Validator validator;

    @InjectMocks
    private CarServiceImpl carService;

    private Car car;
    private Car result;

    private List<Car> cars;
    private List<Car> results;

    private Client client;
    private CarModel carModel;

    private CreateCarResource carResource;
    private UpdateCarResource updateCarResource;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        carModel = new CarModel();
        carModel.setId(1L);

        car = new Car();
        car.setId(1L);
        car.setLicensePlate("AKR-079");
        car.setInsuranceType(InsuranceType.RIMAC);
        car.setCarModel(carModel);
        car.setClient(client);
        car.setActive(true);
        car.setAddress("Cabo blanco con tres Marias #342");
        car.setSeating(4);
        car.setMileage(36);
        car.setManual(true);
        car.setImagePath(List.of("https://image.com"));
        car.setCarValueInDollars(13000);
        car.setCategory(CategoryOfCar.MINIVAN);
        car.setExtraInformation("Lunas rotas");
        car.setMechanicCondition(MechanicConditions.EXCELLENT);
        car.setYear(2007);
    }

    @Given("I am registered user")
    public void iAmRegisteredUser() {
        client = new Client();
        client.setId(1L);
    }

    @Given("Exist the following Cars in the repository")
    public void existTheFollowingCarsInTheRepository(DataTable table) {
        List<List<String>> rows = table.cells().stream().skip(1).toList();

        cars = new ArrayList<>();
        for (List<String> row : rows) {
            Car car = new Car();
            car.setId(Long.parseLong(row.get(0)));
            car.setLicensePlate(row.get(1));
            car.setInsuranceType(InsuranceType.RIMAC);
            car.setCarModel(carModel);
            car.setClient(client);
            car.setActive(true);
            car.setAddress("Cabo blanco con tres Marias #342");
            car.setSeating(4);
            car.setMileage(36);
            car.setManual(true);
            car.setImagePath(List.of("https://image.com"));
            car.setCarValueInDollars(13000);
            car.setCategory(CategoryOfCar.MINIVAN);
            car.setExtraInformation("Lunas rotas");
            car.setMechanicCondition(MechanicConditions.EXCELLENT);
            car.setYear(2007);
            cars.add(car);
        }
    }

    @When("I get all Cars")
    public void iGetAllCars() {
        when(carRepository.findAll()).thenReturn(cars);
        results = carService.getAll();
    }

    @Then("I should get a list of Cars with length {int}")
    public void iShouldGetAListOfCarsWithLength(int length) {
        assertEquals(length, results.size());
        assertEquals(cars, results);
    }

    @When("I get Car with id {long}")
    public void iGetCartWithId(Long carId) {
        when(carRepository.findById(carId))
                .thenReturn(Optional.of(cars.get(carId.intValue() - 1)));
        result = carService.getById(carId);
    }

    @Then("I should get a Car with id {int}")
    public void iShouldGetACartWithId(int carId) {
        assertEquals(carId, result.getId());
    }

    @When("I complete my car info in the system with data")
    public void iCompleteMyRegisterInTheSystemWithData(DataTable table) {
        List<List<String>> rows = table.cells().stream().skip(1).toList();

        // UPDATE DATA
        CreateCarResource createCarResource = new CreateCarResource();
        createCarResource.setCarModelId(Long.parseLong(rows.get(0).get(0)));
        createCarResource.setLicensePlate(rows.get(0).get(1));
        createCarResource.setCarModelId(Long.parseLong(rows.get(0).get(2)));
        createCarResource.setAddress(rows.get(0).get(3));
        createCarResource.setSeating(Integer.parseInt(rows.get(0).get(4)));
        createCarResource.setMileage(Integer.parseInt(rows.get(0).get(5)));
        createCarResource.setImagePath(List.of(rows.get(0).get(6)));
        createCarResource.setCarValueInDollars(Integer.parseInt(rows.get(0).get(7)));
        createCarResource.setExtraInformation(rows.get(0).get(8));
        createCarResource.setYear(Integer.parseInt(rows.get(0).get(9)));
        createCarResource.setManual(true);
        createCarResource.setCategory(CategoryOfCar.MINIVAN);
        createCarResource.setInsuranceType(InsuranceType.RIMAC);
        createCarResource.setMechanicCondition(MechanicConditions.EXCELLENT);

        // CAR DATA
        car = new Car();
        car.setId(Long.parseLong(rows.get(0).get(0)));
        car.setLicensePlate(rows.get(0).get(1));
        car.setInsuranceType(InsuranceType.RIMAC);
        car.setAddress(rows.get(0).get(3));
        car.setSeating(Integer.parseInt(rows.get(0).get(4)));
        car.setMileage(Integer.parseInt(rows.get(0).get(5)));
        car.setImagePath(List.of(rows.get(0).get(6)));
        car.setCarValueInDollars(Integer.parseInt(rows.get(0).get(7)));
        car.setExtraInformation(rows.get(0).get(8));
        car.setYear(Integer.parseInt(rows.get(0).get(9)));
        car.setCarModel(carModel);
        car.setClient(client);
        car.setActive(true);
        car.setManual(true);
        car.setCategory(CategoryOfCar.MINIVAN);
        car.setInsuranceType(InsuranceType.RIMAC);
        car.setMechanicCondition(MechanicConditions.EXCELLENT);

        when(clientService.getByToken()).thenReturn(client);
        when(carRepository.findByLicensePlate(car.getLicensePlate())).thenReturn(null);
        when(carRepositoryCustom.isActiveSOAT(car.getLicensePlate(), car.getInsuranceType())).thenReturn(true);
        when(carModelRepository.findById(createCarResource.getCarModelId())).thenReturn(Optional.of(car.getCarModel()));
        when(carMapper.toModel(createCarResource)).thenReturn(car);
        when(carRepository.save(car)).thenReturn(car);

        result = carService.create(createCarResource);
    }

    @When("I update my car info with")
    public void iUpdateMyCarInfoWith(DataTable table) {
        List<List<String>> rows = table.cells().stream().skip(1).toList();

        UpdateCarResource updateCarResource = new UpdateCarResource();
        updateCarResource.setCarModelId(car.getCarModel().getId());
        updateCarResource.setActive(car.isActive());
        updateCarResource.setYear(Integer.parseInt(rows.get(0).get(9)));

        Car expected = new Car();
        expected.setId(car.getId());
        expected.setLicensePlate(car.getLicensePlate());
        expected.setInsuranceType(car.getInsuranceType());
        expected.setCarModel(car.getCarModel());
        expected.setClient(car.getClient());
        expected.setActive(car.isActive());
        expected.setYear(car.getYear());

        when(carModelRepository.findById(updateCarResource.getCarModelId())).thenReturn(Optional.of(carModel));
        when(carRepository.save(expected)).thenReturn(expected);
        when(carRepository.findById(expected.getId())).thenReturn(Optional.of(expected));

        result = carService.update(expected.getId(), updateCarResource);
    }

    @Then("I should get a Car with id {long} and year {int}")
    public void iShouldGetAClientWithIdAndNamesJuan(Long carId, Integer year) {
        assertEquals(carId, result.getId());
        assertEquals(year, result.getYear());
    }

    @When("I delete my car")
    public void iDeleteMyCar() {
        cars.remove(car);
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        clientService.delete(car.getId());
        cars.remove(client.getId().intValue() - 1);
        results = cars;
    }

    @Then("I should not get a Car with id {long}")
    public void iShouldNotGetACarWithId(Long carId) {
        boolean existsCar = false;
        for (Car car : results) {
            if (Objects.equals(car.getId(), carId)) {
                existsCar = true;
                break;
            }
        }
        assertFalse(existsCar);
    }

}
