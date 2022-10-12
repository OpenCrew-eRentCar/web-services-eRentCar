package com.acme.webserviceserentcar.unitTest.client.service;

import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.domain.persistence.ClientRepository;
import com.acme.webserviceserentcar.client.domain.service.PlanService;
import com.acme.webserviceserentcar.client.service.ClientServiceImpl;
import com.acme.webserviceserentcar.security.domain.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.Validator;
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
    private ClientServiceImpl clientService;

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
        client.setNames("hbcordova");
        client.setFirstName("Heber");
        client.setLastNames("Cordova Jimenez");
        client.setDni("70471826");
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
        String firstName = "Ben";
        String lastName = "Cordova";

        // Act
        boolean result = clientService.isValidFullName(firstName, lastName);

        // Assert
        assertFalse(result);
    }

    @Test
    void validateFullNameCorrectly() {
        // Arrange
        String firstName = "Ben";
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
    void getAllClients() {}

    @Test
    void getClientById() {}

    @Test
    void createClient() {}

    @Test
    void updateClient() {}

    @Test
    void updateClientPlan() {}

    @Test
    void deleteClient() {}
}