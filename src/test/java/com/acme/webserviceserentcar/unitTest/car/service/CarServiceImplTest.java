package com.acme.webserviceserentcar.unitTest.car.service;

import com.acme.webserviceserentcar.car.domain.model.entity.Car;
import com.acme.webserviceserentcar.car.domain.model.entity.CarModel;
import com.acme.webserviceserentcar.car.domain.model.enums.InsuranceType;
import com.acme.webserviceserentcar.car.domain.persistence.CarModelRepository;
import com.acme.webserviceserentcar.car.domain.persistence.CarRepository;
import com.acme.webserviceserentcar.car.mapping.CarMapper;
import com.acme.webserviceserentcar.car.persistence.CarRepositoryCustom;
import com.acme.webserviceserentcar.car.resource.CarResource;
import com.acme.webserviceserentcar.car.resource.create.CreateCarResource;
import com.acme.webserviceserentcar.car.resource.update.UpdateCarResource;
import com.acme.webserviceserentcar.car.service.CarServiceImpl;
import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.domain.service.ClientService;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CarServiceImplTest {
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
    private Client client;
    private CarModel carModel;

    private CreateCarResource carResource;

    private List<Car> cars;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        client = new Client();
        client.setId(1L);

        carModel = new CarModel();
        carModel.setId(1L);

        car = new Car();
        car.setId(1L);
        car.setLicensePlate("AKR-079");
        car.setInsuranceType(InsuranceType.RIMAC);
        car.setCarModel(carModel);
        car.setClient(client);
        car.setActive(true);

        carResource = new CreateCarResource();
        carResource.setLicensePlate("AKR-079");
        carResource.setInsuranceType(InsuranceType.RIMAC);
        carResource.setCarModelId(1L);

        Car car1 = new Car();
        car1.setId(2L);
        car1.setLicensePlate("R-079");
        car1.setInsuranceType(InsuranceType.RIMAC);
        car1.setCarModel(carModel);
        car1.setClient(client);
        car1.setActive(true);

        Car car2 = new Car();
        car2.setId(3L);
        car2.setLicensePlate("AK9");
        car2.setInsuranceType(InsuranceType.PACIFICO);
        car2.setCarModel(carModel);
        car2.setClient(client);
        car2.setActive(true);

        cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
    }


    @Test
    void ValidateThatThereIsNoSimilarLicensePlateNumber() {
        // Arrange
        when(carRepository.findByLicensePlate("AKR-079")).thenReturn(car);

        // Act
        boolean result = carService.existThisLicensePlate("AKR-080");

        // Assert
        boolean expected = false;
        assertEquals(expected, result);
    }

    @Test
    void ValidateThatTheSoatIsActiveForLicensePlateNumber() {
        // Arrange
        String licensePlate = "AKR-079";
        InsuranceType insuranceType = InsuranceType.RIMAC;

        // Act
        boolean result = carService.isActiveSOAT(licensePlate, insuranceType);

        // Assert
        boolean expected = true;
        assertEquals(expected, result);
    }

    @Test
    void ValidateThatVehicleWasSuccessfullyRegistered() {
        // Arrange
        CreateCarResource request = new CreateCarResource();
        request.setCarModelId(1L);

        // Act
        when(clientService.getByToken()).thenReturn(client);
        when(carRepository.findByLicensePlate(car.getLicensePlate())).thenReturn(null);
        when(carRepositoryCustom.isActiveSOAT(car.getLicensePlate(), car.getInsuranceType())).thenReturn(true);
        when(carModelRepository.findById(request.getCarModelId())).thenReturn(Optional.of(car.getCarModel()));
        when(carMapper.toModel(request)).thenReturn(car);
        when(carRepository.save(car)).thenReturn(car);
        Car result = carService.create(request);

        // Assert
        assertNotNull(result);
    }

    @Test
    void getCarById() {
        when(carRepository.findById(car.getId())).thenReturn( Optional.of(car) );
        Car result = carService.getById(car.getId());
        assertEquals(car, result);
    }

    @Test
    void getAllCars() {
        when(carRepository.findAll()).thenReturn(cars);
        List<Car> result = carService.getAll();
        assertEquals(cars, result);
    }

    @Test
    void createCar() {
        CreateCarResource request = new CreateCarResource();
        request.setCarModelId(1L);

        when(clientService.getByToken()).thenReturn(client);
        when(carRepository.findByLicensePlate(car.getLicensePlate())).thenReturn(null);
        when(carRepositoryCustom.isActiveSOAT(car.getLicensePlate(), car.getInsuranceType())).thenReturn(true);
        when(carModelRepository.findById(request.getCarModelId())).thenReturn(Optional.of(car.getCarModel()));
        when(carMapper.toModel(request)).thenReturn(car);
        when(carRepository.save(car)).thenReturn(car);
        Car result = carService.create(request);
        assertEquals(car, result);
    }

    @Test
    void updateCar() {
        UpdateCarResource updateCarResource = new UpdateCarResource();
        updateCarResource.setCarModelId(car.getCarModel().getId());
        updateCarResource.setActive(car.isActive());

        Car expected = new Car();
        expected.setId(car.getId());
        expected.setLicensePlate(car.getLicensePlate());
        expected.setInsuranceType(car.getInsuranceType());
        expected.setCarModel(car.getCarModel());
        expected.setClient(car.getClient());
        expected.setActive(car.isActive());

        when(carModelRepository.findById(updateCarResource.getCarModelId())).thenReturn(Optional.of(carModel));
        when(carRepository.save(expected)).thenReturn(expected);
        when(carRepository.findById(expected.getId())).thenReturn(Optional.of(expected));

        Car result = carService.update(expected.getId(), updateCarResource);
        //System.out.println("ID: " + result.getId());

        assertEquals(expected, result);
    }

    @Test
    void deleteCar() {
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        carService.delete(car.getId());
        cars.remove(car);

        List<Car> result = cars;

        assertEquals(cars, result);

    }
}