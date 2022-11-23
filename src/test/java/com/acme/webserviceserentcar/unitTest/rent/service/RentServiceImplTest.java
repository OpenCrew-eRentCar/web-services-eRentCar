package com.acme.webserviceserentcar.unitTest.rent.service;

import com.acme.webserviceserentcar.car.domain.model.entity.Car;
import com.acme.webserviceserentcar.car.domain.persistence.CarRepository;
import com.acme.webserviceserentcar.car.domain.service.CarService;
import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.domain.persistence.ClientRepository;
import com.acme.webserviceserentcar.client.domain.service.ClientService;
import com.acme.webserviceserentcar.rent.domain.model.entity.Rent;
import com.acme.webserviceserentcar.rent.domain.persistence.RentRepository;
import com.acme.webserviceserentcar.rent.mapping.RentMapper;
import com.acme.webserviceserentcar.rent.resource.create.CreateRentResource;
import com.acme.webserviceserentcar.rent.resource.update.UpdateRentResource;
import com.acme.webserviceserentcar.rent.service.RentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RentServiceImplTest {

    @Mock
    private RentRepository rentRepository;

    @Mock
    private Validator validator;

    @Mock
    private CarService carService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private ClientService clientService;

    @Mock
    private RentMapper rentMapper;

    @InjectMocks
    private RentServiceImpl rentService;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private Rent rent;
    UpdateRentResource updateRentResource = new UpdateRentResource();
    private CreateRentResource createRentResource;
    private Client client1;
    private Car car1;

    private Set<Car> cars;
    private List<Rent> rents;

    @BeforeEach
    void setUp() throws ParseException {
        MockitoAnnotations.openMocks(this);

        createRentResource = new CreateRentResource();
        createRentResource.setCarId(1L);

        client1 = new Client();
        client1.setId(1L);
        
        car1= new Car();
        car1.setId(1L);
        car1.setClient(client1);
        
        cars = new HashSet<>();
        cars.add(car1);

        client1.setCars((Set<Car>) cars);
        
        // Initialize fields rent object
        rent = new Rent();
        rent.setId(1L);
        rent.setAmount(1000);
        rent.setStartDate(format.parse("2022-07-05"));
        rent.setFinishDate(format.parse("2022-07-07"));
        rent.setRate(0);
        rent.setClient(client1);
        rent.setCar(car1);
        rent.setRentPerDay(true);

        //Initialize field rent resource object
        updateRentResource.setAmount(1000);
        updateRentResource.setStartDate(format.parse("2022-07-05"));
        updateRentResource.setFinishDate(format.parse("2022-07-07"));
        updateRentResource.setRate(5);

        rents = new ArrayList<>();
        rents.add(rent);
    }

    @Test
    void validateRentFinished() throws ParseException {
        // Arrange
        LocalDateTime now = LocalDateTime.now(); // We get today's date
        LocalDateTime tomorrow = now.plusDays(1); // We increase it one day
        Date finishedDate = Date.from(tomorrow.toInstant(ZoneOffset.UTC));  // We convert it to Date class

        // Act
        boolean result = rentService.isFinishedRent(finishedDate);

        // Assert
        assertFalse(result);
    }

    @Test
    void validateThatReservationExist() {
        // Arrange
        when(rentRepository.findById(rent.getId())).thenReturn(Optional.of(rent));

        // Act
        var result = rentRepository.findById(rent.getId());

        // Assert
        assertNotNull(result);
    }

    @Test
    void validateThatClientCanGiveRating() throws ParseException {
        // Arrange
        int newRate = updateRentResource.getRate();

        // Act
        when(rentRepository.findById(rent.getId())).thenReturn(Optional.of(rent));
        Rent result = rentService.updateRateClient(rent.getId(), updateRentResource);

        // Assert
        assertEquals(newRate, rent.getRate());
    }

    @Test
    void getAllRents() {
        when(clientService.getByToken()).thenReturn(client1);
        when(rentRepository.findAllByClientId(client1.getId())).thenReturn(rents);
        List<Rent> results = rentService.getAll();
        assertEquals(rents, results);
    }

    @Test
    void getRentById() {
        when(clientService.getByToken()).thenReturn(client1);
        when(rentRepository.findById(client1.getId())).thenReturn(Optional.ofNullable(rent));
        Rent result = rentService.getById(rent.getId());
        assertEquals(rent, result);
    }

    @Test
    void creteRent() {
        when(clientService.getByToken()).thenReturn(client1);
        when(clientRepository.findById(client1.getId())).thenReturn(Optional.of(client1));
        when(carRepository.findById(car1.getId())).thenReturn(Optional.of(car1));
        when(rentMapper.toModel(createRentResource)).thenReturn(rent);
        when(rentRepository.save(rent)).thenReturn(rent);

        Rent result = rentService.create(createRentResource);
        assertEquals(rent, result);
        verify(rentRepository).save(rent);
    }

    @Test
    void updateRent() {
       /* when(clientService.getByToken()).thenReturn(client1);
        when(clientService.getById(client1.getId())).thenReturn(client1);
        when(carService.getById(updateRentResource.get())).thenReturn(car1);
        when(rentMapper.toModel(createRentResource)).thenReturn(rent);
        when(rentRepository.save(rent)).thenReturn(rent);
        Rent result = rentService.update(createRentResource);
        assertEquals(rent, result);
        verify(rentRepository).save(rent);*/
    }

    @Test
    void deleteRent() {}
}