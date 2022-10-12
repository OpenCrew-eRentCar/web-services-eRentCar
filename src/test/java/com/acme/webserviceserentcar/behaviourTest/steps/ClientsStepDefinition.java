package com.acme.webserviceserentcar.behaviourTest.steps;

import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.domain.model.entity.Plan;
import com.acme.webserviceserentcar.client.domain.model.enums.PlanName;
import com.acme.webserviceserentcar.client.domain.persistence.ClientRepository;
import com.acme.webserviceserentcar.client.domain.service.PlanService;
import com.acme.webserviceserentcar.client.service.ClientServiceImpl;
import com.acme.webserviceserentcar.security.domain.model.entity.User;
import com.acme.webserviceserentcar.security.domain.persistence.UserRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.*;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

public class ClientsStepDefinition {
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PlanService planService;

    @Mock
    private Validator validator;

    @InjectMocks
    @Spy
    private ClientServiceImpl clientService;
    private List<Client> clients;
    private Client client;
    private List<Client> results;
    private Client result;
    private User user;
    private List<Plan> plans;
    private Plan plan;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Given("I am Admin User")
    public void iAmAClient() {}

    @And("Exist the following Clients in the repository")
    public void existTheFollowingClientsInTheRepository(DataTable table) {
        List<List<String>> rows = table.cells().stream().skip(1).toList();

        clients = new ArrayList<>();
        for (List<String> row : rows) {
            Client client = new Client();
            client.setId(Long.parseLong(row.get(0)));
            client.setNames(row.get(1));
            clients.add(client);
        }
    }

    @When("I get all Clients")
    public void iGetAllClients() {
        when(clientRepository.findAll()).thenReturn(clients);
        results = clientService.getAll();
    }

    @Then("I should get a list of Clients with length {int}")
    public void iShouldGetAListOfClientsWithLength(int length) {
        assertEquals(length, results.size());
        assertEquals(clients, results);
    }

    @When("I get Client with id {long}")
    public void iGetClientWithId(Long clientId) {
        when(clientRepository.findById(clientId))
                .thenReturn(Optional.of(clients.get(clientId.intValue() - 1)));
        result = clientService.getById(clientId);
    }

    @Then("I should get a Client with id {int}")
    public void iShouldGetAClientWithId(int clientId) {
        assertEquals(clientId, result.getId());
    }

    @Given("I am a User")
    public void iAmAUser(DataTable table) {
        List<List<String>> rows = table.cells().stream().skip(1).toList();
        user = new User();
        user.setId(Long.parseLong(rows.get(0).get(0)));
        user.setEmail(rows.get(0).get(1));
    }

    @When("I complete my register in the system with data")
    public void iCompleteMyRegisterInTheSystemWithData(DataTable table) {
        List<List<String>> rows = table.cells().stream().skip(1).toList();
        client = new Client();
        client.setId(Long.parseLong(rows.get(0).get(0)));
        client.setNames(rows.get(0).get(1));
        client.setLastNames(rows.get(0).get(3));
        client.setDni(rows.get(0).get(4));
        client.setUser(user);

        when(clientRepository.save(client)).thenReturn(client);
        result = clientService.create(client);
    }

    @Given("I am a Client \\(Client)")
    public void iAmAClientClient(DataTable table) {
        List<List<String>> rows = table.asLists(String.class);
        List<String> row = rows.get(1);
        client = new Client();
        client.setId(Long.parseLong(row.get(0)));
        client.setNames(row.get(1));
    }

    @When("I update my data with")
    public void iUpdateMyDataWith(DataTable table) {
        List<List<String>> rows = table.cells().stream().skip(1).toList();

        Client clientUpdate = client;
        clientUpdate.setId(Long.parseLong(rows.get(0).get(0)));
        clientUpdate.setNames(rows.get(0).get(1));

        Mockito.doReturn(client).when(clientService).getByToken(); //Spy the method
        when(clientRepository.save(clientUpdate)).thenReturn(clientUpdate);
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(clientRepository.save(clientUpdate)).thenReturn(clientUpdate);

        result = clientService.update(clientUpdate);
    }

    @Then("I should get a Client with id {long} and names {string}")
    public void iShouldGetAClientWithIdAndNamesJuan(Long clientId, String names) {
        assertEquals(clientId, result.getId());
        assertEquals(names, result.getNames());
    }

    @Given("I am a Client without plan")
    public void iAmAClientWithoutPlan(DataTable table) {
        List<List<String>> rows = table.cells().stream().skip(1).toList();
        client = new Client();
        client.setId(Long.parseLong(rows.get(0).get(0)));
        client.setNames(rows.get(0).get(1));
    }

    @And("Exist the following Plans in the repository")
    public void existTheFollowingPlansInTheRepository(DataTable table) {
        List<List<String>> rows = table.cells().stream().skip(1).toList();

        plans = new ArrayList<>();
        for (List<String> row : rows) {
            Plan plan = new Plan();
            plan.setId(Long.parseLong(row.get(0)));
            plan.setName(PlanName.valueOf(row.get(1)));
            plans.add(plan);
        }
    }

    @When("I update my plan with id {int}")
    public void iUpdateMyPlanWithId(int planId) {
        plan = plans.get(planId - 1);

        Client clientUpdate = client;
        clientUpdate.setPlan(plan);

        Mockito.doReturn(client).when(clientService).getByToken(); //Spy the method
        when(clientRepository.save(clientUpdate)).thenReturn(clientUpdate);
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(clientRepository.save(clientUpdate)).thenReturn(clientUpdate);

        result = clientService.update(clientUpdate);
    }

    @Then("I should get a Client with id {long} and planId {long}")
    public void iShouldGetAClientWithIdAndPlanId(Long clientId, Long planId) {
        assertEquals(clientId, result.getId());
        assertEquals(planId, result.getPlan().getId());
    }

    @When("I delete my account")
    public void iDeleteMyAccount() {
        clients.remove(client);
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        clientService.delete(client.getId());
        clients.remove(client.getId().intValue() - 1);
        results = clients;
    }

    @Then("I should not get a Client with id {long}")
    public void iShouldNotGetAClientWithId(Long clientId) {
        boolean existsUser = false;
        for (Client client : results) {
            if (Objects.equals(client.getId(), clientId)) {
                existsUser = true;
                break;
            }
        }
        assertFalse(existsUser);
    }
}
