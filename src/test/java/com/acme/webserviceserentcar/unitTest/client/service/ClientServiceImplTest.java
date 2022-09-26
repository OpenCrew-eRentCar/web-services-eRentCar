package com.acme.webserviceserentcar.unitTest.client.service;

import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.domain.persistence.ClientRepository;
import com.acme.webserviceserentcar.client.service.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;
    @InjectMocks
    private ClientServiceImpl clientService;

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
        client.setLastNames("Alzamora");
        client.setImagePath("213");
        client.setMinRecordExpected(5.0);
    }

    @Test
    void validateRecord() {
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        boolean result = clientService.validateRecord(client.getId());
        boolean expected = true;
        assertEquals(expected,result);
    }
}