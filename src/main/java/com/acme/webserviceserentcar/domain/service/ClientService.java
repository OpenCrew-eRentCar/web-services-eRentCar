package com.acme.webserviceserentcar.domain.service;

import com.acme.webserviceserentcar.domain.model.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ClientService {
    List<Client> getAll();
    Page<Client> getAll(Pageable pageable);
    Client getAll(Long clientId);
    Client create(Client request);
    Client update(Long clientId, Client request);
    ResponseEntity<?> delete(Long clientId);
}
