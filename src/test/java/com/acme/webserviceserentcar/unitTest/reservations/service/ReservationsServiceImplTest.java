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

    @Mock
    private ReservationServiceImpl reservationsServiceImpl;

    @Mock
    private RentRepository rentRepository;
    @Mock
    private ClientServiceImpl clientService;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Rent rent1 = new Rent();
    Rent rent2 = new Rent();
    private Client client1;

    private List<Rent> reservations;
    UpdateRentResource rentResource = new UpdateRentResource();

    @BeforeEach
    void setUp() throws ParseException {
        MockitoAnnotations.openMocks(this);

        client1 = new Client();
        client1.setId(1L);

        // Initialize fields rent object
        rent1.setId(1L);
        rent1.setAmount(1000);
        rent1.setStartDate(format.parse("2022-07-05"));
        rent1.setFinishDate(format.parse("2022-07-07"));
        rent1.setRate(0);

        rent2.setId(2L);
        rent2.setAmount(2000);
        rent2.setStartDate(format.parse("2022-07-06"));
        rent2.setFinishDate(format.parse("2022-07-08"));
        rent2.setRate(0);

        //Initialize field rent resource object
        rentResource.setAmount(1000);
        rentResource.setStartDate(format.parse("2022-07-05"));
        rentResource.setFinishDate(format.parse("2022-07-07"));
        rentResource.setRate(5);

        rentResource.setAmount(2000);
        rentResource.setStartDate(format.parse("2022-07-06"));
        rentResource.setFinishDate(format.parse("2022-07-08"));
        rentResource.setRate(0);

        reservations = new ArrayList<>();
        reservations.add(rent1);
        reservations.add(rent2);
    }

    @Test
    void getAllReservations() {
        when(clientService.getByToken()).thenReturn(client1);
        when(rentRepository.findAllByClientId(client1.getId())).thenReturn(reservations);
        List<Rent> results = reservationsServiceImpl.getAll();
        assertEquals(reservations, results);
    }
}
