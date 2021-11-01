package com.acme.webserviceserentcar.service;

import com.acme.webserviceserentcar.domain.model.entity.Client;
import com.acme.webserviceserentcar.domain.persistence.ClientRepository;
import com.acme.webserviceserentcar.domain.service.ClientService;
import com.acme.webserviceserentcar.exception.ResourceNotFoundException;
import com.acme.webserviceserentcar.exception.ResourceValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class ClientServiceImpl implements ClientService {
    private static final String ENTITY = "Client";
    private final ClientRepository clientRepository;
    private final Validator validator;

    public ClientServiceImpl(ClientRepository clientRepository, Validator validator) {
        this.clientRepository = clientRepository;
        this.validator = validator;
    }


    @Override
    public List<Client> getAll() { return clientRepository.findAll(); }

    @Override
    public Page<Client> getAll(Pageable pageable) { return clientRepository.findAll(pageable); }

    @Override
    public Client getById(Long clientId) {
        return clientRepository.findById(clientId).orElseThrow(() -> new ResourceNotFoundException(ENTITY, clientId));
    }

    @Override
    public Client create(Client request) {
        Set<ConstraintViolation<Client>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return clientRepository.save(request);
    }

    @Override
    public Client update(Long clientId, Client request) {
        Set<ConstraintViolation<Client>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return clientRepository.findById(clientId).map(client ->
                clientRepository.save(client.withNames(request.getNames())
                        .withLastNames(request.getLastNames())
                        .withAddress(request.getAddress())
                        .withCellphoneNumber(request.getCellphoneNumber())
                        .withAverageResponsibility(request.getAverageResponsibility())
                        .withResponseTime(request.getResponseTime())
                        .withRate(request.getRate())
                        .withPlan(request.getPlan())
                        .withEmail(request.getEmail())
                        .withPassword(request.getPassword()))
        ).orElseThrow(() -> new ResourceNotFoundException(ENTITY, clientId));
    }

    @Override
    public ResponseEntity<?> delete(Long clientId) {
        return clientRepository.findById(clientId).map(client -> {
            clientRepository.delete(client);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, clientId));
    }
}
