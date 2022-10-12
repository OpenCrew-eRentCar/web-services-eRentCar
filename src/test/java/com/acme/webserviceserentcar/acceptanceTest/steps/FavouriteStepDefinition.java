package com.acme.webserviceserentcar.acceptanceTest.steps;

import com.acme.webserviceserentcar.car.domain.model.entity.Car;
import com.acme.webserviceserentcar.car.domain.service.CarService;
import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.service.ClientServiceImpl;
import com.acme.webserviceserentcar.favourite.domain.model.entity.Favourite;
import com.acme.webserviceserentcar.favourite.domain.persistence.FavouriteRepository;
import com.acme.webserviceserentcar.favourite.mapping.FavouriteMapper;
import com.acme.webserviceserentcar.favourite.resource.create.CreateFavouriteResource;
import com.acme.webserviceserentcar.favourite.service.FavouriteServiceImpl;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FavouriteStepDefinition {
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

    private List<Favourite> favourites;
    private Favourite favourite;
    private List<Favourite> results;
    private Favourite result;
    private Client client1;
    private Car car;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Given("I am a Client \\(Favourite)")
    public void iAmAClientFavourite(DataTable table) {
        List<List<String>> rows = table.asLists(String.class);
        List<String> row = rows.get(1);
        client1 = new Client();
        client1.setId(Long.parseLong(row.get(0)));
        client1.setNames(row.get(1));
    }

    @And("Exists a Car with data")
    public void existACarWithData(DataTable table) {
        List<List<String>> rows = table.cells().stream().skip(1).toList();
        List<String> row = rows.get(0);

        Client client = new Client();
        client.setId(Long.parseLong(row.get(1)));
        client.setNames(row.get(2));

        car = new Car();
        car.setId(Long.parseLong(row.get(0)));
        car.setClient(client);
    }

    @When("I add that car to my Favourites")
    public void iAddTheCarToMyFavourites() {
        CreateFavouriteResource createFavouriteResource = new CreateFavouriteResource();
        createFavouriteResource.setCarId(car.getId());

        favourite = new Favourite();
        favourite.setClient(client1);
        favourite.setCar(car);

        when(clientService.getByToken()).thenReturn(client1);
        when(clientService.getById(client1.getId())).thenReturn(client1);
        when(carService.getById(createFavouriteResource.getCarId())).thenReturn(car);
        when(favouriteMapper.toModel(createFavouriteResource)).thenReturn(favourite);
        when(favouriteRepository.save(favourite)).thenReturn(favourite);
        result = favouriteService.create(createFavouriteResource);
    }

    @Then("I should see that car in my Favourites")
    public void iShouldSeeThatCarInMyFavourites() {
        assertEquals(favourite, result);
        verify(favouriteRepository).save(favourite);
    }

    @And("Have a Favourites Cars")
    public void haveAFavouritesCars(DataTable table) {
        List<List<String>> rows = table.cells().stream().skip(1).toList();
        favourites = new ArrayList<>();
        for (List<String> row : rows) {
            car = new Car();
            car.setId(Long.parseLong(row.get(1)));

            favourite = new Favourite();
            favourite.setId(Long.parseLong(row.get(0)));
            favourite.setClient(client1);
            favourite.setCar(car);
            favourites.add(favourite);
        }
    }

    @When("I get my Favourites")
    public void iGetMyFavourites() {
        when(clientService.getByToken()).thenReturn(client1);
        when(favouriteRepository.findByClientId(client1.getId())).thenReturn(favourites);
        results = favouriteService.getAll();
    }

    @Then("I should get my Favourites with length {int}")
    public void iShouldSeeMyFavouritesWithLength(int length) {
        assertEquals(length, results.size());
        assertEquals(favourites, results);
    }

    @When("I get my Favourites by id {long}")
    public void iGetMyFavouritesById(Long carId) {
        when(favouriteRepository.findById(carId))
                .thenReturn(Optional.of(favourites.get(carId.intValue() - 1)));
        result = favouriteService.getById(carId);
    }

    @Then("I should get the Favourite Car with id {int}")
    public void iShouldGetTheFavouriteCarWithId(int id) {
        assertEquals(favourites.get(id - 1), result);
        assertEquals(favourites.get(id - 1).getId(), result.getId());
    }

    @When("I delete my Favourite by id {long}")
    public void iDeleteMyFavouriteById(Long id) {
        when(clientService.getByToken()).thenReturn(client1);
        when(favouriteRepository.existsByIdAndClientId(id, client1.getId())).thenReturn(true);
        when(favouriteRepository.findById(id)).thenReturn(Optional.of(favourites.get(id.intValue() - 1)));
        favouriteService.delete(id);

        favourites.remove(id.intValue() - 1);
        results = favourites;
    }
}
