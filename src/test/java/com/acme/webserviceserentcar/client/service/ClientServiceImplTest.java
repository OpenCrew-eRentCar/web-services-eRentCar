package com.acme.webserviceserentcar.client.service;

import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.domain.persistence.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        client.setId(Long.valueOf(1));
        client.setRate(5.0);
        client.setAddress("Av.29 de julio");
        client.setRecord(7.0);
        client.setAverageResponsibility(6);
        client.setCellphoneNumber(Long.valueOf(958332883));
        client.setLastNames("Alzamora");
        client.setImagePath("213");
        client.setMinRecordExpected(5.0);
    }

    @Test
    void validateRecord() {
        when(clientRepository.getById(1l)).thenReturn(client);
        boolean result = clientService.validateRecord(1L);
        boolean expected = true;
        assertEquals(expected,result);
    }
}