package com.acme.webserviceserentcar.client.service;

import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.domain.model.entity.Plan;
import com.acme.webserviceserentcar.client.domain.persistence.ClientRepository;
import com.acme.webserviceserentcar.client.domain.persistence.PlanRepository;
import com.acme.webserviceserentcar.client.domain.service.ClientService;
import com.acme.webserviceserentcar.security.domain.model.entity.User;
import com.acme.webserviceserentcar.security.domain.persistence.UserRepository;
import com.acme.webserviceserentcar.security.middleware.UserDetailsImpl;
import com.acme.webserviceserentcar.shared.exception.ResourceNotFoundException;
import com.acme.webserviceserentcar.shared.exception.ResourceValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserRepository userRepository;
    private final Validator validator;

    public ClientServiceImpl(ClientRepository clientRepository, PlanRepository planRepository, UserRepository userRepository, Validator validator) {
        this.clientRepository = clientRepository;
        this.planRepository = planRepository;
        this.userRepository = userRepository;
        this.validator = validator;
    }

    public String getUsernameFromAuthentication() {
        return ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    public Long getUserIdFromAuthentication() {
        return ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
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
    public Client getByToken() {
        Long userId = this.getUserIdFromAuthentication();
        return clientRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException(ENTITY, userId));
    }

    @Override
    public Client create(Client request) {
        Set<ConstraintViolation<Client>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        User user = userRepository.findById(this.getUserIdFromAuthentication())
                .orElseThrow(() -> new ResourceNotFoundException("User", this.getUserIdFromAuthentication()));

        request.setUser(user);

        return clientRepository.save(request);
    }

    @Override
    public Client update(Client request) {
        Set<ConstraintViolation<Client>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        Long clientId = getByToken().getId(); //get client id using token

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
    public Client updatePlan(Long planId) {
        Plan plan;

        if (planId == 0) plan = null;
        else plan = planRepository.findById(planId).orElseThrow(() -> new ResourceNotFoundException("Plan", planId));

        Long clientId = getByToken().getId(); //get client id using token

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
