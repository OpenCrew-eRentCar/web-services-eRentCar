package com.acme.webserviceserentcar.client.domain.service;

import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.resource.create.CreateClientResource;
import com.acme.webserviceserentcar.client.resource.update.UpdateClientResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ClientService {
    List<Client> getAll();
    Page<Client> getAll(Pageable pageable);
    Client getById(Long clientId);
    Client getByToken();
    Client create(CreateClientResource request);
    Client update(UpdateClientResource request);
    Client updatePlan(Long planId);
    void updateAccumulatedKilometers(Long kilometers);
    ResponseEntity<?> delete(Long clientId);
    boolean validateRecord(Long clientId);
}
