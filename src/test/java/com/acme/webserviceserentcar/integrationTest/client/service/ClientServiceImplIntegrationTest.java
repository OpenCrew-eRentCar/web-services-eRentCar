package com.acme.webserviceserentcar.integrationTest.client.service;

import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.domain.model.entity.Plan;
import com.acme.webserviceserentcar.client.domain.model.enums.PlanName;
import com.acme.webserviceserentcar.client.domain.persistence.ClientRepository;
import com.acme.webserviceserentcar.client.domain.service.PlanService;
import com.acme.webserviceserentcar.client.mapping.ClientMapper;
import com.acme.webserviceserentcar.client.resource.create.CreateClientResource;
import com.acme.webserviceserentcar.client.resource.update.UpdateClientResource;
import com.acme.webserviceserentcar.client.service.ClientServiceImpl;
import com.acme.webserviceserentcar.security.domain.model.entity.User;
import com.acme.webserviceserentcar.security.domain.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ClientServiceImplIntegrationTest {
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        client = new Client();
        client.setId(1L);
        client.setRate(5.0);
        client.setAddress("Av.29 de julio");
        client.setRecord(4.0);
        client.setAverageResponsibility(6);
        client.setCellphoneNumber(958332883L);
        client.setImagePath("213");
        client.setMinRecordExpected(5.0);
        client.setNames("heber");
        client.setLastNames("Cordova Jimenez");
        client.setDni("70471826");

        Client client1 = new Client();
        client1.setId(2L);
        client1.setNames("Juan");

        Client client2 = new Client();
        client2.setId(3L);
        client2.setNames("Pedro");

        clients = new ArrayList<>();
        clients.add(client1);
        clients.add(client2);
    }

    @Test
    void createClient() {
        User user = new User();
        user.setId(1L);
        user.setEmail("pedrito@gmail.com");

        CreateClientResource createClientResource = new CreateClientResource();
        createClientResource.setRate(client.getRate());
        createClientResource.setAddress(client.getAddress());
        createClientResource.setAverageResponsibility(client.getAverageResponsibility());
        createClientResource.setCellphoneNumber(client.getCellphoneNumber());
        createClientResource.setImagePath(client.getImagePath());
        createClientResource.setNames(client.getNames());
        createClientResource.setLastNames(client.getLastNames());
        createClientResource.setDni(client.getDni());

        Mockito.doReturn(client).when(clientService).getByToken();
        Mockito.doReturn(user.getId()).when(clientService).getUserIdFromAuthentication();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(clientMapper.toModel(createClientResource)).thenReturn(client);
        when(clientRepository.save(client)).thenReturn(client);
        Client result = clientService.create(createClientResource);
        assertEquals(client, result);
    }

    @Test
    void updateClient() {
        Client clientUpdate = client;
        clientUpdate.setNames("Pedrito");

        UpdateClientResource updateClientResource = new UpdateClientResource();
        updateClientResource.setRate(client.getRate());
        updateClientResource.setAddress(client.getAddress());
        updateClientResource.setAverageResponsibility(client.getAverageResponsibility());
        updateClientResource.setCellphoneNumber(client.getCellphoneNumber());
        updateClientResource.setImagePath(client.getImagePath());
        updateClientResource.setNames(client.getNames());
        updateClientResource.setLastNames(client.getLastNames());

        Mockito.doReturn(client).when(clientService).getByToken(); //Spy the method
        when(clientRepository.save(clientUpdate)).thenReturn(clientUpdate);
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(clientRepository.save(clientUpdate)).thenReturn(clientUpdate);

        Client result = clientService.update(updateClientResource);

        assertEquals(clientUpdate, result);
    }

    @Test
    void updateClientPlan() {
        Plan plan = new Plan();
        plan.setId(1L);
        plan.setName(PlanName.PREMIUM);

        Client clientUpdate = client;
        clientUpdate.setPlan(plan);

        Mockito.doReturn(client).when(clientService).getByToken(); //Spy the method
        when(planService.getById(plan.getId())).thenReturn(plan);
        when(clientRepository.save(clientUpdate)).thenReturn(clientUpdate);
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(clientRepository.save(clientUpdate)).thenReturn(clientUpdate);

        Client result = clientService.updatePlan(plan.getId());

        assertEquals(clientUpdate, result);
    }

    @Test
    void deleteClient() {
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        clientService.delete(client.getId());
        clients.remove(client);
        List<Client> results = clients;
        assertEquals(clients, results);
    }
}