package com.acme.webserviceserentcar.api;

import com.acme.webserviceserentcar.domain.service.ClientService;
import com.acme.webserviceserentcar.mapping.ClientMapper;
import com.acme.webserviceserentcar.resource.ClientResource;
import com.acme.webserviceserentcar.resource.CreateClientResource;
import com.acme.webserviceserentcar.resource.UpdateClientResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientsController {
    private final ClientService clientService;
    private final ClientMapper mapper;

    public ClientsController(ClientService clientService, ClientMapper mapper) {
        this.clientService = clientService;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<ClientResource> getAllClients(Pageable pageable) {
        return mapper.modelListToPage(clientService.getAll(), pageable);
    }

    @GetMapping("{clientId}")
    public ClientResource getClientById(@PathVariable Long clientId) {
        return mapper.toResource(clientService.getById(clientId));
    }

    @PostMapping
    public ClientResource createClient(@Valid @RequestBody CreateClientResource request) {
        return mapper.toResource(clientService.create(mapper.toModel(request)));
    }

    @PutMapping("{clientId}")
    public ClientResource updateClient(@PathVariable Long clientId, @Valid @RequestBody UpdateClientResource request) {
        return mapper.toResource(clientService.update(clientId, mapper.toModel(request)));
    }

    @DeleteMapping("{clientId}")
    public ResponseEntity<?> deleteClient(@PathVariable Long clientId) {
        return clientService.delete(clientId);
    }
}
