package com.acme.webserviceserentcar.unitTest.reservations.service;

import com.acme.webserviceserentcar.car.domain.model.entity.Car;
import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.service.ClientServiceImpl;
import com.acme.webserviceserentcar.favourite.domain.model.entity.Favourite;
import com.acme.webserviceserentcar.rent.domain.model.entity.Rent;
import com.acme.webserviceserentcar.rent.domain.persistence.RentRepository;
import com.acme.webserviceserentcar.rent.resource.create.CreateRentResource;
import com.acme.webserviceserentcar.rent.resource.update.UpdateRentResource;
import com.acme.webserviceserentcar.reservations.service.ReservationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ReservationsServiceImplTest {

    @InjectMocks
    private ReservationServiceImpl reservationsServiceImpl;

    @Mock
    private RentRepository rentRepository;
    @Mock
    private ClientServiceImpl clientService;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private Client client1;

    private Car car1;
    private List<Rent> reservations;
    CreateRentResource rentResource = new CreateRentResource();

    @BeforeEach
    void setUp() throws ParseException {
        MockitoAnnotations.openMocks(this);

        rentResource.setCarId(1L);

        client1 = new Client();
        client1.setId(1L);

        car1 = new Car();
        car1.setId(1L);
        car1.setClient(client1);

        // Initialize fields rent object
        Rent rent1 = new Rent();

        rent1.setId(1L);
        rent1.setAmount(1000);
        rent1.setStartDate(format.parse("2022-07-05"));
        rent1.setFinishDate(format.parse("2022-07-07"));
        rent1.setRate(0);
        rent1.setCar(car1);
        rent1.setClient(client1);

        reservations = new ArrayList<>();
        reservations.add(rent1);
    }

    @Test
    void getAllReservations() {
        when(clientService.getByToken()).thenReturn(client1);
        when(rentRepository.findAllByCarOwner(client1.getId())).thenReturn(reservations);
        List<Rent> results = reservationsServiceImpl.getAll();
        assertEquals(reservations, results);
    }
}
