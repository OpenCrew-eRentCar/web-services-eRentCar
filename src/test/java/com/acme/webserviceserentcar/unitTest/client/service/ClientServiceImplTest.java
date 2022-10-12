package com.acme.webserviceserentcar.unitTest.client.service;

import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.domain.model.entity.Plan;
import com.acme.webserviceserentcar.client.domain.model.enums.PlanName;
import com.acme.webserviceserentcar.client.domain.persistence.ClientRepository;
import com.acme.webserviceserentcar.client.domain.service.PlanService;
import com.acme.webserviceserentcar.client.service.ClientServiceImpl;
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

class ClientServiceImplTest {

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
    private final String EMPTY_STRING = "";

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
    void validateRecord() {
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        boolean result = clientService.validateRecord(client.getId());
        boolean expected = client.getRecord()<5.0;
        assertEquals(expected, result);
    }

    @Test
    void validateFullNameEmpty() {
        // Arrange
        String firstName = EMPTY_STRING;
        String lastName = EMPTY_STRING;

        // Act
        boolean result = clientService.isValidFullName(firstName, lastName);

        // Assert
        assertFalse(result);
    }

    @Test
    void validateFullNameShort() {
        // Arrange
        String firstName = "An"; // this has less than two letters.
        String lastName = "To";

        // Act
        boolean result = clientService.isValidFullName("An", "To");

        // Assert
        assertFalse(result);
    }

    @Test
    void validateFullNameWithALastname() {
        // Arrange
        String names = "Be";
        String lastName = "Cordova";

        // Act
        boolean result = clientService.isValidFullName(names, lastName);

        // Assert
        assertFalse(result);
    }

    @Test
    void validateFullNameCorrectly() {
        // Arrange
        String firstName = "Benn";
        String lastName = "Cordova Jimenez";

        // Act
        boolean result = clientService.isValidFullName(firstName, lastName);

        // Assert
        assertTrue(result);
    }

    @Test
    void validateNickNameEmpty() {
        // Arrange
        String nickName = EMPTY_STRING;

        // Act
        boolean result = clientService.isValidNickName(nickName);

        // Assert
        assertFalse(result);
    }

    @Test
    void validateNickNameShort() {
        // Arrange
        String nickname = "asd"; // Short: The string has less than 6 letters.

        // Act
        boolean result = clientService.isValidNickName(nickname);

        // Assert
        assertFalse(result);
    }

    @Test
    void validateNickNameCorrectly() {
        // Arrange
        String nickname = "hbcordova";

        // Act
        boolean result = clientService.isValidNickName(nickname);

        // Assert
        assertTrue(result);
    }

    @Test
    void validateDNIWithout8Numbers() {
        // Arrange
        String dni = "123"; // Dni must have eight numbers

        // Act
        boolean result = clientService.isValidDni(dni);

        // Assert
        assertFalse(result);
    }

    @Test
    void validateDNIWithLetters() {
        // Arrange
        String dni = "hello"; // DNI should not have letters.

        // Act
        boolean result = clientService.isValidDni(dni);

        // Arrange
        assertFalse(result);
    }

    @Test
    void validateDNICorrectly() {
        // Arrange
        String dni = "70471826";

        // Act
        boolean result = clientService.isValidDni(dni);

        // Assert
        assertTrue(result);
    }

    @Test
    void validateClientValid() {
        // Arrange
        Client clientBen = client;

        // Act
        when(clientRepository.save(clientBen)).thenReturn(clientBen);
        var result = clientService.create(clientBen);

        // Assert
        assertEquals(clientBen, result);
    }

    @Test
    void validateClientInvalid() {
        // Arrange
        client.setDni("789");

        // Act
        when(clientRepository.save(client)).thenReturn(client);
        var result =
                assertThrows(IllegalArgumentException.class, () -> {
                    clientService.create(client);
                });

        // Assert
        assertEquals("The DNI must have 8 numbers", result.getMessage());
    }

    @Test
    void getAllClients() {
        when(clientRepository.findAll()).thenReturn(clients);
        List<Client> result = clientService.getAll();
        assertEquals(clients, result);
    }

    @Test
    void getClientById() {
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        Client result = clientService.getById(client.getId());
        assertEquals(client, result);
    }

    @Test
    void createClient() {
        when(clientRepository.save(client)).thenReturn(client);
        Client result = clientService.create(client);
        assertEquals(client, result);
    }

    @Test
    void updateClient() {
        Client clientUpdate = client;
        clientUpdate.setNames("Pedrito");

        Mockito.doReturn(client).when(clientService).getByToken(); //Spy the method
        when(clientRepository.save(clientUpdate)).thenReturn(clientUpdate);
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(clientRepository.save(clientUpdate)).thenReturn(clientUpdate);

        Client result = clientService.update(clientUpdate);

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