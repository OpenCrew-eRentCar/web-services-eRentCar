package com.acme.webserviceserentcar.client.service;

import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.domain.model.entity.Plan;
import com.acme.webserviceserentcar.client.domain.persistence.ClientRepository;
import com.acme.webserviceserentcar.client.domain.persistence.PlanRepository;
import com.acme.webserviceserentcar.client.domain.service.ClientService;
import com.acme.webserviceserentcar.shared.exception.ResourceNotFoundException;
import com.acme.webserviceserentcar.shared.exception.ResourceValidationException;
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
    private final PlanRepository planRepository;
    private final Validator validator;

    public ClientServiceImpl(ClientRepository clientRepository, PlanRepository planRepository, Validator validator) {
        this.clientRepository = clientRepository;
        this.planRepository = planRepository;
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
                        .withImagePath(request.getImagePath())
                        .withPlan(request.getPlan()))
        ).orElseThrow(() -> new ResourceNotFoundException(ENTITY, clientId));
    }

    @Override
    public Client updatePlan(Long clientId, Long planId) {
        Plan plan;

        if (planId == 0) plan = null;
        else plan = planRepository.findById(planId).orElseThrow(() -> new ResourceNotFoundException("Plan", planId));

        return clientRepository.findById(clientId).map(client ->
                clientRepository.save(client.withPlan(plan))
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
