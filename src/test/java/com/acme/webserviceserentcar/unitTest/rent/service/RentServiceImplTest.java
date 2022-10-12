package com.acme.webserviceserentcar.unitTest.rent.service;

import com.acme.webserviceserentcar.car.domain.persistence.CarRepository;
import com.acme.webserviceserentcar.client.domain.persistence.ClientRepository;
import com.acme.webserviceserentcar.client.domain.service.ClientService;
import com.acme.webserviceserentcar.rent.domain.model.entity.Rent;
import com.acme.webserviceserentcar.rent.domain.persistence.RentRepository;
import com.acme.webserviceserentcar.rent.mapping.RentMapper;
import com.acme.webserviceserentcar.rent.resource.RentResource;
import com.acme.webserviceserentcar.rent.resource.update.UpdateRentResource;
import com.acme.webserviceserentcar.rent.service.RentServiceImpl;
import com.acme.webserviceserentcar.shared.exception.ResourceNotFoundException;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

class RentServiceImplTest {

    @Mock
    private RentRepository rentRepository;

    @Mock
    private Validator validator;

    @Mock
    private CarRepository carRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientService clientService;

    @Mock
    private RentMapper rentMapper;

    @InjectMocks
    private RentServiceImpl rentService;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Rent rent = new Rent();
    UpdateRentResource rentResource = new UpdateRentResource();

    @BeforeEach
    void setUp() throws ParseException {
        MockitoAnnotations.openMocks(this);

        // Initialize fields rent object
        rent.setId(1L);
        rent.setAmount(1000);
        rent.setStartDate(format.parse("2022-07-05"));
        rent.setFinishDate(format.parse("2022-07-07"));
        rent.setRate(0);

        //Initialize field rent resource object
        rentResource.setAmount(1000);
        rentResource.setStartDate(format.parse("2022-07-05"));
        rentResource.setFinishDate(format.parse("2022-07-07"));
        rentResource.setRate(5);
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
        int newRate = rentResource.getRate();

        // Act
        when(rentRepository.findById(rent.getId())).thenReturn(Optional.of(rent));
        Rent result = rentService.updateRateClient(rent.getId(), rentResource);

        // Assert
        assertEquals(newRate, rent.getRate());
    }

    @Test
    void getAllRents() {}

    @Test
    void getRentById() {}

    @Test
    void creteRent() {}

    @Test
    void updateRent() {}

    @Test
    void deleteRent() {}
}