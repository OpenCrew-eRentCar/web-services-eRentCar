package com.acme.webserviceserentcar.unitTest.car.service;

import com.acme.webserviceserentcar.car.domain.model.entity.Car;
import com.acme.webserviceserentcar.car.domain.model.enums.InsuranceType;
import com.acme.webserviceserentcar.car.domain.persistence.CarRepository;
import com.acme.webserviceserentcar.car.persistence.CarRepositoryCustom;
import com.acme.webserviceserentcar.car.service.CarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarRepositoryCustom carRepositoryCustom;

    @InjectMocks
    private CarServiceImpl carService;

    private Car car;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        car = new Car();
        car.setId(Long.valueOf(11));
        car.setLicensePlate("AKR-079");
        car.setInsurance(InsuranceType.RIMAC);
        car.setAddress("Av. 28 de julio");
    }

    @Test
    void ValidateThatThereIsNoSimilarLicensePlateNumber() {
        when(carRepository.findByLicensePlate("AKR-079")).thenReturn(car);
        boolean result = carService.existThisLicensePlate("AKR-080");
        boolean expected = false;
        assertEquals(expected, result);
    }

    @Test
    void ValidateThatTheSoatIsActiveForLicensePlateNumber() {
        boolean result = carService.isActiveSOAT("AKR-079", InsuranceType.RIMAC);
        boolean expected = true;
        assertEquals(expected, result);
    }

    /* TODO: THIS TEST NO WORKING
    @Test
    void ValidateThatVehicleWasSuccessfullyRegistered() {
        when(carRepository.findByLicensePlate("AKR-079")).thenReturn(null);
        when(carRepositoryCustom.isActiveSOAT("AKR-079", InsuranceType.RIMAC)).thenReturn(true);

        Car result = carService.create(new CreateCarResource());
        Car expected = car;

        assertEquals(expected, result);
    }
     */
}