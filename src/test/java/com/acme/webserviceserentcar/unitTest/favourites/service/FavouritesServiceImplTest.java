package com.acme.webserviceserentcar.unitTest.favourites.service;

import com.acme.webserviceserentcar.car.domain.model.entity.Car;
import com.acme.webserviceserentcar.car.domain.service.CarService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FavouritesServiceImplTest {
    @Mock
    private FavouriteRepository favouriteRepository;
    @Mock
    private FavouriteMapper favouriteMapper;
    @Mock
    private ClientServiceImpl clientService;
    @Mock
    private CarService carService;
    @Mock
    private Validator validator;

    @InjectMocks
    private FavouriteServiceImpl favouriteService;

    private CreateFavouriteResource createFavouriteResource;
    private Favourite favourite;
    private Client client1;
    private Client client2;
    private Car car;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        createFavouriteResource = new CreateFavouriteResource();
        createFavouriteResource.setCarId(1L);

        client1 = new Client();
        client1.setId(1L);

        client2 = new Client();
        client2.setId(2L);

        car = new Car();
        car.setId(1L);
        car.setClient(client2);

        favourite = new Favourite();
        favourite.setClient(client1);
        favourite.setCar(car);
    }

    @Test
    void saveFavourite() {
        when(clientService.getByToken()).thenReturn(client1);
        when(clientService.getById(client1.getId())).thenReturn(client1);
        when(carService.getById(createFavouriteResource.getCarId())).thenReturn(car);
        when(favouriteMapper.toModel(createFavouriteResource)).thenReturn(favourite);
        when(favouriteRepository.save(favourite)).thenReturn(favourite);
        Favourite result = favouriteService.create(createFavouriteResource);
        assertEquals(favourite, result);
        verify(favouriteRepository).save(favourite);
    }
}
