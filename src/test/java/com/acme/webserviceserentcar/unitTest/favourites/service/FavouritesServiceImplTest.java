package com.acme.webserviceserentcar.unitTest.favourites.service;

import com.acme.webserviceserentcar.car.domain.model.entity.Car;
import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.service.ClientServiceImpl;
import com.acme.webserviceserentcar.favourite.domain.model.entity.Favourite;
import com.acme.webserviceserentcar.favourite.domain.persistence.FavouriteRepository;
import com.acme.webserviceserentcar.favourite.mapping.FavouriteMapper;
import com.acme.webserviceserentcar.favourite.resource.create.CreateFavouriteResource;
import com.acme.webserviceserentcar.favourite.service.FavouriteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.Validator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class FavouritesServiceImplTest {
    @Mock
    private FavouriteRepository favouriteRepository;
    @Mock
    private FavouriteMapper favouriteMapper;
    @Mock
    private ClientServiceImpl clientService;
    @Mock
    private Validator validator;

    @InjectMocks
    private FavouriteServiceImpl favouriteService;

    private CreateFavouriteResource createFavouriteResource;
    private List<Favourite> favourites;
    private Favourite favourite1;
    private Client client1;
    private Car car1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        createFavouriteResource = new CreateFavouriteResource();
        createFavouriteResource.setCarId(1L);

        client1 = new Client();
        client1.setId(1L);

        Client client2 = new Client();
        client2.setId(2L);

        car1 = new Car();
        car1.setId(1L);
        car1.setClient(client2);

        Car car2 = new Car();
        car2.setId(2L);
        car2.setClient(client2);

        favourite1 = new Favourite();
        favourite1.setId(1L);
        favourite1.setCar(car1);
        favourite1.setClient(client1);

        Favourite favourite2 = new Favourite();
        favourite2.setId(2L);
        favourite2.setCar(car2);
        favourite2.setClient(client1);

        favourites = new ArrayList<>();
        favourites.add(favourite1);
        favourites.add(favourite2);
    }

    @Test
    void getAllFavourites() {
        when(clientService.getByToken()).thenReturn(client1);
        when(favouriteRepository.findByClientId(client1.getId())).thenReturn(favourites);
        List<Favourite> results = favouriteService.getAll();
        assertEquals(favourites, results);
    }

    @Test
    void getFavouriteById() {
        when(favouriteRepository.findById(favourite1.getId())).thenReturn(java.util.Optional.ofNullable(favourite1));
        Favourite result = favouriteService.getById(favourite1.getId());
        assertEquals(favourite1, result);
    }
}
